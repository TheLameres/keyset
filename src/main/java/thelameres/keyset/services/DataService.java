package thelameres.keyset.services;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.blazebit.persistence.spring.data.repository.KeysetAwareSlice;
import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import thelameres.keyset.data.entityviews.IdView;

public interface DataService<T extends IdView> {
    KeysetAwareSlice<T> getAll(KeysetPageable pageable);
}
