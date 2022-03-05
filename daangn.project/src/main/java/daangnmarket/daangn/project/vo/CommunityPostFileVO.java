package daangnmarket.daangn.project.vo;

import daangnmarket.daangn.project.domain.community.CommunityCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CommunityPostFileVO {
    private String memberId;
    private String title;
    private String description;
    private CommunityCategory communityCategory;
    private List<MultipartFile> files;

}
