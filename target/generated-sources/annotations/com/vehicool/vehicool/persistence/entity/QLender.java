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

    private static final PathInits INITS = PathInits.DIRECT2;

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

    public final QDataPool status;

    public final ListPath<Vehicle, QVehicle> vehicles = this.<Vehicle, QVehicle>createList("vehicles", Vehicle.class, QVehicle.class, PathInits.DIRECT2);

    public QLender(String variable) {
        this(Lender.class, forVariable(variable), INITS);
    }

    public QLender(Path<? extends Lender> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLender(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLender(PathMetadata metadata, PathInits inits) {
        this(Lender.class, metadata, inits);
    }

    public QLender(Class<? extends Lender> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.status = inits.isInitialized("status") ? new QDataPool(forProperty("status")) : null;
    }

}

