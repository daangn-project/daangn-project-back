package daangnmarket.daangn.project;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.repository.ProductRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.service.ProductService;
import daangnmarket.daangn.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitDb {


    private final InitService initService;



    @PostConstruct
    public void init() {
//        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberService memberService;
        private final ProductService itemPostService;
        private final MemberRepository memberRepository;
        private final ProductRepository itemPostRepository;

        public void dbInit1() {
            Member member1 = Member.builder().nickname("jsh1").email("email1@naver.com").password("1234").username("member1").build();
            Member member2 = Member.builder().nickname("jsh2").email("email2@naver.com").password("1234").username("member2").build();
            Member member3 = Member.builder().nickname("jsh3").email("email3@naver.com").password("1234").username("member3").build();
            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);


            Member[] memberArr = new Member[]{member1, member2, member3};
            ProductCategory[] categories = new ProductCategory[]{ProductCategory.CLOTHES, ProductCategory.SPORTS};
            for(int i = 0; i < 10; i++){
                Member member = memberArr[new Random().nextInt(memberArr.length)];
                ProductCategory category = categories[new Random().nextInt(categories.length)];
                int price = (int) (Math.random() * 100000);
                int itemImageIndex = (int) (1 + Math.random() * 8);
                Photo p = Photo.builder().path("https://daangn-images.s3.ap-northeast-2.amazonaws.com/static/test-"+String.valueOf(itemImageIndex)+".jpeg").build();
                Product itemPost = Product.builder()
                        .member(member)
                        .title("Item : " + i)
                        .price(price)
                        .viewCount(0)
                        .productCategory(category)
                        .description("아이템 " + i +" 에 대한 설명입니다.")
                        .build();
                itemPost.addPhoto(p);
                itemPost.setMember(member);
                itemPostRepository.save(itemPost);
            }
        }
    }
}
