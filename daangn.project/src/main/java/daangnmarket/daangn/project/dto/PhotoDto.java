package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Photo;
import lombok.Data;

@Data
public class PhotoDto {
    private String name;
    private String path;
    private Long size;

}
