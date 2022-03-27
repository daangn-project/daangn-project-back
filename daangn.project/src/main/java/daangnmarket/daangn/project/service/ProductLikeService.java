package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.product.ProductLike;
import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.dto.ProductLikeDTO;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.ProductLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

    private final MemberRepository memberRepository;
    private final ProductLikeRepository productLikeRepository;

    public boolean alreadyLike(Member member, Product product) {
        return productLikeRepository.findByUserIdAndProductId(member, product).isPresent();
    }

    // 좋아요생성
    public boolean save(ProductLikeDTO.LikeRequestDto likeRequestDto) throws IOException {
        Member member = memberRepository.findByUsername(String.valueOf(likeRequestDto.getUsername())).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        ProductLike productLIke = ProductLike.builder()
                .member(member)
                .product(likeRequestDto.getProductId())
                .build();

        //중복체크
        if(!alreadyLike(likeRequestDto.getUsername(), likeRequestDto.getProductId())) {
            productLikeRepository.save(productLIke);
            return true;
        }
        return false;

    }
}
