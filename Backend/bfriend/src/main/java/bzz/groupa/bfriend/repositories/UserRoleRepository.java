package bzz.groupa.bfriend.repositories;

import bzz.groupa.bfriend.enums.EnumUserRole;
import bzz.groupa.bfriend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(EnumUserRole name);
}
