package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.product.ProductDetailResponseDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberResponseDto {
    private String nickname;
    private String email;
    private List<ProductDetailResponseDto> productDetailResponseDtoList;

    public  MemberResponseDto(Member member){
        this.nickname = member.getNickname();
        this.email = member.getNickname();
        this.productDetailResponseDtoList = member.getProductList()
                .stream().map(ProductDetailResponseDto::new).collect(Collectors.toList());
    }
}

