package bzz.groupa.bfriend.model;

import bzz.groupa.bfriend.enums.LikeState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Getter
@Setter
@Builder
@EnableAutoConfiguration
@AllArgsConstructor
@Entity
@Table(name = "user_likes")
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "liked_user_id")
    private User likedUser;

    @Enumerated(EnumType.STRING)
    private LikeState likeState;
}
