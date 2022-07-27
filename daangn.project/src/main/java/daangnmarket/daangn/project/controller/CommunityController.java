package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.dto.CommunityDTO;

import daangnmarket.daangn.project.dto.EnumDTO;
import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.dto.utils.EnumManager;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {
    private final CommunityService communityService;
    private static final int PAGE_DEFAULT_SIZE = 10;

    // 카테고리 리스트 조회
    @GetMapping("/categories")
    public List<EnumDTO> categoryList(){
        Class<? extends EnumManager> e = CommunityCategory.class;
        return Arrays
                .stream(e.getEnumConstants())
                .map(EnumDTO::new)
                .collect(Collectors.toList());
    }

    // 생성
    @PostMapping("")
    public ResponseEntity<Message> communityCreate(@ModelAttribute CommunityDTO.SaveDTO saveDTO) throws IOException {
        communityService.save(saveDTO);
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("동네 생활이 생성되었어요.").data(saveDTO).build(), HttpStatus.OK);
    }

    // 조회
    @GetMapping("")
    public ResponseEntity<Message> communityList(Long cursor) {
        List<CommunityDTO.ResponseDTO> communityResponseDtoList = communityService.findCommunityByPage(cursor, PageRequest.of(0,  PAGE_DEFAULT_SIZE));
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("전체 동네 생활 조회 결과입니다.")
                .data(communityResponseDtoList)
                .build(), HttpStatus.OK);
    }

    // 상세 페이지
    @GetMapping("/{id}")
    public ResponseEntity<Message> communityDetails(@PathVariable String id) {
        CommunityDTO.ResponseDTO communityResponseDto = communityService.findById(Long.parseLong(id));
        communityResponseDto.sortCommentsByParentOrderThenCommentOrder();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(communityResponseDto)
                .build(), HttpStatus.OK);
    }

    // 카테고리에 해당하는 모든 CommunityPost 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<Message> communityListByCategory(@PathVariable String category) {
        List<CommunityDTO.ResponseDTO> communityResponseDtoList = communityService.findByCategory(category);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message(category + "카테고리에 대한 동네 생활 조회 결과입니다.")
                .data(communityResponseDtoList)
                .build(), HttpStatus.OK);
    }




//    // 동네 생활 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<Message> communityModify(@PathVariable Long id, @ModelAttribute CommunityPostFileVO communityPostFileVO) {
//        CommunitySaveDto communitySaveDto = communityService.update(id, communityPostFileVO);
//        return new ResponseEntity<>(Message.builder()
//                .status(StatusEnum.OK)
//                .message("동네 생활을 수정했어요.")
//                .data(communitySaveDto)
//                .build(), HttpStatus.OK);
//    }

    // 동네 생활삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> communityRemove(@PathVariable Long id) {
        try {
            communityService.delete(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
