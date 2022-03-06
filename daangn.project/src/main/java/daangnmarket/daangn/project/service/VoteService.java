package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.domain.vote.VoteOption;
import daangnmarket.daangn.project.domain.vote.VoteResult;
import daangnmarket.daangn.project.dto.vote.VoteParticipateDto;
import daangnmarket.daangn.project.dto.vote.VoteResponseDto;
import daangnmarket.daangn.project.dto.vote.VoteResultResponseDto;
import daangnmarket.daangn.project.dto.vote.VoteSaveDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.*;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import daangnmarket.daangn.project.repository.VoteOptionRepository;
import daangnmarket.daangn.project.repository.VoteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final VoteResultRepository voteResultRepository;
    private final S3Uploader s3Uploader;

    public Vote findById(Long id) {
        return voteRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 투표가 존재하지 않습니다"));
    }

    public void save(VoteSaveDto voteSaveDto)  throws IOException{
        Member member = memberRepository.findByNickname(voteSaveDto.getWriter()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        Vote vote = Vote.builder()
                .member(member)
                .title(voteSaveDto.getTitle())
                .description(voteSaveDto.getDescription())
                .isMultipleVote(voteSaveDto.getIsMultipleVote())
                .build();

        voteSaveDto.getImages().forEach((f) -> {
            try {
                String S3Url = s3Uploader.upload(f, "static");
                vote.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        voteSaveDto.getVoteOptionCreateDtoList().forEach((vo) -> {
            VoteOption voteOption = VoteOption.builder()
                    .content(vo.getContent())
                    .build();
            voteOption.setVote(vote);
            voteOptionRepository.save(voteOption);
        });
        voteRepository.save(vote);
    }

    public void getResultOfVote(VoteResponseDto voteResponseDto){
        List<VoteResultResponseDto> collectedVoteResult = voteResponseDto.getVoteOptionResponseDtos().stream().map(
                (e) -> {
                    Long voteOptionId = e.getId();
                    List<Long> participants = voteResultRepository.findAllMemberByVoteOptionId(voteOptionId)
                            .stream().map(Member::getId).collect(Collectors.toList());
                    return new VoteResultResponseDto(voteOptionId, participants);
                }
        ).collect(Collectors.toList());
        voteResponseDto.setVoteResultResponseDtos(collectedVoteResult);
    }


    public boolean participate(Long voteId, VoteParticipateDto voteParticipateDto) {
        // 이미 투표를 한 회원인지 확인
        Member m = memberRepository.findByNickname(voteParticipateDto.getParticipantName()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));
        if(isParticipatedMember(voteId, m)){
            return false;
        }
        VoteResult voteResult = VoteResult.builder()
                .voteOption(voteOptionRepository.findById(voteParticipateDto.getVoteOptionId()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 항목입니다.")))
                .member(m)
                .build();
        voteResultRepository.save(voteResult);
        return true;
    }

    public boolean isParticipatedMember(Long voteId, Member m){
        // 이미 투표를 한 회원인지 확인하는 로직
        List<VoteResult> voteResultList = voteResultRepository.findByVoteId(voteId);
        for (VoteResult voteResult : voteResultList) {
            if (voteResult.getMember().equals(m))
                return true;
        }
        return false;
    }

}
