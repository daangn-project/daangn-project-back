package daangnmarket.daangn.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoDto {
    private String name;
    private String path;
    private Long size;

}
