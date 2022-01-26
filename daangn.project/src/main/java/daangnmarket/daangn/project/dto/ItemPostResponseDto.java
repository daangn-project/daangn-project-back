package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.ItemPost;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemPostResponseDto {
    private Long id;
    private String member;
    private String title;
    private String description;
    private List<Long> fileId = new ArrayList<>();  // 첨부 파일 id 목록

    public ItemPostResponseDto(ItemPost entity) {
        this.id = entity.getId();
        this.member = entity.getMember().getNickname();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
    }
}
