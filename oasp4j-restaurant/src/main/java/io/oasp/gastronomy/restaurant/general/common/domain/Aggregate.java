package io.oasp.gastronomy.restaurant.general.common.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This type is a marker for class that represents an Aggregate: A collection of objects that are bound together by a
 * root entity, otherwise known as an aggregate root. The aggregate root guarantees the consistency of changes being
 * made within the aggregate by forbidding external objects from holding references to its members.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Domain-driven_design#Building_blocks_of_DDD">Building blocks of DDD</a>
 * @author llaszkie
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Aggregate {

}
