package daangnmarket.daangn.project;

import daangnmarket.daangn.project.domain.*;

import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.repository.CommunityRepository;

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
public class InitDb2 {
    private final InitDb2.InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberService memberService;
        private final ProductService itemPostService;
        private final MemberRepository memberRepository;
        private final CommunityRepository communityRepository;

        public void dbInit2() {
            Member member1 = Member.builder().nickname("skc1").email("skc1@naver.com").password("1234").username("member4").build();
            Member member2 = Member.builder().nickname("skc2").email("skc2@naver.com").password("1234").username("member5").build();
            Member member3 = Member.builder().nickname("skc3").email("skc3@naver.com").password("1234").username("member6").build();
            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);


            Member[] memberArr = new Member[]{member1, member2, member3};
            CommunityCategory[] categories = new CommunityCategory[]{CommunityCategory.FIND,CommunityCategory.QUESTION};
            for(int i = 0; i < 10; i++){
                Member member = memberArr[new Random().nextInt(memberArr.length)];
                CommunityCategory category = categories[new Random().nextInt(categories.length)];
                int communityImageIdx = (int) (1 + Math.random() * 8);
                Photo p = Photo.builder().path("https://daangn-images.s3.ap-northeast-2.amazonaws.com/static/test-"+String.valueOf(communityImageIdx)+".jpeg").build();

                Community community = Community.builder()
                        .member(member)
                        .title("동네생활게시물 : " + i)
                        .viewCount(0)
                        .communityCategory(category)
                        .description(i +"번 동네생활에 대한 설명입니다.")
                        .build();
                community.addPhoto(p);
                community.setMember(member);
                communityRepository.save(community);
            }
        }
    }
}
