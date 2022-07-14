package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.jpa.entity.RecipeRecipeBoxEntity;
import kr.momjobgo.eyou.web.service.RecipeRecipeBoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reciperecipebox")
public class RecipeRecipeBoxController {
    private final RecipeRecipeBoxService recipeRecipeBoxService;

    public RecipeRecipeBoxController(RecipeRecipeBoxService recipeRecipeBoxService) { this.recipeRecipeBoxService = recipeRecipeBoxService; }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body( recipeRecipeBoxService.getAll() );
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> getRecipeRecipeBox(@PathVariable Long id) { return ResponseEntity.ok().body( recipeRecipeBoxService.getRecipeRecipeBox(id) ); }

    @GetMapping("/box={boxId}")
    public ResponseEntity<?> findByRecipeBoxId(@PathVariable Long boxId) { return ResponseEntity.ok().body( recipeRecipeBoxService.findByRecipeBoxId(boxId) ); }

    @PostMapping("/{boxId}")
    public ResponseEntity<?> insertRecipeBox(
            @PathVariable Long boxId,
            @RequestParam(value = "recipeId", required = false) Long recipeId,
            @RequestParam(value = "userId", required = false) Long userId
    ) { return ResponseEntity.ok().body( recipeRecipeBoxService.insertRecipeBox(boxId, recipeId, userId) ); }
}
