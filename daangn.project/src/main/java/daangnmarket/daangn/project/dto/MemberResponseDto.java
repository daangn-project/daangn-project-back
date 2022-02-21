package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Member;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberResponseDto {
    private String nickname;
    private String email;
    private List<ItemPostDetailResponseDto> itemPostResponseDtoList;

    public  MemberResponseDto(Member member){
        this.nickname = member.getNickname();
        this.email = member.getNickname();
        this.itemPostResponseDtoList = member.getItemPostList()
                .stream().map(ItemPostDetailResponseDto::new).collect(Collectors.toList());
    }
}

