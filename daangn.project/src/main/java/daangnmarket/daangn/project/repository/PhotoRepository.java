package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByItemPostId(Long id);
}
