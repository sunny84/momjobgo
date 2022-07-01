package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.ContentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<ContentsEntity, Long> {
}
