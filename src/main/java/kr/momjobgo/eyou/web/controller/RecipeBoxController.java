package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipebox")
public class RecipeBoxController {
    private final RecipeBoxService recipeBoxService;

    public RecipeBoxController(RecipeBoxService recipeBoxService) { this.recipeBoxService = recipeBoxService; }

    @GetMapping("/join/recipe")
    public ResponseEntity<?> joinRecipe() { return ResponseEntity.ok(recipeBoxService.joinRecipe()); }
}
