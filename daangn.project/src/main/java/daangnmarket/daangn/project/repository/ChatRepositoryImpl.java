package daangnmarket.daangn.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import daangnmarket.daangn.project.domain.chat.*;
import daangnmarket.daangn.project.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static daangnmarket.daangn.project.domain.chat.QChatMessage.chatMessage;
import static daangnmarket.daangn.project.domain.chat.QChatRoom.chatRoom;
import static daangnmarket.daangn.project.domain.chat.QChatRoomHistory.chatRoomHistory;

@Repository
public class ChatRepositoryImpl implements ChatRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ChatRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Transactional
    public Long saveChatRoom(ChatRoom chatRoom) {
        entityManager.persist(chatRoom);
        return chatRoom.getId();
    }

    @Transactional
    public Long saveChatRoomHistory(ChatRoomHistory history) {
        entityManager.persist(history);
        return history.getId();
    }

    @Transactional
    public void saveContent(ChatMessage chatMessage) {
        entityManager.persist(chatMessage);
    }

    @Transactional
    public void updateChatRoomHistory(Long memberId, Long chatRoomId) {
        ChatRoomHistory history = queryFactory.selectFrom(chatRoomHistory)
                .where(chatRoomHistory.member.id.eq(memberId),
                        chatRoomHistory.chatRoom.id.eq(chatRoomId))
                .fetchOne();
        history.updateLatestVisitTime(LocalDateTime.now());
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
    public List<ChatRoom> findRooms(Long memberId, Integer page) {
        return queryFactory.selectFrom(chatRoom)
                .join(chatRoom.host).fetchJoin()
                .join(chatRoom.guest).fetchJoin()
                .where(chatRoom.host.id.eq(memberId).or(chatRoom.guest.id
                        .eq(memberId)))
                .orderBy(chatRoom.modifiedTime.desc())
                .limit(11)
                .offset(10 * page)
                .fetch();
    }

    // 채팅방 및 채팅 내용 삭제
    @Transactional
    public void deleteRoom(Long chatRoomId) {
        ChatRoom room = findRoomById(chatRoomId);
        entityManager.remove(room);
    }

    public ChatRoomHistory findChatRoomHistoryByChatRoomAndMember(Long chatRoomId, Member member) {
        return queryFactory.selectFrom(chatRoomHistory)
                .leftJoin(chatRoomHistory.chatRoom, chatRoom)
                .where(chatRoomHistory.chatRoom.id.eq(chatRoomId),
                        chatRoomHistory.member.id.eq(member.getId()))
                .fetchOne();
    }

    public Integer countMessagesByCreatedTimeAfter(Long chatRoomId, LocalDateTime lastVisitTime){
        List<ChatMessage> messages = queryFactory
                .selectFrom(chatMessage)
                .where(chatMessage.chatRoom.id.eq(chatRoomId),
                        chatMessage.createdTime.after(lastVisitTime))
                .fetch();
        return messages.size();
    }
}
