package daangnmarket.daangn.project.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -2092096651L;

    public static final QMember member = new QMember("member1");

    public final BooleanPath activated = createBoolean("activated");

    public final SetPath<Authority, QAuthority> authorities = this.<Authority, QAuthority>createSet("authorities", Authority.class, QAuthority.class, PathInits.DIRECT2);

    public final ListPath<daangnmarket.daangn.project.domain.community.CommunityComment, daangnmarket.daangn.project.domain.community.QCommunityComment> commentList = this.<daangnmarket.daangn.project.domain.community.CommunityComment, daangnmarket.daangn.project.domain.community.QCommunityComment>createList("commentList", daangnmarket.daangn.project.domain.community.CommunityComment.class, daangnmarket.daangn.project.domain.community.QCommunityComment.class, PathInits.DIRECT2);

    public final ListPath<daangnmarket.daangn.project.domain.community.Community, daangnmarket.daangn.project.domain.community.QCommunity> communityList = this.<daangnmarket.daangn.project.domain.community.Community, daangnmarket.daangn.project.domain.community.QCommunity>createList("communityList", daangnmarket.daangn.project.domain.community.Community.class, daangnmarket.daangn.project.domain.community.QCommunity.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<daangnmarket.daangn.project.domain.product.Product, daangnmarket.daangn.project.domain.product.QProduct> productList = this.<daangnmarket.daangn.project.domain.product.Product, daangnmarket.daangn.project.domain.product.QProduct>createList("productList", daangnmarket.daangn.project.domain.product.Product.class, daangnmarket.daangn.project.domain.product.QProduct.class, PathInits.DIRECT2);

    public final StringPath profileImg = createString("profileImg");

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

