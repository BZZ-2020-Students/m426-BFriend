package bzz.groupa.bfriend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

import static bzz.groupa.bfriend.util.GlobalVars.*;

@Getter
@Setter
@EnableAutoConfiguration
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Column(length = MAX_USERNAME_LENGTH, unique = true)
    private String username;

    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    @Column(length = MAX_PASSWORD_LENGTH)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles;

    private Date lastLogin;

    private Date accountCreated;

    public User() {
        accountCreated = new Date();
        lastLogin = accountCreated;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        accountCreated = new Date();
        lastLogin = accountCreated;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
