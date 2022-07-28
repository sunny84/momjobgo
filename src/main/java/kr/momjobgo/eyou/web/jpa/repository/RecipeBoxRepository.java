package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipeBoxRepository extends JpaRepository<RecipeBoxEntity, Long> {
    List<RecipeBoxEntity> findByName(String name);

    List<RecipeBoxEntity> findByNameContains(String name);

    RecipeBoxEntity findByIsDefaultAndUserId(Boolean isDefault, Long userId);

    @Transactional
    void deleteByName(String name);

    List<RecipeBoxEntity> findByUserId(Long userId);
}
