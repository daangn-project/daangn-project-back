package daangnmarket.daangn.project.dto.product;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProductSaveDto {
    private String writer;
    private String title;
    private String description;
    private Integer price;
    private ProductCategory itemCategory;
    @Builder.Default
    private List<String> photoList = new ArrayList<>();
}
