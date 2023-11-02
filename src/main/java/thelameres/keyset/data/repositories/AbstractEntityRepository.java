package thelameres.keyset.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import thelameres.keyset.data.entities.AbstractEntity;

import java.util.UUID;

@NoRepositoryBean
public interface AbstractEntityRepository<T extends AbstractEntity> extends JpaRepository<T, UUID> {
}