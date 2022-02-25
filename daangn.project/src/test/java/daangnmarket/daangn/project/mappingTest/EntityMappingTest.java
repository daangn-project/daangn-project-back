package daangnmarket.daangn.project.mappingTest;


import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.repository.ItemPostRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EntityMappingTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemPostRepository itemPostRepository;

    @BeforeEach
    public void deleteAll(){
        memberRepository.deleteAll();
        itemPostRepository.deleteAll();
    }

    @Test
    @Transactional
    public void findMember_From_ItemPostRepository(){
        Member member = new Member();
        member.setUsername("ABC");
        memberRepository.save(member);

        List<Member> all = memberRepository.findAll();
        Long memberId = all.get(0).getId();
        Product p = new Product();
        p.setMember(member);

        itemPostRepository.save(p);
        List<Product> x = itemPostRepository.findByMemberId(memberId);

        assertThat(x.get(0)).isEqualTo(member);
    }
//
//    @Test
//    @Transactional
//    public void findItemPost_From_MemberRepository(){
//        Member member = new Member();
//        member.setUsername("ABC");
//        ItemPost p = new ItemPost();
//        p.setMember(member);
//
//        itemPostRepository.save(p);
//        memberRepository.save(member);
//
//        List<ItemPost> all = itemPostRepository.findAll();
//        Long postId = all.get(0).getId();
//        Member findMember = memberRepository.findByPostId(postId).orElseThrow(() ->
//                new NoSuchElementException("해당 회원이 없습니다."));
//        assertThat(findMember).isEqualTo(member);
//    }
}
