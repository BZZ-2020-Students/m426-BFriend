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
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select u from bzz.groupa.bfriend.model.User u")
    List<UserOnly> findAllUserOnly();

    @Query("select u from bzz.groupa.bfriend.model.User u where u.location = ?1 and u.id <> ?2 and u.id not in (select m from bzz.groupa.bfriend.model.UserLike m where m.user.id = ?2 and m.likedUser.id = u.id)")
    List<User> findNotMatchedUsers(String city, long id);
}
