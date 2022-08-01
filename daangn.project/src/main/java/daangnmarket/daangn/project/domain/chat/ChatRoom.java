package daangnmarket.daangn.project.domain.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import daangnmarket.daangn.project.domain.BaseTimeEntity;
import daangnmarket.daangn.project.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseTimeEntity {
    @Id
    @Column(name = "chat_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Member host;
    private Boolean hostReadStatus = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private Member guest;
    private Boolean guestReadStatus = false;

    @Column(columnDefinition = "VARCHAR(100)")
    private String latestMessage;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    private LocalDateTime updateTime;

    public void setHostReadStatus(Boolean status){
        this.hostReadStatus = status;
    }

    public void setGuestReadStatus(Boolean status){
        this.guestReadStatus = status;
    }

    public void setLatestMessage(String latestMessage) {
        if (latestMessage.strip().length() > 100) {
            this.latestMessage = latestMessage.strip().substring(0, 100);
        } else {
            this.latestMessage = latestMessage.strip();
        }
    }
}
