package bzz.groupa.bfriend.util.annotation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WikiDataIdValidator implements ConstraintValidator<WikiDataID, String> {
    public static boolean isValid(String value) {
        if (value == null) {
            return true;
        }

        var firstChar = value.charAt(0);
        if (!Character.isLetter(firstChar)) {
            return false;
        }

        for (int i = 1; i < value.length(); i++) {
            var c = value.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValid(value);
    }
}
