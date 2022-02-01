package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.ItemCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ItemPostSaveDto {
    private String writer;
    private String title;
    private String description;
    private Integer price;
    private ItemCategory itemCategory;
    private List<PhotoDto> photoList;
}
