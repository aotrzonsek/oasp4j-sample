package io.oasp.gastronomy.restaurant.general.common.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This type is a marker for class that represents a Domain Event: a domain object that defines an event. Where a domain
 * event is something that happened that domain experts care about.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Domain-driven_design#Building_blocks_of_DDD">Building blocks of DDD</a>
 * @author llaszkie
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface DomainEvent {

}
