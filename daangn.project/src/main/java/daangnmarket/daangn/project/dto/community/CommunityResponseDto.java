package daangnmarket.daangn.project.dto.community;

import daangnmarket.daangn.project.domain.community.Community;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.vote.VoteResponseDto;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityResponseDto {
    private Long id;
    private String writer;
    private String title;
    private String description;
    private String communityCategory;
    private Integer viewCount;
    private LocalDateTime createdDate;
    private String adjustedCreatedDate;
    private LocalDateTime modifiedDate;
    private List<String> imageUrls;
    private String thumbnailImg;
    private VoteResponseDto voteResponseDto;

    public CommunityResponseDto(Community entity) {
        this.id = entity.getId();
        this.writer = entity.getMember().getNickname();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.viewCount = entity.getViewCount();
        this.communityCategory = entity.getCommunityCategory().getValue();
        this.imageUrls = entity.getPhotoList().stream().map(Photo::getPath).collect(Collectors.toList());
        this.thumbnailImg = entity.getPhotoList().isEmpty() ? null : entity.getPhotoList().get(0).getPath();
        this.createdDate = entity.getCreatedTime();
        this.modifiedDate = entity.getModifiedTime();
        this.adjustedCreatedDate = adjustCreatedTime();
        this.voteResponseDto = entity.getVote() != null ? new VoteResponseDto(entity.getVote()) : null;
    }

    public String adjustCreatedTime(){ // 보여지는 시간 카운트
        LocalDateTime created = this.createdDate;
        LocalDateTime now = LocalDateTime.now();
        this.adjustedCreatedDate = "";
        if(created.isAfter(now.minusHours(1L))){
            LocalDateTime nowByMinute = now.truncatedTo(ChronoUnit.MINUTES);
            LocalDateTime createdByMinute = created.truncatedTo(ChronoUnit.MINUTES);
            if(createdByMinute.isAfter(nowByMinute.minusMinutes(1L))){
                this.adjustedCreatedDate = "방금 전";
            }else{
                Duration duration = Duration.between(created, now);
                this.adjustedCreatedDate = (duration.getSeconds() / 60) + "분 전";
            }
        }else if(created.truncatedTo(ChronoUnit.DAYS).isEqual(now.truncatedTo(ChronoUnit.DAYS))){
            this.adjustedCreatedDate = this.createdDate.format(DateTimeFormatter.ofPattern("hh시 mm분"));
        }else{
            this.adjustedCreatedDate = this.createdDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        }
        return this.adjustedCreatedDate;
    }
}
