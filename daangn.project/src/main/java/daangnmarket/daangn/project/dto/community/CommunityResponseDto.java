package daangnmarket.daangn.project.dto.community;

import daangnmarket.daangn.project.domain.community.Community;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.comment.CommentResponseDto;
import daangnmarket.daangn.project.dto.utils.Utility;
import daangnmarket.daangn.project.dto.vote.VoteResponseDto;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityResponseDto extends Utility {
    private Long id;
    private String writer;
    private String title;
    private String description;
    private String communityCategory;
    private LocalDateTime createdDate;
    private String adjustedCreatedDate;
    private LocalDateTime modifiedDate;
    private List<String> imageUrls;
    private String thumbnailImg;
    private VoteResponseDto voteResponseDto;
    private List<CommentResponseDto> commentResponseDtoList;

    public CommunityResponseDto(Community entity) {
        this.id = entity.getId();
        this.writer = entity.getMember().getNickname();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.communityCategory = entity.getCommunityCategory().getValue();
        this.imageUrls = entity.getPhotoList().stream().map(Photo::getPath).collect(Collectors.toList());
        this.thumbnailImg = entity.getPhotoList().isEmpty() ? null : entity.getPhotoList().get(0).getPath();
        this.createdDate = entity.getCreatedTime();
        this.modifiedDate = entity.getModifiedTime();
        this.adjustedCreatedDate = timeFormatting(createdDate);
        this.voteResponseDto = entity.getVote() != null ? new VoteResponseDto(entity.getVote()) : null;
        this.commentResponseDtoList = entity.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
    public void sortCommentsByParentOrderThenCommentOrder(){
        Comparator<CommentResponseDto> orderComparator = Comparator.comparing(CommentResponseDto::getCommentOrder);
        List<CommentResponseDto> comments = this.getCommentResponseDtoList();
        comments.sort(Comparator.comparing(CommentResponseDto::getParentCommentNum)
                .thenComparing(orderComparator));
    }
}
