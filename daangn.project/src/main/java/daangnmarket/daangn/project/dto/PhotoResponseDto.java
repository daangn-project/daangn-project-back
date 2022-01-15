package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Photo;
import lombok.Data;

@Data
public class PhotoResponseDto {
    private Long fileId;

    public PhotoResponseDto(Photo entity){
        this.fileId = entity.getId();
    }
}
