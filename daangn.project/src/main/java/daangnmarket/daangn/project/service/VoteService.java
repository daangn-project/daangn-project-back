package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.domain.vote.VoteOption;
import daangnmarket.daangn.project.dto.vote.VoteResponseDto;
import daangnmarket.daangn.project.dto.vote.VoteSaveDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import daangnmarket.daangn.project.repository.VoteOptionRepository;
import daangnmarket.daangn.project.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final VoteOptionRepository voteOptionRepository;
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
                .build();

        voteSaveDto.getVoteOptionCreateDtoList().forEach((vo) -> {
            VoteOption voteOption = VoteOption.builder()
                    .content(vo.getContent())
                    .build();
            vo.getImages().forEach((f) -> {
                try{
                    String S3Url = s3Uploader.upload(f, "static");
                    voteOption.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
                }catch (IOException e){
                    e.printStackTrace();
                }

            });
            voteOption.setVote(vote);
            voteOptionRepository.save(voteOption);
        });
        voteRepository.save(vote);
    }
}
