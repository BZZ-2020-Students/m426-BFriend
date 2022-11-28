package bzz.groupa.bfriend.util.annotation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WikiDataIdValidator implements ConstraintValidator<WikiDataID, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValid(value);
    }

    public static boolean isValid(String value) {
        if (value == null) {
            return true;
        }

        // A wikiDataID is a 1 letter + 5 digits String
        if (value.length() != 6) {
            return false;
        }

        return value.matches("[A-Z][0-9]{5}");
    }
}
