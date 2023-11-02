package thelameres.keyset.configurations;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableEntityViews(basePackages = "thelameres.keyset.data.entityviews")
@EnableBlazeRepositories(basePackages = "thelameres.keyset.data.repositories",
        repositoryImplementationPostfix = "Impl")
public class BlazePersistenceConfiguration {
    @Bean
    public CriteriaBuilderConfiguration criteriaBuilderConfiguration() {
        return Criteria.getDefault();
    }

    @Bean
    public CriteriaBuilderFactory criteriaBuilderFactory(CriteriaBuilderConfiguration criteriaBuilderConfiguration,
                                                         EntityManagerFactory entityManagerFactory) {
        return criteriaBuilderConfiguration.createCriteriaBuilderFactory(entityManagerFactory);
    }

    @Bean
    public EntityViewManager entityViewManager(CriteriaBuilderFactory criteriaBuilderFactory,
                                               EntityViewConfiguration entityViewConfiguration) {
        return entityViewConfiguration.createEntityViewManager(criteriaBuilderFactory);
    }

}
