package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.dto.RecipeRequest;
import kr.momjobgo.eyou.web.service.RecipeApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/recipe")
public class RecipeApiController {

    private RecipeApiService recipeApiService;

    public RecipeApiController(RecipeApiService recipeApiService) {
        this.recipeApiService = recipeApiService;
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeRecipe(HttpServletRequest req, @RequestBody RecipeRequest request) {
        return ResponseEntity.ok().body(recipeApiService.write(req, request));
    }

    @PatchMapping("/updateOpen={id}")
    public ResponseEntity<?> updateOpen(Long id) {
        return ResponseEntity.ok().body(recipeApiService.updateOpen(id));
    }
}
