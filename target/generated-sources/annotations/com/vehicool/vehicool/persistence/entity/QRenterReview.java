package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRenterReview is a Querydsl query type for RenterReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRenterReview extends EntityPathBase<RenterReview> {

    private static final long serialVersionUID = 1961029709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRenterReview renterReview = new QRenterReview("renterReview");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLender lender;

    public final NumberPath<Long> rating = createNumber("rating", Long.class);

    public final QRenter renter;

    public QRenterReview(String variable) {
        this(RenterReview.class, forVariable(variable), INITS);
    }

    public QRenterReview(Path<? extends RenterReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRenterReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRenterReview(PathMetadata metadata, PathInits inits) {
        this(RenterReview.class, metadata, inits);
    }

    public QRenterReview(Class<? extends RenterReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lender = inits.isInitialized("lender") ? new QLender(forProperty("lender")) : null;
        this.renter = inits.isInitialized("renter") ? new QRenter(forProperty("renter")) : null;
    }

}

