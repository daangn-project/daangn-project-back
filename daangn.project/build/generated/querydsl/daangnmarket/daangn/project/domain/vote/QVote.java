package daangnmarket.daangn.project.domain.vote;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVote is a Querydsl query type for Vote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVote extends EntityPathBase<Vote> {

    private static final long serialVersionUID = 300043765L;

    public static final QVote vote = new QVote("vote");

    public final DateTimePath<java.util.Date> expirationTime = createDateTime("expirationTime", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isMultipleVote = createBoolean("isMultipleVote");

    public final ListPath<VoteOption, QVoteOption> voteOptionList = this.<VoteOption, QVoteOption>createList("voteOptionList", VoteOption.class, QVoteOption.class, PathInits.DIRECT2);

    public QVote(String variable) {
        super(Vote.class, forVariable(variable));
    }

    public QVote(Path<? extends Vote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVote(PathMetadata metadata) {
        super(Vote.class, metadata);
    }

}

