package bzz.groupa.bfriend.model;

import bzz.groupa.bfriend.enums.LikeState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Getter
@Setter
@Builder
@EnableAutoConfiguration
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_likes")
@Entity
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



