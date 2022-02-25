package daangnmarket.daangn.project.dto.community;

import daangnmarket.daangn.project.domain.CommunityCategory;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CommunityPostSaveDto {

    private String title;
    private String description;
    private String writer;
    private CommunityCategory communityCategory;
    @Builder.Default // 기본 생성자를 만들어준다.
    private List<String> photoList = new ArrayList<>();

}
