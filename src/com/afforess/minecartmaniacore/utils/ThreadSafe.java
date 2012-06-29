/**
 * 
 */
package com.afforess.minecartmaniacore.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Afforess
 */
@Documented
@Target(value=ElementType.METHOD)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ThreadSafe {
	
	public String author() default "Afforess";
	public String version() default "1.0";
	public String shortDescription() default "Indicates that the function is safe to use in parallel threads";

}
