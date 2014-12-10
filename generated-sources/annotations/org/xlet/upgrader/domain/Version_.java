package org.xlet.upgrader.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Version.class)
public abstract class Version_ extends org.xlet.upgrader.domain.BaseEntity_ {

	public static volatile SingularAttribute<Version, Product> product;
	public static volatile SingularAttribute<Version, Long> count;
	public static volatile ListAttribute<Version, ChangeLog> changeLogs;
	public static volatile SingularAttribute<Version, VersionState> state;
	public static volatile SingularAttribute<Version, String> download;
	public static volatile SingularAttribute<Version, String> pack;
	public static volatile SingularAttribute<Version, String> version;

}

