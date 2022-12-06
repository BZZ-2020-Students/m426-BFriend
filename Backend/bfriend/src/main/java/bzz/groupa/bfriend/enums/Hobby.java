package bzz.groupa.bfriend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Hobby {
    FOOTBALL("Football"),
    BASKETBALL("Basketball"),
    HANDBALL("Handball"),
    VOLLEYBALL("Volleyball"),
    TENNIS("Tennis"),
    SWIMMING("Swimming"),
    RUNNING("Running"),
    CYCLING("Cycling"),
    SKIING("Skiing"),
    SNOWBOARDING("Snowboarding"),
    SKATING("Skating"),
    FISHING("Fishing");

    private final String niceName;
}
