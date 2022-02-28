package daangnmarket.daangn.project.dto.vote;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteOptionCreateDto {
    private String content;
    private List<MultipartFile> images = new ArrayList<>();
}
