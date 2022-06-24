package daangnmarket.daangn.project.domain.vote;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoteResult is a Querydsl query type for VoteResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoteResult extends EntityPathBase<VoteResult> {

    private static final long serialVersionUID = 306050674L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoteResult voteResult = new QVoteResult("voteResult");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final daangnmarket.daangn.project.domain.member.QMember member;

    public final QVoteOption voteOption;

    public QVoteResult(String variable) {
        this(VoteResult.class, forVariable(variable), INITS);
    }

    public QVoteResult(Path<? extends VoteResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoteResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoteResult(PathMetadata metadata, PathInits inits) {
        this(VoteResult.class, metadata, inits);
    }

    public QVoteResult(Class<? extends VoteResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new daangnmarket.daangn.project.domain.member.QMember(forProperty("member")) : null;
        this.voteOption = inits.isInitialized("voteOption") ? new QVoteOption(forProperty("voteOption"), inits.get("voteOption")) : null;
    }

}

