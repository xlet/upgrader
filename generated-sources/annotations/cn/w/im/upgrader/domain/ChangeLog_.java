package cn.w.im.upgrader.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChangeLog.class)
public abstract class ChangeLog_ extends cn.w.im.upgrader.domain.BaseEntity_ {

	public static volatile SingularAttribute<ChangeLog, String> title;
	public static volatile SingularAttribute<ChangeLog, Integer> height;
	public static volatile SingularAttribute<ChangeLog, String> description;
	public static volatile SingularAttribute<ChangeLog, Integer> width;
	public static volatile SingularAttribute<ChangeLog, String> image;
	public static volatile SingularAttribute<ChangeLog, Version> version;

}

