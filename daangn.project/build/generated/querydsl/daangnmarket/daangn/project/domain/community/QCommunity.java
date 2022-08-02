package daangnmarket.daangn.project.domain.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunity is a Querydsl query type for Community
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunity extends EntityPathBase<Community> {

    private static final long serialVersionUID = -1541108309L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunity community = new QCommunity("community");

    public final daangnmarket.daangn.project.domain.QBaseTimeEntity _super = new daangnmarket.daangn.project.domain.QBaseTimeEntity(this);

    public final ListPath<CommunityComment, QCommunityComment> commentList = this.<CommunityComment, QCommunityComment>createList("commentList", CommunityComment.class, QCommunityComment.class, PathInits.DIRECT2);

    public final EnumPath<CommunityCategory> communityCategory = createEnum("communityCategory", CommunityCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<CommunityPostLike, QCommunityPostLike> likes = this.<CommunityPostLike, QCommunityPostLike>createSet("likes", CommunityPostLike.class, QCommunityPostLike.class, PathInits.DIRECT2);

    public final daangnmarket.daangn.project.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final ListPath<daangnmarket.daangn.project.domain.Photo, daangnmarket.daangn.project.domain.QPhoto> photoList = this.<daangnmarket.daangn.project.domain.Photo, daangnmarket.daangn.project.domain.QPhoto>createList("photoList", daangnmarket.daangn.project.domain.Photo.class, daangnmarket.daangn.project.domain.QPhoto.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final daangnmarket.daangn.project.domain.vote.QVote vote;

    public QCommunity(String variable) {
        this(Community.class, forVariable(variable), INITS);
    }

    public QCommunity(Path<? extends Community> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunity(PathMetadata metadata, PathInits inits) {
        this(Community.class, metadata, inits);
    }

    public QCommunity(Class<? extends Community> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new daangnmarket.daangn.project.domain.member.QMember(forProperty("member")) : null;
        this.vote = inits.isInitialized("vote") ? new daangnmarket.daangn.project.domain.vote.QVote(forProperty("vote")) : null;
    }

}

