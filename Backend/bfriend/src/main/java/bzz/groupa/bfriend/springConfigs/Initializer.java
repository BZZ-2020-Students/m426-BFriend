package bzz.groupa.bfriend.springConfigs;

import bzz.groupa.bfriend.enums.EnumUserRole;
import bzz.groupa.bfriend.model.UserRole;
import bzz.groupa.bfriend.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Initializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    public Initializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Initializing...");
        System.out.println("Checking if DB has all needed roles...");

        EnumUserRole[] userRoles = EnumUserRole.values();

        for (EnumUserRole userRole : userRoles) {
            Optional<UserRole> userRoleOptional = userRoleRepository.findByName(userRole);
            if (userRoleOptional.isEmpty()) {
                System.out.println(userRole + " not found, creating...");
                userRoleRepository.save(new UserRole(userRole));
            } else {
                System.out.println(userRole + " found, skipping it...");
            }
        }

        System.out.println("Done!");
    }
}
