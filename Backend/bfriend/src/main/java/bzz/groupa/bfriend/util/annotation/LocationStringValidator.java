package bzz.groupa.bfriend.util.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocationStringValidator implements ConstraintValidator<LocationString, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // A location String contains the wikiDataID and the name of the location. separated by a semicolon
        var split = value.split(";");

        if (split.length != 2) {
            return false;
        }

        var wikiDataID = split[0];
        var name = split[1];

        boolean validWikiDataID = new WikiDataIdValidator().isValid(wikiDataID, context);

        return name.length() >= 2 && validWikiDataID;
    }
}
