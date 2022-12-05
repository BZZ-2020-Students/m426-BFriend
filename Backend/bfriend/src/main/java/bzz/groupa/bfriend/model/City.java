package bzz.groupa.bfriend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {
    private String wikiDataId;
    private String name;

    @Override
    public String toString() {
        return wikiDataId + ";" + name;
    }
}
