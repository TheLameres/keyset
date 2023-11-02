package thelameres.keyset.services;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.blazebit.persistence.spring.data.repository.KeysetAwareSlice;
import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import thelameres.keyset.data.entityviews.UserReadView;

public interface UserService extends DataService<UserReadView> {
    @Override
    KeysetAwareSlice<UserReadView> getAll(KeysetPageable pageable);
}
