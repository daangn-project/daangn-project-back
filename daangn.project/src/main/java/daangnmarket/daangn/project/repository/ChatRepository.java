package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.chat.ChatMessage;
import daangnmarket.daangn.project.domain.chat.ChatRoom;
import daangnmarket.daangn.project.domain.chat.ChatRoomHistory;
import daangnmarket.daangn.project.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository {
    Long saveChatRoomHistory(ChatRoomHistory history);
    Long saveChatRoom(ChatRoom chatRoom);

    void saveContent(ChatMessage chatMessage);

    ChatRoom findRoomById(Long chatRoomId);

    List<ChatMessage> findChatContents(Long chatRoomId, Integer page);

    List<ChatRoom> findRooms(Long hostOrGuestId, Integer page);

    ChatRoom findRoomByMemberId(Long fromMemberId, Long toMemberId);

    void updateChatRoomHistory(Long memberId, Long chatRoomId);

    void deleteRoom(Long chatRoomId);

    ChatRoomHistory findChatRoomHistoryByChatRoomAndMember(Long chatRoomId, Member member);

    Integer countMessagesByCreatedTimeAfter(Long chatRoomId, LocalDateTime lastVisitTime);

}
