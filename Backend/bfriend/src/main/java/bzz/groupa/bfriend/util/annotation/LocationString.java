package bzz.groupa.bfriend.util.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {LocationStringValidator.class})
public @interface LocationString {
    String message() default "Invalid location String";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
