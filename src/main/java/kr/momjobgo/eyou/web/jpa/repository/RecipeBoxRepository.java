package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
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

    List<RecipeBoxEntity> findByUserId(Long userId,
                                       @PageableDefault(size=5, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable);

//    List<RecipeBoxEntity> findAllByOrderByOrderByCreatedAtDesc();

    List<RecipeBoxEntity> findAllByOrderByCreatedAtDesc();
//    List<RecipeBoxEntity> findAll(Long userId);
}
