package thelameres.keyset.services;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.blazebit.persistence.spring.data.repository.KeysetAwareSlice;
import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import org.springframework.stereotype.Service;
import thelameres.keyset.data.entityviews.UserReadView;
import thelameres.keyset.data.repositories.UserViewRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserViewRepository userViewRepository;

    public UserServiceImpl(UserViewRepository userViewRepository) {
        this.userViewRepository = userViewRepository;
    }

    @Override
    public KeysetAwareSlice<UserReadView> getAll(KeysetPageable pageable) {
        return userViewRepository.findAll(pageable);
    }
}
