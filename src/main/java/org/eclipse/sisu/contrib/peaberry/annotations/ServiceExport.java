package org.eclipse.sisu.contrib.peaberry.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an instance of this class should be exported as an OSGI service.
 */
@Retention(RetentionPolicy.SOURCE)
@Target( value = { ElementType.TYPE } )
public @interface ServiceExport {

	boolean generatePid() default true;

	/** The interfaces this service should be exported under */
	Class<?>[] services() default {};
}
