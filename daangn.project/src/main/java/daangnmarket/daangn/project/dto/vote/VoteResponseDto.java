package daangnmarket.daangn.project.dto.vote;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.service.VoteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class VoteResponseDto {
    private Long id;
    private String title;
    private String description;
    private String writer;
    private List<String> imageUrls;
    private List<VoteOptionResponseDto> voteOptionResponseDtos;
    private List<VoteResultResponseDto> voteResultResponseDtos;

    public VoteResponseDto(Vote vote){
        this.id = vote.getId();
        this.title = vote.getTitle();
        this.description = vote.getDescription();
        this.writer = vote.getMember().getNickname();
        this.imageUrls = vote.getPhotoList().stream().map(Photo::getPath).collect(Collectors.toList());
        this.voteOptionResponseDtos = vote.getVoteOptionList().stream().map(
                VoteOptionResponseDto::new).collect(Collectors.toList());
    }
}
