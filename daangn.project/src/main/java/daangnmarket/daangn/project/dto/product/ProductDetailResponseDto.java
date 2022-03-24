package daangnmarket.daangn.project.dto.product;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.utils.Utility;
import lombok.Data;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDetailResponseDto extends Utility {
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
    private List<ProductByUserDto> productByUserDtos;

    public ProductDetailResponseDto(Product entity) {
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