package thelameres.keyset.data.entityviews;

import com.blazebit.persistence.view.EntityView;
import thelameres.keyset.data.entities.User;

@EntityView(User.class)
public interface UserReadView extends IdView {
    String getUserName();
}
