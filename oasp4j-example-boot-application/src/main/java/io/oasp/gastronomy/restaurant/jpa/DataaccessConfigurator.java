package io.oasp.gastronomy.restaurant.jpa;

import org.springframework.config.java.plugin.tx.AnnotationDrivenTx;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(value = { "io.oasp.gastronomy.restaurant.*.dataaccess" })
@Import({ DatabaseConfigurator.class, JpaConfigurator.class })
@AnnotationDrivenTx(transactionManager = "transactionManager")
public class DataaccessConfigurator {
}
