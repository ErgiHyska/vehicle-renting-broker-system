package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVehicleReview is a Querydsl query type for VehicleReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVehicleReview extends EntityPathBase<VehicleReview> {

    private static final long serialVersionUID = 418640853L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVehicleReview vehicleReview = new QVehicleReview("vehicleReview");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> rating = createNumber("rating", Long.class);

    public final QRenter renter;

    public final QVehicle vehicleReviewed;

    public QVehicleReview(String variable) {
        this(VehicleReview.class, forVariable(variable), INITS);
    }

    public QVehicleReview(Path<? extends VehicleReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVehicleReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVehicleReview(PathMetadata metadata, PathInits inits) {
        this(VehicleReview.class, metadata, inits);
    }

    public QVehicleReview(Class<? extends VehicleReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.renter = inits.isInitialized("renter") ? new QRenter(forProperty("renter"), inits.get("renter")) : null;
        this.vehicleReviewed = inits.isInitialized("vehicleReviewed") ? new QVehicle(forProperty("vehicleReviewed"), inits.get("vehicleReviewed")) : null;
    }

}

