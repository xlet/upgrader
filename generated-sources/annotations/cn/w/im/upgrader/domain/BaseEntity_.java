package cn.w.im.upgrader.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, Long> id;
	public static volatile SingularAttribute<BaseEntity, Date> updateAt;
	public static volatile SingularAttribute<BaseEntity, Date> createAt;

}

