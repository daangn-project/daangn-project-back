package daangnmarket.daangn.project.dto.community;

import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.dto.vote.VoteSaveDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommunitySaveDto {
    private String writer;
    private String title;
    private String description;
    private CommunityCategory communityCategory;
    private List<MultipartFile> images = new ArrayList<>();
    private Boolean isMultipleVote;
    private Boolean isVoteArticle;
    private List<String> voteOptions;
}
