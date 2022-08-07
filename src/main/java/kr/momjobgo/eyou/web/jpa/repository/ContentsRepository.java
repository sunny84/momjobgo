package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.ContentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContentsRepository extends JpaRepository<ContentsEntity, Long> {
    List<ContentsEntity> findByWriter(Long writer);

    @Query(value = "SELECT c.title, c.sub_title FROM CONTENTS c " +
            "INNER JOIN RECIPE r ON r.contents_id = c.id " +
            "WHERE r.id = :recipeId", nativeQuery = true)
    Map<String, String> getContentsByRecipeId(@Param("recipeId") Long recipeId);

    List<ContentsEntity> findByWriterAndCategory(Long writer, Long category);
}
