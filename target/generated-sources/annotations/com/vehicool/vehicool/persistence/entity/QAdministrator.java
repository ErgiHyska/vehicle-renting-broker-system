package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdministrator is a Querydsl query type for Administrator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdministrator extends EntityPathBase<Administrator> {

    private static final long serialVersionUID = -1349430018L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdministrator administrator = new QAdministrator("administrator");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    public final QUser user;

    public QAdministrator(String variable) {
        this(Administrator.class, forVariable(variable), INITS);
    }

    public QAdministrator(Path<? extends Administrator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdministrator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdministrator(PathMetadata metadata, PathInits inits) {
        this(Administrator.class, metadata, inits);
    }

    public QAdministrator(Class<? extends Administrator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

