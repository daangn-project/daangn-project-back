package daangnmarket.daangn.project;

import daangnmarket.daangn.project.domain.ItemCategory;
import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.repository.ItemPostRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.service.ItemPostService;
import daangnmarket.daangn.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {


    private final InitService initService;



    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberService memberService;
        private final ItemPostService itemPostService;
        private final MemberRepository memberRepository;
        private final ItemPostRepository itemPostRepository;

        public void dbInit1() {
            Member member1 = Member.builder().nickname("jsh1").email("email1@naver.com").password("1234").username("member1").build();
            Member member2 = Member.builder().nickname("jsh2").email("email2@naver.com").password("1234").username("member2").build();
            Member member3 = Member.builder().nickname("jsh3").email("email3@naver.com").password("1234").username("member3").build();
            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);


            ItemPost itemPost1 = ItemPost.builder()
                    .member(member1)
                    .title("아이템1 판매")
                    .price(50000)
                    .itemCategory(ItemCategory.CLOTHES)
                    .description("아이템1 상세설명입니다")
                    .build();
            ItemPost itemPost2 = ItemPost.builder()
                    .member(member2)
                    .title("아이템2 판매")
                    .price(10000)
                    .itemCategory(ItemCategory.SPORTS)
                    .description("아이템2 상세설명입니다")
                    .build();
            ItemPost itemPost3 = ItemPost.builder()
                    .member(member1)
                    .title("아이템3 판매")
                    .price(1210000)
                    .itemCategory(ItemCategory.SPORTS)
                    .description("아이템3 상세설명입니다")
                    .build();

            itemPostRepository.save(itemPost1);
            itemPostRepository.save(itemPost2);
            itemPostRepository.save(itemPost3);
        }
    }
}
