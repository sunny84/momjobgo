package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long> {
    List<SubscribeEntity> findByName(String name);

    List<SubscribeEntity> findByNameContains(String name);

//    List<SubscribeEntity> findByUserId(Long id);

}
