package io.oasp.gastronomy.restaurant.general.common.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This type is a marker for class that represents an Entity: An object that is not defined by its attributes, but
 * rather by a thread of continuity and its identity.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Domain-driven_design#Building_blocks_of_DDD">Building blocks of DDD</a>
 * @author llaszkie
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BusinessEntity {

}
