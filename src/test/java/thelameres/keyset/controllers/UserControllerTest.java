package thelameres.keyset.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import thelameres.keyset.data.entities.User;
import thelameres.keyset.data.repositories.AbstractEntityRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class UserControllerTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
            .withReuse(true);
    static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);


    @Autowired
    MockMvc mockMvc;
    @Autowired
    AbstractEntityRepository<User> userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    List<User> list;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        if (!postgreSQLContainer.isRunning()) postgreSQLContainer.start();
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    void init() {
        list = IntStream.rangeClosed(1, 10)
                .mapToObj(it -> "user" + it)
                .map(it -> {
                    User user = new User();
                    user.setUserName(it);
                    return user;
                })
                .toList();
        userRepository.saveAllAndFlush(list);
    }

    @Test
    void getAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/v1/users")
                                .queryParam("page", "0")
                                .queryParam("size", "3")
                                .queryParam("sort", "id")
                                .queryParam("id.dir", "DESC")
                                .queryParam("sort", "createdDate")
                                .queryParam("createdDate.dir", "DESC")
                )
                .andExpectAll(status().is(200))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        Pair lowest = getNode(jsonNode, "lowest");
        Pair highest = getNode(jsonNode, "highest");
        MvcResult mvcResult1 = mockMvc.perform(
                        get("/api/v1/users")
                                .queryParam("page", "1")
                                .queryParam("prevPage", "0")
                                .queryParam("size", "3")
                                .queryParam("sort", "id")
                                .queryParam("id.dir", "DESC")
                                .queryParam("sort", "createdDate")
                                .queryParam("createdDate.dir", "DESC")
                                .queryParam("lowest", objectMapper.writeValueAsString(lowest))
                                .queryParam("highest", objectMapper.writeValueAsString(highest))
                )
                .andExpectAll(status().is(200))
                .andReturn();
        assertNotEquals(mvcResult.getResponse().getContentAsString(), mvcResult1.getResponse().getContentAsString());
    }

    @AfterAll
    void destroy() {
        userRepository.deleteAll(list);
    }

    private static Pair getNode(JsonNode jsonNode, String node) {
        List<JsonNode> highest = jsonNode.findValues(node);
        JsonNode jsonNode1 = highest.get(0);
        JsonNode tuple = jsonNode1.findValue("tuple");
        AtomicReference<UUID> uuid = new AtomicReference<>();
        AtomicReference<Instant> createdDate = new AtomicReference<>();
        tuple.elements().forEachRemaining(it -> {
            String text = it.asText();
            try {
                uuid.set(UUID.fromString(text));
            } catch (Exception ignored) {
            }
            try {
                createdDate.set(Instant.parse(text));
            } catch (Exception ignored) {
            }
        });
        UUID uuid1 = uuid.get();
        Instant instant = createdDate.get();
        return new Pair(uuid1, instant);
    }

    record Pair(UUID id, Instant createdDate) {
    }
}

