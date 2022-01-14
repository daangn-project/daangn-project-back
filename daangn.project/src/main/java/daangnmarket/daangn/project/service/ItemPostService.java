package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.repository.ItemPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemPostService {
    private final ItemPostRepository itemPostRepository;

    public ResponseEntity<Message> save(ItemPostSaveDto itemPostSaveDto){
        ItemPost itemPost = itemPostSaveDto.toEntity();
        ItemPost savedPost = itemPostRepository.save(itemPost);
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("게시물이 등록되었어요.").build(), HttpStatus.OK);
    }
}
