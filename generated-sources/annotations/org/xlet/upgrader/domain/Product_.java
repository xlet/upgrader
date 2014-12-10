package org.xlet.upgrader.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends org.xlet.upgrader.domain.BaseEntity_ {

	public static volatile ListAttribute<Product, Version> versions;
	public static volatile SingularAttribute<Product, Integer> height;
	public static volatile SingularAttribute<Product, Integer> width;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> code;
	public static volatile SingularAttribute<Product, String> homepage;

}

