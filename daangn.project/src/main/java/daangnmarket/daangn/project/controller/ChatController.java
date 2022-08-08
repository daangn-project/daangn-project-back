package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.auth.CurrentUser;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.dto.ChatDTO;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/rooms")
    public ResponseEntity<Message> chatRooms(@CurrentUser Member member, @RequestParam(required = false, defaultValue = "1") Integer page) {
        List<ChatDTO.ChatRoomDTO> chatRoomDtos = chatService.loadChatRooms(member,page - 1)
                .stream()
                .map(e -> new ChatDTO.ChatRoomDTO(e, member))
                .collect(Collectors.toList());
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("채팅 조회 결과입니다.")
                .data(chatRoomDtos)
                .build(), HttpStatus.OK);
    }

    // 채팅내용 출력
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<Message> chatMessages(@CurrentUser Member member, @PathVariable Long chatRoomId,
                                     @RequestParam(required = false, defaultValue = "1") Integer page) {
        List<ChatDTO.ChatMessageDTO> messages = chatService.loadMessages(member, chatRoomId, page - 1);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("채팅 내역입니다.")
                .data(messages)
                .build(), HttpStatus.OK);
    }

    // 채팅방 새로 생성
    @GetMapping("/create/{otherMemberId}")
    public ResponseEntity<Message> startChat(@CurrentUser Member member, @PathVariable Long otherMemberId) {
        Long roomId = chatService.createChatRoom(member, otherMemberId);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .data(String.format("/chat/{%d}", roomId))
                .build(), HttpStatus.OK);
    }

    // 메세지 보내기
    @PostMapping("/{chatRoomId}/send")
    public ResponseEntity<Message> sendMessage(@CurrentUser Member member, @PathVariable Long chatRoomId,
                                    @RequestBody HashMap<String, String> messageMap) {
        chatService.sendMessage(member, chatRoomId, messageMap.get("message"));
        return new ResponseEntity<>(Message.builder().build(), HttpStatus.OK);
    }

    // 대화방 나가기
    @GetMapping("/{chatRoomId}/delete")
    public ResponseEntity<Message> leaveChatRoom(@PathVariable Long chatRoomId) {
        chatService.leaveChatRoom(chatRoomId);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .data(String.format("/chat/rooms"))
                .build(), HttpStatus.OK);
    }
}
