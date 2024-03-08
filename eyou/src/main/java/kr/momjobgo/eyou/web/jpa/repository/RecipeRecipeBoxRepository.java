package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeRecipeBoxEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RecipeRecipeBoxRepository extends JpaRepository<RecipeRecipeBoxEntity, Long> {
    List<RecipeRecipeBoxEntity> findByRecipeId(Long id);
    List<RecipeRecipeBoxEntity> findByRecipeBoxId(Long boxId);
    List<RecipeRecipeBoxEntity> findByUserId(Long userId);
    List<RecipeRecipeBoxEntity> findRecipesByRecipeBoxId(Long boxId);
//    SELECT * FROM eyou.RECIPE_RECIPEBOX_MAP WHERE recipe_box_id = 22 AND recipe_id = 8 AND user_id = 1
    Optional<RecipeRecipeBoxEntity> findByRecipeBoxIdAndRecipeIdAndUserId(@Param("recipeBox") Long boxId, @Param("recipe") Long recipeId, @Param("user") Long userId);
    List<RecipeRecipeBoxEntity> findByRecipeBoxIdAndUserId(Long boxId, Long userId,
                                                           @PageableDefault(size=5, sort="Id", direction = Sort.Direction.DESC) Pageable pageable);

    @Query(value = "SELECT r.id, r.period, r.time_taken_id, AVG(s.score) score, c.title, f.id file_id FROM RECIPE_RECIPEBOX_MAP rrm " +
            "INNER JOIN RECIPE r ON r.id = rrm.recipe_id " +
            "INNER JOIN CONTENTS c ON c.id = r.contents_id " +
            "LEFT JOIN SCORE s ON r.id = s.recipe_id " +
            "LEFT JOIN (SELECT id, contents_id FROM FILE " +
            "WHERE file_real_name LIKE BINARY 'M%') f ON f.contents_id = r.contents_id " +
            "WHERE rrm.user_id = :userId " +
            "AND r.open  = true " +
            "AND (:period IS NULL OR r.period = :period) " +
            "GROUP BY rrm.recipe_id " +
            "LIMIT 5",
            nativeQuery = true)
    List<Map<String, Object>> getSubscribeList(@Param("userId") Long userId, @Param("period") Long period);

    List<RecipeRecipeBoxEntity> findByRecipeAndUserId(Long recipeId, Long userId);
//
//    List<RecipeRecipeBoxEntity> findByUserIdByOrderByRecipeIdDesc(Long id);
//
//    List<RecipeRecipeBoxEntity> findByUserIdAndOrderByIdDesc(Long id);
//
//    List<RecipeRecipeBoxEntity> findByOrderByIdDesc();
}
