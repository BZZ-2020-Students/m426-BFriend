package bzz.groupa.bfriend.repositories;

import bzz.groupa.bfriend.model.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    UserLike findUserLikeByUserIdAndLikedUserId(long userId, long likedUserId);
}
