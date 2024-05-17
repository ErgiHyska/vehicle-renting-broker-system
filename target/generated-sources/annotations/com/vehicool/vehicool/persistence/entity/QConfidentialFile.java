package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfidentialFile is a Querydsl query type for ConfidentialFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfidentialFile extends EntityPathBase<ConfidentialFile> {

    private static final long serialVersionUID = 689214291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfidentialFile confidentialFile = new QConfidentialFile("confidentialFile");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ArrayPath<byte[], Byte> imageData = createArray("imageData", byte[].class);

    public final QLender lender;

    public final StringPath name = createString("name");

    public final QRenter renter;

    public final StringPath type = createString("type");

    public final QVehicle vehicle;

    public QConfidentialFile(String variable) {
        this(ConfidentialFile.class, forVariable(variable), INITS);
    }

    public QConfidentialFile(Path<? extends ConfidentialFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfidentialFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfidentialFile(PathMetadata metadata, PathInits inits) {
        this(ConfidentialFile.class, metadata, inits);
    }

    public QConfidentialFile(Class<? extends ConfidentialFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lender = inits.isInitialized("lender") ? new QLender(forProperty("lender")) : null;
        this.renter = inits.isInitialized("renter") ? new QRenter(forProperty("renter")) : null;
        this.vehicle = inits.isInitialized("vehicle") ? new QVehicle(forProperty("vehicle")) : null;
    }

}

