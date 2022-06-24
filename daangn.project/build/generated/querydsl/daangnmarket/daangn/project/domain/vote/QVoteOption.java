package daangnmarket.daangn.project.domain.vote;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoteOption is a Querydsl query type for VoteOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoteOption extends EntityPathBase<VoteOption> {

    private static final long serialVersionUID = 230340298L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoteOption voteOption = new QVoteOption("voteOption");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QVote vote;

    public QVoteOption(String variable) {
        this(VoteOption.class, forVariable(variable), INITS);
    }

    public QVoteOption(Path<? extends VoteOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoteOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoteOption(PathMetadata metadata, PathInits inits) {
        this(VoteOption.class, metadata, inits);
    }

    public QVoteOption(Class<? extends VoteOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vote = inits.isInitialized("vote") ? new QVote(forProperty("vote")) : null;
    }

}

