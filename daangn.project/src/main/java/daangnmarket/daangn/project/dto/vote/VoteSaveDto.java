package daangnmarket.daangn.project.dto.vote;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteSaveDto {
    private String writer;
    private String title;
    private String description;
    private List<MultipartFile> images = new ArrayList<>();
    private List<VoteOptionCreateDto> voteOptionCreateDtoList;
}
