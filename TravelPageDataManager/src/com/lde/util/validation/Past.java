package com.lde.util.validation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Documentation: <br/>
 * <a href="https://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html/validator-customconstraints.html">
 * 	{@link docs.jboss.org/hibernate/validator/5.0/reference/...} 
 * </a>
 * @author ldenkewi-mint
 *
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastValidator.class)
@Documented
public @interface Past {
	
    String message() default "com.beanValidator.jsr310.past";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
}