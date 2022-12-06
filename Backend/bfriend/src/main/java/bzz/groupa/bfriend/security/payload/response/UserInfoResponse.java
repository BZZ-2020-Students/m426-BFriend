package bzz.groupa.bfriend.security.payload.response;

import bzz.groupa.bfriend.model.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    private final List<String> roles;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profilepicture;

    public UserInfoResponse() {
        this.roles = new ArrayList<>();
    }

    public UserInfoResponse(Long id, String email, String firstName, String lastName, Set<UserRole> roles) {
        this.id = id;
        this.email = email;
        this.roles = new ArrayList<>();
        for (UserRole role : roles) {
            this.roles.add(role.getName().toString());
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
