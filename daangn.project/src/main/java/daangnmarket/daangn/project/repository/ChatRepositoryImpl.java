package daangnmarket.daangn.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import daangnmarket.daangn.project.domain.chat.ChatMessage;
import daangnmarket.daangn.project.domain.chat.ChatRoom;
import daangnmarket.daangn.project.domain.chat.QChatMessage;
import daangnmarket.daangn.project.domain.chat.QChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static daangnmarket.daangn.project.domain.chat.QChatMessage.chatMessage;
import static daangnmarket.daangn.project.domain.chat.QChatRoom.chatRoom;

@Repository
public class ChatRepositoryImpl implements ChatRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ChatRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Long saveChatRoom(ChatRoom chatRoom) {
        entityManager.persist(chatRoom);
        return chatRoom.getId();
    }

    public void saveContent(ChatMessage chatMessage) {
        entityManager.persist(chatMessage);
    }

    // 채팅방
    public ChatRoom findRoomById(Long chatRoomId) {
        return queryFactory.selectFrom(chatRoom)
                .join(chatRoom.host).fetchJoin()
                .join(chatRoom.guest).fetchJoin()
                .where(chatRoom.id.eq(chatRoomId))
                .fetchOne();
    }

    // 채팅방
    public ChatRoom findRoomByMemberId(Long fromMemberId, Long toMemberId) {
        Long[] ids = {fromMemberId, toMemberId};
        return queryFactory.selectFrom(chatRoom)
                .where(chatRoom.host.id.in(ids),
                        chatRoom.guest.id.in(ids))
                .fetchOne();
    }

    // 채팅 내용
    public List<ChatMessage> findChatContents(Long chatRoomId, Integer page) {
        return queryFactory.selectFrom(chatMessage)
                .join(chatMessage.fromMember).fetchJoin()
                .where(chatMessage.chatRoom.id.eq(chatRoomId))
                .orderBy(chatMessage.createdTime.desc())
                .limit(51)
                .offset(50 * page)
                .fetch();
    }

    // 채팅방 목록
    public List<ChatRoom> findRooms(Long hostOrGuestId, Integer page) {
        return queryFactory.selectFrom(chatRoom)
                .join(chatRoom.host).fetchJoin()
                .join(chatRoom.guest).fetchJoin()
                .where(chatRoom.host.id.eq(hostOrGuestId)
                        .or(chatRoom.guest.id.eq(hostOrGuestId)))
                .orderBy(chatRoom.updateTime.desc())
                .limit(11)
                .offset(10 * page)
                .fetch();
    }

    // 채팅방 및 채팅 내용 삭제
    public void deleteRoom(Long chatRoomId) {
        ChatRoom room = findRoomById(chatRoomId);
        entityManager.remove(room);
    }
}
