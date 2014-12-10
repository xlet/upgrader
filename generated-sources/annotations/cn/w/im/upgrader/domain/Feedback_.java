package cn.w.im.upgrader.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Feedback.class)
public abstract class Feedback_ extends cn.w.im.upgrader.domain.BaseEntity_ {

	public static volatile SingularAttribute<Feedback, String> product;
	public static volatile SingularAttribute<Feedback, String> remark;
	public static volatile SingularAttribute<Feedback, String> ip;
	public static volatile SingularAttribute<Feedback, String> version;

}

