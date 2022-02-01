package daangnmarket.daangn.project.dto;
import daangnmarket.daangn.project.domain.ItemPost;
import lombok.Data;

<<<<<<< Updated upstream
=======
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.List;

@Data
public class ItemPostResponseDto {
    private Long id;
    private Long memberId;
    private String writer;
    private String title;
    private String description;
<<<<<<< Updated upstream
    private List<Long> fileId;  // 첨부 파일 id 목록
=======
    private Integer viewCount;
    private Integer price;
    private LocalDateTime createdDate;
    private String adjustedCreatedDate;
    private LocalDateTime modifiedDate;
    private List<Long> fileId = new ArrayList<>();  // 첨부 파일 id 목록
>>>>>>> Stashed changes

    public ItemPostResponseDto(ItemPost entity, List<Long> fileId) {
        this.id = entity.getId();
        this.memberId = entity.getMember().getId();
        this.writer = entity.getMember().getNickname();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
<<<<<<< Updated upstream
        this.fileId = fileId;
=======
        this.viewCount = entity.getViewCount();
        this.price = entity.getPrice();
        this.createdDate = entity.getCreatedTime();
        this.modifiedDate = entity.getModifiedTime();
        this.adjustedCreatedDate = adjustCreatedTime();
    }

    public String adjustCreatedTime(){
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
>>>>>>> Stashed changes
    }
}
