package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    //    @Query("select r from RECIPE r where r.open = true and r.period = :period and r.timeTakenId = :timeTakenId")
    List<RecipeEntity> findByPeriod(Long period);

}

