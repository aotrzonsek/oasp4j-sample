package io.oasp.gastronomy.restaurant.dozer;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.MealEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SideDishEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.beanmapping.common.impl.IdentityConverter;

import java.util.Arrays;

import net.sf.mmm.util.transferobject.api.EntityTo;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldDefinition;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = { "io.oasp.module.beanmapping" })
public class DozerConfigurator {

	@Bean(name = "dozer")
	public DozerBeanMapper dozerBeanMapper() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		dozerBeanMapper.setMappingFiles(Arrays
				.asList("config/app/common/dozer-mapping.xml"));

		BeanMappingBuilder tableBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(TableEto.class, TableEntity.class);
			}
		};
		BeanMappingBuilder drinkBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(DrinkEto.class, DrinkEntity.class);
			}
		};
		BeanMappingBuilder mealBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(MealEto.class, MealEntity.class);
			}
		};
		BeanMappingBuilder sideDishBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(SideDishEto.class, SideDishEntity.class);
			}
		};
		BeanMappingBuilder entityBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(ApplicationPersistenceEntity.class, EntityTo.class,
						TypeMappingOptions.oneWay()).fields(
						"this",
						new FieldDefinition("persistentEntity")
								.accessible(true),
						FieldsMappingOptions
								.customConverter(IdentityConverter.class));
			}
		};
		dozerBeanMapper.addMapping(tableBuilder);
		dozerBeanMapper.addMapping(drinkBuilder);
		dozerBeanMapper.addMapping(mealBuilder);
		dozerBeanMapper.addMapping(sideDishBuilder);
		dozerBeanMapper.addMapping(entityBuilder);

		return dozerBeanMapper;
	}
}
