package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/join/ingredient")
    public ResponseEntity<?> joinIngredient() {
        return ResponseEntity.ok(recipeService.joinIngredient());
    }
}
