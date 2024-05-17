package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLender is a Querydsl query type for Lender
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLender extends EntityPathBase<Lender> {

    private static final long serialVersionUID = -115523381L;

    public static final QLender lender = new QLender("lender");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<ConfidentialFile, QConfidentialFile> confidentialFiles = this.<ConfidentialFile, QConfidentialFile>createList("confidentialFiles", ConfidentialFile.class, QConfidentialFile.class, PathInits.DIRECT2);

    public final ListPath<Contract, QContract> contractSigned = this.<Contract, QContract>createList("contractSigned", Contract.class, QContract.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<RenterReview, QRenterReview> reviewsGiven = this.<RenterReview, QRenterReview>createList("reviewsGiven", RenterReview.class, QRenterReview.class, PathInits.DIRECT2);

    public final ListPath<LenderReview, QLenderReview> reviewsRecieved = this.<LenderReview, QLenderReview>createList("reviewsRecieved", LenderReview.class, QLenderReview.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public QLender(String variable) {
        super(Lender.class, forVariable(variable));
    }

    public QLender(Path<? extends Lender> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLender(PathMetadata metadata) {
        super(Lender.class, metadata);
    }

}

