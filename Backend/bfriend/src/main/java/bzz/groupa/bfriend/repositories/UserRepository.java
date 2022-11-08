package bzz.groupa.bfriend.repositories;

import bzz.groupa.bfriend.model.User;
import bzz.groupa.bfriend.model.UserOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select u from User u")
    List<UserOnly> findAllUserOnly();
}
