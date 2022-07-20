package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.CookingOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CookingOrderRepository extends JpaRepository<CookingOrderEntity, Long> {
    List<CookingOrderEntity> findByRecipeId(Long id);
}
