package bzz.groupa.bfriend.security.payload.response;

import bzz.groupa.bfriend.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserInfoResponse {
    private final List<String> roles;
    private Long id;
    private String email;

    public UserInfoResponse(Long id, String email, List<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public UserInfoResponse(Long id, String email, Set<UserRole> roles) {
        this.id = id;
        this.email = email;
        this.roles = new ArrayList<>();
        for (UserRole role : roles) {
            this.roles.add(role.getName().toString());
        }
    }
}
