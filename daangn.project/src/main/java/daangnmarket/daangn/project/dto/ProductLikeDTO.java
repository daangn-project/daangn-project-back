package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.product.ProductLike;
import lombok.Data;

public class ProductLikeDTO {

    @Data
    public static class LikeRequestDto{
        private Long id;
        private Member username;
        private Product productId;

        public LikeRequestDto(ProductLike entity) {
            this.id = entity.getId();
            this.username = entity.getMember();
            this.productId = entity.getProduct();
        }
    }

}
