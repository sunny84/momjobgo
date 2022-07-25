package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.dto.RecipeRequest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RecipeApiService {
    Map<String, Object> write(HttpServletRequest req, @RequestBody RecipeRequest request);

    String updateOpen(Long id);
}

