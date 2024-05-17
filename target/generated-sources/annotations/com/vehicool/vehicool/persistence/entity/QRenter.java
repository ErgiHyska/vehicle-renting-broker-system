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

    public static final QRenter renter = new QRenter("renter");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<Contract, QContract> contractSigned = this.<Contract, QContract>createList("contractSigned", Contract.class, QContract.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<LenderReview, QLenderReview> reviewsGiven = this.<LenderReview, QLenderReview>createList("reviewsGiven", LenderReview.class, QLenderReview.class, PathInits.DIRECT2);

    public final ListPath<RenterReview, QRenterReview> reviewsRecieved = this.<RenterReview, QRenterReview>createList("reviewsRecieved", RenterReview.class, QRenterReview.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public QRenter(String variable) {
        super(Renter.class, forVariable(variable));
    }

    public QRenter(Path<? extends Renter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRenter(PathMetadata metadata) {
        super(Renter.class, metadata);
    }

}

