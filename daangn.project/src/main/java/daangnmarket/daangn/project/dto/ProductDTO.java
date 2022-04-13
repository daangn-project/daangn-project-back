package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.dto.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class DetailResponseDTO extends Utility {
        private Long id;
        private Long memberId;
        private String writer;
        private String title;
        private String description;
        private String productCategory;
        private Integer price;
        private LocalDateTime createdDate;
        private String adjustedCreatedDate;
        private LocalDateTime modifiedDate;
        private List<String> imageUrls;
        private String thumbnailImg;
        private List<ProductDTO.ResponseWithMemberDTO> productWithMemberDtoList;

        public DetailResponseDTO(Product entity) {
            this.id = entity.getId();
            this.memberId = entity.getMember().getId();
            this.writer = entity.getMember().getNickname();
            this.title = entity.getTitle();
            this.description = entity.getDescription();
            this.price = entity.getPrice();
            this.productCategory = entity.getProductCategory().getValue();
            this.imageUrls = entity.getPhotoList().stream().map(Photo::getPath).collect(Collectors.toList());
            this.thumbnailImg = entity.getPhotoList().isEmpty() ? null : entity.getPhotoList().get(0).getPath();
            this.createdDate = entity.getCreatedTime();
            this.modifiedDate = entity.getModifiedTime();
            this.adjustedCreatedDate = timeFormatting(createdDate);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    public static class ResponseWithMemberDTO extends Utility {
        private Long id;
        private String title;
        private String description;
        private String productCategory;
        private Integer price;
        private LocalDateTime createdDate;
        private String adjustedCreatedDate;
        private LocalDateTime modifiedDate;
        private List<String> imageUrls;
        private String thumbnailImg;

        public ResponseWithMemberDTO(Product entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.description = entity.getDescription();
            this.price = entity.getPrice();
            this.productCategory = entity.getProductCategory().getValue();
            this.imageUrls = entity.getPhotoList().stream().map(Photo::getPath).collect(Collectors.toList());
            this.thumbnailImg = entity.getPhotoList().isEmpty() ? null : entity.getPhotoList().get(0).getPath();
            this.createdDate = entity.getCreatedTime();
            this.modifiedDate = entity.getModifiedTime();
            this.adjustedCreatedDate = timeFormatting(createdDate);
        }
    }

    @Data
    @AllArgsConstructor
    public static class SaveDto {
        @NotNull
        private String writer;

        @NotBlank
        private String title;

        @NotBlank
        private String description;

        private Integer price;

        @NotNull
        private ProductCategory productCategory;

        private List<MultipartFile> images = new ArrayList<>();
    }
}
