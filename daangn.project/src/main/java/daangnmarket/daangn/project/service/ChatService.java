package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.auth.CurrentUser;
import daangnmarket.daangn.project.domain.chat.ChatMessage;
import daangnmarket.daangn.project.domain.chat.ChatRoom;
import daangnmarket.daangn.project.domain.chat.ChatRoomHistory;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.dto.ChatDTO;
import daangnmarket.daangn.project.repository.ChatRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public Long createChatRoom(Member host, Long guestMemberId){
        // 이미 존재하는 채팅방있으면 채팅방 id 리턴
        ChatRoom foundRoom = chatRepository.findRoomByMemberId(host.getId(), guestMemberId);
        if (foundRoom != null) {
            return foundRoom.getId();
        }
        // 상대방이 유효한지 체크
        Member guestMember = memberRepository.findMemberById(guestMemberId);

        // 채팅방 없으면 생성
        ChatRoom room = ChatRoom.builder().host(host).guest(guestMember).build();

        // 최초 채팅방 방문 시간 생성
        ChatRoomHistory chatRoomHistoryForHost = ChatRoomHistory.builder().
                member(host).chatRoom(room).latestVisitTime(LocalDateTime.now()).build();

        ChatRoomHistory chatRoomHistoryForGuest = ChatRoomHistory.builder().
                member(guestMember).chatRoom(room).latestVisitTime(LocalDateTime.now()).build();

        Long savedChatRoomId = chatRepository.saveChatRoom(room);
        chatRepository.saveChatRoomHistory(chatRoomHistoryForHost);
        chatRepository.saveChatRoomHistory(chatRoomHistoryForGuest);

        return savedChatRoomId;
    }

    // 채팅방 목록, page당 10개, 마지막 1개는 다음페이지 확인용
    @Transactional(readOnly = true)
    public List<ChatRoom> loadChatRooms(Member member, Integer page) {
        return chatRepository.findRooms(member.getId(), page);
    }

    @Transactional
    public ChatDTO.ChatRoomDTO countUnreadMessages(ChatDTO.ChatRoomDTO chatRoomDTO, Member member) {
        Long chatRoomId = chatRoomDTO.getChatRoomId();
        ChatRoomHistory history = chatRepository.findChatRoomHistoryByChatRoomAndMember(chatRoomId, member);
        LocalDateTime lastVisitTime = history.getLatestVisitTime();
        chatRoomDTO.setUnreadMessageCount(chatRepository.countMessagesByCreatedTimeAfter(chatRoomId, lastVisitTime));
        return chatRoomDTO;
    }

    // 채팅 내용 가져오기
    public List<ChatDTO.ChatMessageDTO> loadMessages(Member member, Long chatRoomId, Integer page) {
//        if (!isParticipant(chatRoomId)) {
//            throw new CustomException(2002, "잘못된 접근입니다.");
//        }

        Long clientMemberId = member.getId();
        ChatRoom room = chatRepository.findRoomById(chatRoomId);
        // '읽음' 상태로 변경
        if (clientMemberId.equals(room.getHost().getId())) {
            room.setHostReadStatus(true);
        } else {
            room.setGuestReadStatus(true);
        }

        // 자신의 최근 채팅방 방문 시간 업데이트
        chatRepository.updateChatRoomHistory(clientMemberId, chatRoomId);

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
    public void sendMessage(Member member, Long chatRoomId, String message) {
//        if (!isParticipant(chatRoomId)) {
//            throw new CustomException(2003, "잘못된 접근입니다.");
//        }
        ChatRoom room = chatRepository.findRoomById(chatRoomId);

        ChatMessage chatMessage = ChatMessage.builder()
                .message(message).chatRoom(room).build();

        room.setLatestMessage(message);
        room.getChatMessageList().add(chatMessage);

        // 메세지의 보낸사람, 받는사람 설정 + 채팅방 읽음 상태 설정
        Long memberId = member.getId();
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
    private Boolean isParticipant(@CurrentUser Member member, Long chatRoomId) {
        Long memberId = member.getId();
        ChatRoom room = chatRepository.findRoomById(chatRoomId);
        if (memberId == room.getHost().getId() || memberId == room.getGuest().getId()) {
            return true;
        }
        return false;
    }

}
