package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.dto.utils.Utility;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CommunityDTO extends Utility {

    @Data
    public static class ResponseDTO{
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
        private VoteDTO.InfoResponseDTO voteResponseDto;
        private List<CommentDTO.ResponseDTO> commentResponseDtoList;

        public ResponseDTO(Community entity){
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
            this.voteResponseDto = entity.getVote() != null ? new VoteDTO.InfoResponseDTO(entity.getVote()) : null;
            this.commentResponseDtoList = entity.getCommentList().stream().map(CommentDTO.ResponseDTO::new).collect(Collectors.toList());
        }

        public void sortCommentsByParentOrderThenCommentOrder(){
            Comparator<CommentDTO.ResponseDTO> orderComparator = Comparator.comparing(CommentDTO.ResponseDTO::getCommentOrder);
            List<CommentDTO.ResponseDTO> comments = this.getCommentResponseDtoList();
            comments.sort(Comparator.comparing(CommentDTO.ResponseDTO::getParentCommentNum)
                    .thenComparing(orderComparator));
        }
    }

    @Data
    public static class SaveDTO {
        @NotNull
        private String writer;

        @NotEmpty
        private String title;

        @NotEmpty
        private String description;

        @NotNull
        private CommunityCategory communityCategory;

        private List<MultipartFile> images = new ArrayList<>();

        @NotNull
        private Boolean isMultipleVote;

        @NotNull
        private Boolean isVoteArticle;

        private List<String> voteOptions;
    }
}
