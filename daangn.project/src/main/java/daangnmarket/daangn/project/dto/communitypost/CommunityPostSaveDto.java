package daangnmarket.daangn.project.dto.communitypost;

import daangnmarket.daangn.project.domain.CommunityCategory;
import daangnmarket.daangn.project.domain.CommunityPost;
import daangnmarket.daangn.project.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
