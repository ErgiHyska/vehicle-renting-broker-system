package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRenter is a Querydsl query type for Renter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRenter extends EntityPathBase<Renter> {

    private static final long serialVersionUID = 56266901L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRenter renter = new QRenter("renter");

    public final ListPath<Contract, QContract> contractSigned = this.<Contract, QContract>createList("contractSigned", Contract.class, QContract.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LenderReview, QLenderReview> reviewsGiven = this.<LenderReview, QLenderReview>createList("reviewsGiven", LenderReview.class, QLenderReview.class, PathInits.DIRECT2);

    public final ListPath<RenterReview, QRenterReview> reviewsRecieved = this.<RenterReview, QRenterReview>createList("reviewsRecieved", RenterReview.class, QRenterReview.class, PathInits.DIRECT2);

    public final QDataPool status;

    public final com.vehicool.vehicool.security.user.QUser user;

    public QRenter(String variable) {
        this(Renter.class, forVariable(variable), INITS);
    }

    public QRenter(Path<? extends Renter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRenter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRenter(PathMetadata metadata, PathInits inits) {
        this(Renter.class, metadata, inits);
    }

    public QRenter(Class<? extends Renter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.status = inits.isInitialized("status") ? new QDataPool(forProperty("status")) : null;
        this.user = inits.isInitialized("user") ? new com.vehicool.vehicool.security.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

