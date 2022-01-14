package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.ItemCategory;
import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Photo;
import lombok.Data;

import java.util.List;

@Data
public class ItemPostSaveDto {
    private String title;
    private String description;
    private ItemCategory itemCategory;
    private List<PhotoDto> photoList;

    public ItemPost toEntity(){
        ItemPost itemPost = ItemPost.builder()
                .title(title)
                .description(description)
                .itemCategory(itemCategory)
                .build();
        // 게시물에 사진 추가
        photoList.forEach(photoDto -> {
            Photo p = Photo.builder().name(photoDto.getName()).path(photoDto.getPath())
                    .size(photoDto.getSize()).build();
            itemPost.getPhotoList().add(p);
        });
        return itemPost;
    }
}
