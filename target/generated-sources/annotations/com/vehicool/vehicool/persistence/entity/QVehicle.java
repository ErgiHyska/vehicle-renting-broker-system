package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVehicle is a Querydsl query type for Vehicle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVehicle extends EntityPathBase<Vehicle> {

    private static final long serialVersionUID = 993450525L;

    public static final QVehicle vehicle = new QVehicle("vehicle");

    public final BooleanPath available = createBoolean("available");

    public final StringPath Brand = createString("Brand");

    public final StringPath color = createString("color");

    public final ListPath<Contract, QContract> contractSigned = this.<Contract, QContract>createList("contractSigned", Contract.class, QContract.class, PathInits.DIRECT2);

    public final NumberPath<Double> engineSize = createNumber("engineSize", Double.class);

    public final StringPath engineType = createString("engineType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<FileData, QFileData> images = this.<FileData, QFileData>createList("images", FileData.class, QFileData.class, PathInits.DIRECT2);

    public final StringPath model = createString("model");

    public final NumberPath<Long> noOfSeats = createNumber("noOfSeats", Long.class);

    public final NumberPath<Long> productionYear = createNumber("productionYear", Long.class);

    public final StringPath status = createString("status");

    public final StringPath transmissionType = createString("transmissionType");

    public final StringPath type = createString("type");

    public final ListPath<ConfidentialFile, QConfidentialFile> vehicleRegistrations = this.<ConfidentialFile, QConfidentialFile>createList("vehicleRegistrations", ConfidentialFile.class, QConfidentialFile.class, PathInits.DIRECT2);

    public final StringPath vin = createString("vin");

    public QVehicle(String variable) {
        super(Vehicle.class, forVariable(variable));
    }

    public QVehicle(Path<? extends Vehicle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVehicle(PathMetadata metadata) {
        super(Vehicle.class, metadata);
    }

}

