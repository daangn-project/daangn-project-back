package daangnmarket.daangn.project.vo;

import daangnmarket.daangn.project.domain.ItemCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemPostFileVO {
    private String memberId;
    private String title;
    private String description;
    private Integer price;
    private ItemCategory itemCategory;
    private List<MultipartFile> files;
}
