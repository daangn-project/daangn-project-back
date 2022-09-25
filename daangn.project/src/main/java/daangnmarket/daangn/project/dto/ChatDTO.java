package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.chat.ChatMessage;
import daangnmarket.daangn.project.domain.chat.ChatRoom;
import daangnmarket.daangn.project.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

import static daangnmarket.daangn.project.dto.utils.Utility.timeFormatting;

public class ChatDTO {

    @Data
    public static class ChatRoomDTO{
        private Long chatRoomId;
        private MemberDTO.ChatProfileDto chatPartner;
        private Boolean readStatus;
        private LocalDateTime latestChatTime;
        private String latestChatTimeAsString;
        private Integer unreadMessageCount;
        private String latestMessage;

        public ChatRoomDTO(ChatRoom chatRoom, Member member) {
            if(member.getId().equals(chatRoom.getHost().getId())){
                this.readStatus = chatRoom.getHostReadStatus();
                this.chatPartner = new MemberDTO.ChatProfileDto(chatRoom.getGuest());
            } else {
                this.readStatus = chatRoom.getGuestReadStatus();
                this.chatPartner = new MemberDTO.ChatProfileDto(chatRoom.getHost());
            }
            this.chatRoomId = chatRoom.getId();
            this.latestChatTime = chatRoom.getModifiedTime();
            this.latestChatTimeAsString = timeFormatting(latestChatTime);
            this.latestMessage = chatRoom.getLatestMessage();
        }
    }

    @Data
    public static class ChatMessageDTO{
        private Long chatRoomId;
        private String writer;
        private String message;
        private LocalDateTime sendTime;

        public ChatMessageDTO(ChatMessage message) {
            this.chatRoomId = message.getChatRoom().getId();
            this.writer = message.getFromMember().getNickname();
            this.message = message.getMessage();
            this.sendTime = message.getCreatedTime();
        }
    }


}
