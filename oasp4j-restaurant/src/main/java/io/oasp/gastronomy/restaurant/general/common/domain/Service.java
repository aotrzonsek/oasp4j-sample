package io.oasp.gastronomy.restaurant.general.common.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This type is a marker for class that represents a Service: an implementation that spans across {@link BusinessEntity}
 * borders. When an operation does not conceptually belong to any object. Following the natural contours of the problem,
 * you can implement these operations in services.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Domain-driven_design#Building_blocks_of_DDD">Building blocks of DDD</a>
 * @author llaszkie
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Service {

}
