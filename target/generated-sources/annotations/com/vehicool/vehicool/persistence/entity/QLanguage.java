package com.vehicool.vehicool.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLanguage is a Querydsl query type for Language
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLanguage extends EntityPathBase<Language> {

    private static final long serialVersionUID = 1399367303L;

    public static final QLanguage language1 = new QLanguage("language1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath language = createString("language");

    public final StringPath languageShortCode = createString("languageShortCode");

    public final ListPath<TranslatedDataPool, QTranslatedDataPool> translatedDataPoolList = this.<TranslatedDataPool, QTranslatedDataPool>createList("translatedDataPoolList", TranslatedDataPool.class, QTranslatedDataPool.class, PathInits.DIRECT2);

    public QLanguage(String variable) {
        super(Language.class, forVariable(variable));
    }

    public QLanguage(Path<? extends Language> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLanguage(PathMetadata metadata) {
        super(Language.class, metadata);
    }

}

