package io.oasp.gastronomy.restaurant.general.common.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This type is a marker for class that represents a Factory: an abstract {@link BusinessEntity} producer. Methods for
 * creating domain objects should delegate to a specialized Factory object such that alternative implementations may be
 * easily interchanged.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Domain-driven_design#Building_blocks_of_DDD">Building blocks of DDD</a>
 * @author llaszkie
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Factory {

}
