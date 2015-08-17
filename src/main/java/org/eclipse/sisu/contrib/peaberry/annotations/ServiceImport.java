package org.eclipse.sisu.contrib.peaberry.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this instance is an OSGI service import.
 */
@Retention(RetentionPolicy.SOURCE)
@Target( value = { ElementType.FIELD, ElementType.PARAMETER } )
public @interface ServiceImport {
}
