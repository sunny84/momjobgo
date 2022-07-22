package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Recipe")
public class RecipeApiController {
    private RecipeService recipeService;

    public RecipeApiController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getByUser() { return ResponseEntity.ok().body( recipeService.findByWriter()); }

}
