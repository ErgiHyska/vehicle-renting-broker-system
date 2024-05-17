package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTranslatedDataPool is a Querydsl query type for TranslatedDataPool
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTranslatedDataPool extends EntityPathBase<TranslatedDataPool> {

    private static final long serialVersionUID = 1135387211L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTranslatedDataPool translatedDataPool = new QTranslatedDataPool("translatedDataPool");

    public final QDataPool enumLabel;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLanguage language;

    public final StringPath translatedEnumLabel = createString("translatedEnumLabel");

    public QTranslatedDataPool(String variable) {
        this(TranslatedDataPool.class, forVariable(variable), INITS);
    }

    public QTranslatedDataPool(Path<? extends TranslatedDataPool> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTranslatedDataPool(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTranslatedDataPool(PathMetadata metadata, PathInits inits) {
        this(TranslatedDataPool.class, metadata, inits);
    }

    public QTranslatedDataPool(Class<? extends TranslatedDataPool> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.enumLabel = inits.isInitialized("enumLabel") ? new QDataPool(forProperty("enumLabel")) : null;
        this.language = inits.isInitialized("language") ? new QLanguage(forProperty("language")) : null;
    }

}

