package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.dto.RecipeRequest;
import kr.momjobgo.eyou.web.service.RecipeApiService;
import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/Recipe")
public class RecipeApiController {

    private RecipeApiService recipeApiService;
    private RecipeService recipeService;

    public RecipeApiController(RecipeApiService recipeApiService, RecipeService recipeService) {
        this.recipeApiService = recipeApiService;
        this.recipeService = recipeService;
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getByUser() {
        return ResponseEntity.ok().body( recipeService.findByWriter());
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeRecipe(HttpServletRequest req, @RequestBody RecipeRequest request) {
        return ResponseEntity.ok().body(recipeApiService.write(req, request));
    }

    @PatchMapping("/updateOpen/{id}")
    public ResponseEntity<?> updateOpen(@PathVariable Long id) {
        return ResponseEntity.ok().body(recipeApiService.updateOpen(id));
    }
}
