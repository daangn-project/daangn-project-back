package daangnmarket.daangn.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhoto is a Querydsl query type for Photo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhoto extends EntityPathBase<Photo> {

    private static final long serialVersionUID = 548730873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhoto photo = new QPhoto("photo");

    public final daangnmarket.daangn.project.domain.community.QCommunity community;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath path = createString("path");

    public final daangnmarket.daangn.project.domain.product.QProduct product;

    public final daangnmarket.daangn.project.domain.vote.QVote vote;

    public QPhoto(String variable) {
        this(Photo.class, forVariable(variable), INITS);
    }

    public QPhoto(Path<? extends Photo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhoto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhoto(PathMetadata metadata, PathInits inits) {
        this(Photo.class, metadata, inits);
    }

    public QPhoto(Class<? extends Photo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.community = inits.isInitialized("community") ? new daangnmarket.daangn.project.domain.community.QCommunity(forProperty("community"), inits.get("community")) : null;
        this.product = inits.isInitialized("product") ? new daangnmarket.daangn.project.domain.product.QProduct(forProperty("product"), inits.get("product")) : null;
        this.vote = inits.isInitialized("vote") ? new daangnmarket.daangn.project.domain.vote.QVote(forProperty("vote")) : null;
    }

}

