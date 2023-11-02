package thelameres.keyset.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_username", columnNames = "USERNAME")
        },
        indexes = {
                @Index(name = "idx_created_date", columnList = "CREATED_DATE")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractEntity {
    @Column(name = "USERNAME")
    private String userName;
}
