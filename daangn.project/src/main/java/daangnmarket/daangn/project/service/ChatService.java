package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.auth.ClientMemberLoader;
import daangnmarket.daangn.project.domain.chat.ChatMessage;
import daangnmarket.daangn.project.domain.chat.ChatRoom;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.dto.ChatDTO;
import daangnmarket.daangn.project.repository.ChatRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final ClientMemberLoader clientMemberLoader;

    public Long createChatRoom(Long guestMemberId){
        Member host = clientMemberLoader.getClientMember();
        // 이미 존재하는 채팅방있으면 채팅방 id 리턴
        ChatRoom foundRoom = chatRepository.findRoomByMemberId(host.getId(), guestMemberId);
        if (foundRoom != null) {
            return foundRoom.getId();
        }
        // 상대방이 유효한지 체크
        Member guestMember = memberRepository.findMemberById(guestMemberId);

        // 채팅방 없으면 생성
        ChatRoom room = ChatRoom.builder().host(host).guest(guestMember).build();
        return chatRepository.saveChatRoom(room);
    }

    // 채팅방 목록, page당 10개, 마지막 1개는 다음페이지 확인용
    @Transactional(readOnly = true)
    public List<ChatRoom> loadChatRooms(Integer page) {
        return chatRepository.findRooms(clientMemberLoader.getClientMember().getId(), page);
    }

    // 채팅 내용 가져오기
    public List<ChatDTO.ChatMessageDTO> loadMessages(Long chatRoomId, Integer page) {
//        if (!isParticipant(chatRoomId)) {
//            throw new CustomException(2002, "잘못된 접근입니다.");
//        }

        Long clientMemberId = clientMemberLoader.getClientMember().getId();
        ChatRoom room = chatRepository.findRoomById(chatRoomId);
        // '읽음' 상태로 변경
        if (clientMemberId.equals(room.getHost().getId())) {
            room.setHostReadStatus(true);
        } else {
            room.setGuestReadStatus(true);
        }

        // 요청자 본인 메세지 탐색
        List<ChatMessage> chatMessageList = chatRepository.findChatContents(chatRoomId, page);
        List<ChatDTO.ChatMessageDTO> chatMessageDTOList = new ArrayList<>();
        for (ChatMessage message : chatMessageList) {
            if (clientMemberId == message.getFromMember().getId()) {
                chatMessageDTOList.add(new ChatDTO.ChatMessageDTO(message));
            } else {
                chatMessageDTOList.add(new ChatDTO.ChatMessageDTO(message));
            }
        }
        return chatMessageDTOList;
    }

    // 메세지 보냈을 떄
    public void sendMessage(Long chatRoomId, String message) {
//        if (!isParticipant(chatRoomId)) {
//            throw new CustomException(2003, "잘못된 접근입니다.");
//        }
        ChatRoom room = chatRepository.findRoomById(chatRoomId);

        ChatMessage chatMessage = ChatMessage.builder()
                .message(message).chatRoom(room).build();

        room.setLatestMessage(message);
        room.getChatMessageList().add(chatMessage);

        // 메세지의 보낸사람, 받는사람 설정 + 채팅방 읽음 상태 설정
        Long memberId = clientMemberLoader.getClientMember().getId();
        if (memberId == room.getHost().getId()) {
            room.setHostReadStatus(true);
            room.setGuestReadStatus(false);
        } else {
            room.setHostReadStatus(false);
            room.setGuestReadStatus(true);
        }
        chatRepository.saveContent(chatMessage);
    }

    // 채팅방 나가기
    public void leaveChatRoom(Long chatRoomId) {
//        if (!isParticipant(chatRoomId)) {
//            throw new CustomException(2004, "잘못된 접근입니다.");
//        }
        chatRepository.deleteRoom(chatRoomId);
    }

    // 요청자가 채팅방의 참여자가 맞는지 확인
    private Boolean isParticipant(Long chatRoomId) {
        Long memberId = clientMemberLoader.getClientMember().getId();
        ChatRoom room = chatRepository.findRoomById(chatRoomId);
        if (memberId == room.getHost().getId() || memberId == room.getGuest().getId()) {
            return true;
        }
        return false;
    }

}
