package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.RecipeIngredientMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientMapRepository extends JpaRepository<RecipeIngredientMapEntity, Long> {

}
