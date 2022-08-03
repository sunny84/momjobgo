package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

//    @Query(value = "SELECT f.id file_id, rrm.recipe_id subscribe, AVG(s.score) score, r.id, r.period, r.time_taken_id, c.title, c.sub_title FROM RECIPE r " +
//            "INNER JOIN CONTENTS c ON r.contents_id = c.id " +
//            "LEFT JOIN SCORE s ON r.id = s.recipe_id " +
//            "LEFT JOIN (SELECT recipe_id FROM RECIPE_RECIPEBOX_MAP " +
//                        "WHERE user_id = :userId " +
//                        "GROUP BY recipe_id) rrm ON r.id = rrm.recipe_id " +
//            "LEFT JOIN (SELECT id, contents_id FROM FILE " +
//                        "WHERE file_real_name LIKE BINARY 'M%') f ON f.contents_id = r.contents_id " +
//            "WHERE r.open = true " +
//                "AND (:period IS NULL OR r.period = :period) " +
//                "AND (:timeTakenId IS NULL OR r.time_taken_id = :timeTakenId) " +
//                "AND (:IdsCnt = :v OR r.id IN ( " +
//                    "SELECT recipe_id FROM RECIPE_INGREDIENT_MAP " +
//                    "WHERE ingredient_id IN (:Ids) " +
//                    "GROUP BY recipe_id " +
//                    "HAVING COUNT(DISTINCT ingredient_id) = :IdsCnt)) " +
//            "GROUP BY r.id " +
//            "ORDER BY (CASE " +
//                        "WHEN :sort IS NULL THEN c.updated_at " +
//                        "ELSE AVG(s.score) END) DESC",
//        nativeQuery = true)
    @Query(value = "SELECT f.id file_id, rrm.recipe_id subscribe, AVG(s.score) score, r.id, r.period, r.time_taken_id, c.title, c.sub_title FROM RECIPE r " +
            "INNER JOIN CONTENTS c ON r.contents_id = c.id " +
            "LEFT JOIN SCORE s ON r.id = s.recipe_id " +
            "LEFT JOIN (SELECT recipe_id FROM RECIPE_RECIPEBOX_MAP " +
                        "WHERE user_id = :userId " +
                        "GROUP BY recipe_id) rrm ON r.id = rrm.recipe_id " +
            "LEFT JOIN (SELECT id, contents_id FROM FILE " +
                        "WHERE file_real_name LIKE BINARY 'M%') f ON f.contents_id = r.contents_id " +
            "LEFT JOIN RECIPE_INGREDIENT_MAP rim ON rim.recipe_id = r.id " +
            "WHERE r.open = true " +
                "AND (:period IS NULL OR r.period = :period) " +
                "AND (:timeTakenId IS NULL OR r.time_taken_id <= :timeTakenId) " +
                "AND (:IdsCnt = 0 OR rim.ingredient_id IN (:Ids)) " +
            "GROUP BY r.id " +
//            "ORDER BY COUNT(DISTINCT rim.ingredient_id) DESC, " +
            "ORDER BY (CASE WHEN :IdsCnt = 0 THEN 0=0 " +
                            "ELSE COUNT(DISTINCT rim.ingredient_id) END) DESC, " +
                "(CASE " +
                    "WHEN :sort IS NULL THEN c.updated_at " +
                    "ELSE AVG(s.score) END) DESC",
            nativeQuery = true)
    List<Map<String, Object>> findByFilter(Pageable pageable, @Param("userId") Long userId, @Param("sort") Long sort, @Param("period") Long period, @Param("timeTakenId") Long timeTakenId, @Param("Ids") List<Long> Ids, @Param("IdsCnt") Long IdsCnt);
    List<RecipeEntity> findByContentsId(Long contentsId);

    Optional<RecipeEntity> findById(Long id);
}

