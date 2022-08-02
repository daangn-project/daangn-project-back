package daangnmarket.daangn.project.domain.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityPostLike is a Querydsl query type for CommunityPostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityPostLike extends EntityPathBase<CommunityPostLike> {

    private static final long serialVersionUID = 737379106L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityPostLike communityPostLike = new QCommunityPostLike("communityPostLike");

    public final QCommunity community;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final daangnmarket.daangn.project.domain.member.QMember member;

    public QCommunityPostLike(String variable) {
        this(CommunityPostLike.class, forVariable(variable), INITS);
    }

    public QCommunityPostLike(Path<? extends CommunityPostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityPostLike(PathMetadata metadata, PathInits inits) {
        this(CommunityPostLike.class, metadata, inits);
    }

    public QCommunityPostLike(Class<? extends CommunityPostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.community = inits.isInitialized("community") ? new QCommunity(forProperty("community"), inits.get("community")) : null;
        this.member = inits.isInitialized("member") ? new daangnmarket.daangn.project.domain.member.QMember(forProperty("member")) : null;
    }

}

