package thelameres.keyset.data.repositories;

import com.blazebit.persistence.spring.data.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import thelameres.keyset.data.entityviews.IdView;

import java.util.UUID;

@NoRepositoryBean
public interface AbstractEntityViewRepository<T extends IdView> extends EntityViewRepository<T, UUID> {
    KeysetAwareSlice<T> findAll(KeysetPageable pageable);
    KeysetAwareSlice<T> findAll(KeysetPageable pageable, BlazeSpecification specification);
}