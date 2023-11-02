package thelameres.keyset.data.entityviews;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import thelameres.keyset.data.entities.AbstractEntity;

import java.time.Instant;
import java.util.UUID;

@EntityView(AbstractEntity.class)
public interface IdView {
    @IdMapping
    UUID getId();

    Instant getCreatedDate();
}
