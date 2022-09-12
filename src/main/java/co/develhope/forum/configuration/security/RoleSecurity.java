package co.develhope.forum.configuration.security;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoleSecurity {

	public String[] value() default "";
	
}
