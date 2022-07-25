package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.dto.RecipeRequest;
import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RecipeService {
    List<Map<String, Object>> findByFilter(Pageable pageable, Long sort, Long period, Long timeTakenId, List<Long> Ids);
    List<RecipeEntity> getAll();

    List<Map<String, Object>> findByWriter();

    RecipeEntity getById(Long id);
    List<RecipeEntity> getByContentsId(Long contentsId);

    Map<String, Object> getDetailById(Long id);
}
