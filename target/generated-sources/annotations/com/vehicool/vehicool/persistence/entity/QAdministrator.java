package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdministrator is a Querydsl query type for Administrator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdministrator extends EntityPathBase<Administrator> {

    private static final long serialVersionUID = -1349430018L;

    public static final QAdministrator administrator = new QAdministrator("administrator");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath status = createString("status");

    public QAdministrator(String variable) {
        super(Administrator.class, forVariable(variable));
    }

    public QAdministrator(Path<? extends Administrator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdministrator(PathMetadata metadata) {
        super(Administrator.class, metadata);
    }

}

