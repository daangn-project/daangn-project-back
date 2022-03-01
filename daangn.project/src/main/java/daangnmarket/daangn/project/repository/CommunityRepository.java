package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.CommunityCategory;
import daangnmarket.daangn.project.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query(value = "select i from Community i where i.communityCategory = :category")
    List<Community> findByCategory(@Param(value="category") CommunityCategory category);
}
