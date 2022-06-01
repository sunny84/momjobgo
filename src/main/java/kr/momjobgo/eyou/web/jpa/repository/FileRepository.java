package kr.momjobgo.eyou.web.jpa.repository;

import kr.momjobgo.eyou.web.jpa.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
