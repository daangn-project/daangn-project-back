package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;

    public Photo findByFileId(Long id) {
        return photoRepository.findById(id).get();

    }
}
