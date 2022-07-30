package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityComment;
import daangnmarket.daangn.project.dto.CommentDTO;
import daangnmarket.daangn.project.repository.CommunityCommentRepository;
import daangnmarket.daangn.project.repository.CommunityRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommunityCommentRepository communityCommentRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;

    public CommentDTO.ResponseDTO save(CommentDTO.SaveDTO commentSaveDto) {
        System.out.println("SecurityUtil.getCurrentUsername() = " + SecurityUtil.getCurrentUsername());

        Member member = memberRepository.findByUsername(commentSaveDto.getWriter()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Community community = communityRepository.findById(commentSaveDto.getCommunityId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        commentSaveDto.setCommentOrder(communityCommentRepository.getLastCommentOrder() + 1);

        // 부모 댓글은 부모 번호를 자신의 댓글 번호로 한다.
        if (commentSaveDto.getParentCommentNum() == -1) {
            commentSaveDto.setParentCommentNum(commentSaveDto.getCommentOrder());
//            log.info("부모 댓글을 생성합니다. parentCommentNum = {}", commentSaveDto.getCommentOrder());
        } else {
            long parentCommentNum = commentSaveDto.getParentCommentNum();
            // 자식 댓글이라면, 부모 댓글의 자식 수를 증가시킨다.
            CommunityComment parentComm = communityCommentRepository.findByCommentOrder(parentCommentNum);
            parentComm.plusChildCommentCount();
//            log.info("자식 댓글을 생성합니다. parentCommentNum = {}, parentChildCommentCount = {}", parentComm.getParentCommentNum(), parentComm.getChildCommentCount());
        }
        return new CommentDTO.ResponseDTO(communityCommentRepository.save(commentSaveDto.toEntity(member, community)));
    }

    @Transactional
    public void delete(Long id) {
        CommunityComment comment = communityCommentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        // 자식 댓글을 삭제하면 부모 댓글의 자식 숫자를 감소시킨다.
        if (!comment.getCommentOrder().equals(comment.getParentCommentNum())) {
            CommunityComment parentComm = communityCommentRepository.findByCommentOrder(comment.getParentCommentNum());
            parentComm.minusChildCommentCount();
            // 자식 댓글이 없고, isDeleted = true인 부모 댓글은 삭제시킨다.
            if(parentComm.getChildCommentCount() == 0 && parentComm.getIsDeleted())
                communityCommentRepository.delete(parentComm);
        }
        communityCommentRepository.delete(comment);
        log.info("자식 댓글을 삭제합니다.");
    }

    @Transactional
    public void deleteByMessage(Long id) {
        CommunityComment comment = communityCommentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.setDeleted();
    }
}
