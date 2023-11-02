package thelameres.keyset.controllers;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.blazebit.persistence.spring.data.repository.KeysetAwareSlice;
import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.blazebit.persistence.spring.data.webmvc.KeysetConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thelameres.keyset.data.entities.User;
import thelameres.keyset.data.entityviews.UserReadView;
import thelameres.keyset.services.DataService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final DataService<UserReadView> dataService;

    public UserController(DataService<UserReadView> dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public KeysetAwareSlice<UserReadView> getAll(@KeysetConfig(value = User.class) KeysetPageable pageable) {
        return dataService.getAll(pageable);
    }
}
