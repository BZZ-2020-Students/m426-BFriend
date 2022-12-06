package bzz.groupa.bfriend.model;

import bzz.groupa.bfriend.enums.EnumUserRole;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private EnumUserRole name;

    public UserRole(EnumUserRole name) {
        this.name = name;
    }

    public UserRole() {
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "name=" + name +
                '}';
    }
}
