package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDataPool is a Querydsl query type for DataPool
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataPool extends EntityPathBase<DataPool> {

    private static final long serialVersionUID = 506931733L;

    public static final QDataPool dataPool = new QDataPool("dataPool");

    public final ListPath<Contract, QContract> contractList = this.<Contract, QContract>createList("contractList", Contract.class, QContract.class, PathInits.DIRECT2);

    public final ListPath<Vehicle, QVehicle> engineTypeVehicles = this.<Vehicle, QVehicle>createList("engineTypeVehicles", Vehicle.class, QVehicle.class, PathInits.DIRECT2);

    public final StringPath enumLabel = createString("enumLabel");

    public final StringPath enumName = createString("enumName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Vehicle, QVehicle> locationVehicles = this.<Vehicle, QVehicle>createList("locationVehicles", Vehicle.class, QVehicle.class, PathInits.DIRECT2);

    public final ListPath<Vehicle, QVehicle> statusVehicles = this.<Vehicle, QVehicle>createList("statusVehicles", Vehicle.class, QVehicle.class, PathInits.DIRECT2);

    public final ListPath<TranslatedDataPool, QTranslatedDataPool> translatedDataPoolList = this.<TranslatedDataPool, QTranslatedDataPool>createList("translatedDataPoolList", TranslatedDataPool.class, QTranslatedDataPool.class, PathInits.DIRECT2);

    public final ListPath<Vehicle, QVehicle> transmissionVehicles = this.<Vehicle, QVehicle>createList("transmissionVehicles", Vehicle.class, QVehicle.class, PathInits.DIRECT2);

    public final ListPath<Vehicle, QVehicle> vehicleTypeVehicles = this.<Vehicle, QVehicle>createList("vehicleTypeVehicles", Vehicle.class, QVehicle.class, PathInits.DIRECT2);

    public QDataPool(String variable) {
        super(DataPool.class, forVariable(variable));
    }

    public QDataPool(Path<? extends DataPool> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataPool(PathMetadata metadata) {
        super(DataPool.class, metadata);
    }

}

