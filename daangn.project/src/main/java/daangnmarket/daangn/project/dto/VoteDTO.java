package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.domain.vote.VoteOption;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class VoteDTO {

    @Data
    public static class OptionSaveDTO {
        private String content;
    }

    @Data
    public static class OptionResponseDTO {
        private Long id;
        private String content;

        public OptionResponseDTO(VoteOption voteOption){
            this.id = voteOption.getId();
            this.content = voteOption.getContent();
        }
    }

    @Data
    public static class ParticipateDTO {
        private Long voteOptionId;
        private String participantName;
    }

    @Data
    @RequiredArgsConstructor
    public static class InfoResponseDTO {
        private boolean isMultipleVote;
        private List<OptionResponseDTO> voteOptionResponseDtos;
        private List<ResultResponseDTO> voteResultResponseDtos;

        public InfoResponseDTO(Vote vote){
            this.isMultipleVote = vote.getIsMultipleVote();
            this.voteOptionResponseDtos = vote.getVoteOptionList().stream().map(
                    OptionResponseDTO::new).collect(Collectors.toList());
        }
    }

    @Data
    public static class ResultResponseDTO {
        private Long voteOptionId;
        private List<Long> memberIdList;

        public ResultResponseDTO(Long id, List<Long> memberIdList){
            this.voteOptionId = id;
            this.memberIdList = memberIdList;
        }
    }

    @Data
    public static class SaveDTO {
        private Boolean isMultipleVote;
        private List<String> voteOptions;

        public SaveDTO(Boolean isMultipleVote, List<String> voteOptions) {
            this.isMultipleVote = isMultipleVote;
            this.voteOptions = voteOptions;
        }
    }

    @Data
    public static class UpdateDTO{

    }

}
