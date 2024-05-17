package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLenderReview is a Querydsl query type for LenderReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLenderReview extends EntityPathBase<LenderReview> {

    private static final long serialVersionUID = 41033731L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLenderReview lenderReview = new QLenderReview("lenderReview");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLender lender;

    public final NumberPath<Long> rating = createNumber("rating", Long.class);

    public final QRenter renter;

    public QLenderReview(String variable) {
        this(LenderReview.class, forVariable(variable), INITS);
    }

    public QLenderReview(Path<? extends LenderReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLenderReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLenderReview(PathMetadata metadata, PathInits inits) {
        this(LenderReview.class, metadata, inits);
    }

    public QLenderReview(Class<? extends LenderReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lender = inits.isInitialized("lender") ? new QLender(forProperty("lender")) : null;
        this.renter = inits.isInitialized("renter") ? new QRenter(forProperty("renter")) : null;
    }

}

