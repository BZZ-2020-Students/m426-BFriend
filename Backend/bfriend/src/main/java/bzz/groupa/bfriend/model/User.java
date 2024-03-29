package bzz.groupa.bfriend.model;

import bzz.groupa.bfriend.enums.Gender;
import bzz.groupa.bfriend.enums.Hobby;
import bzz.groupa.bfriend.util.annotation.LocationString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

import static bzz.groupa.bfriend.util.GlobalVars.*;

@Getter
@Setter
@Builder
@EnableAutoConfiguration
@AllArgsConstructor
@Table(name = "users")
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Email
    @Size(max = MAX_EMAIL_LENGTH)
    @Column(length = MAX_EMAIL_LENGTH, unique = true)
    private String email;

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Column(length = MAX_NAME_LENGTH)
    private String firstname;

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Column(length = MAX_NAME_LENGTH)
    private String lastName;

    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    @Column(length = MAX_PASSWORD_LENGTH)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Size(max = MAX_LOCATION_LENGTH)
    @Column(length = MAX_LOCATION_LENGTH)
    @LocationString
    private String location;

    // list of hobbies
    @NotNull
    @ElementCollection(targetClass = Hobby.class)
    @Enumerated(EnumType.STRING)
    private Set<Hobby> hobbies;

    @NotNull
    @Size(max = MAX_BASE64_LENGTH)
    @Column(length = MAX_BASE64_LENGTH)
    private String profilePicture;

    // all the users that this user likes or dislikes
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<UserLike> userLikes;

    private int age;

    private Date lastLogin;

    private Date accountCreated;

    public User() {
        accountCreated = new Date();
        lastLogin = accountCreated;
    }
}
