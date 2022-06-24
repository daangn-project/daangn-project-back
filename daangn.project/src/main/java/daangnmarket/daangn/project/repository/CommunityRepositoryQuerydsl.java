package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.community.Community;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityRepositoryQuerydsl {
    List<Community> findByOrderByIdDescWithList(Pageable pageable);
}
