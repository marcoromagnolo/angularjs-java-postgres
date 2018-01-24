package dmt.server.data.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Marco Romagnolo
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {

    String from() default "id";

    String to() default "id";
}
