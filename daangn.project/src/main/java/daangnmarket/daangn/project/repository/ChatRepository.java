package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.chat.ChatMessage;
import daangnmarket.daangn.project.domain.chat.ChatRoom;

import java.util.List;

public interface ChatRepository {
    Long saveChatRoom(ChatRoom chatRoom);

    void saveContent(ChatMessage chatMessage);

    ChatRoom findRoomById(Long chatRoomId);

    List<ChatMessage> findChatContents(Long chatRoomId, Integer page);

    List<ChatRoom> findRooms(Long hostOrGuestId, Integer page);

    ChatRoom findRoomByMemberId(Long fromMemberId, Long toMemberId);

    void deleteRoom(Long chatRoomId);

}
