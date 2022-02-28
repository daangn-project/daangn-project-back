package daangnmarket.daangn.project.dto.product;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductSaveDto {
    private String writer;
    private String title;
    private String description;
    private Integer price;
    private ProductCategory productCategory;
    private List<MultipartFile> images = new ArrayList<>();
}
