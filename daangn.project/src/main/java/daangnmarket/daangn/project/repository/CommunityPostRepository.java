package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.CommunityCategory;
import daangnmarket.daangn.project.domain.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    @Query(value = "select i from CommunityPost i where i.communityCategory = :category")
    List<CommunityPost> findByCategory(@Param(value="category") CommunityCategory category);
}
