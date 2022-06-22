package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/ingredient")
    public ResponseEntity<?> joinIngredient() {
        return ResponseEntity.ok(recipeService.joinIngredient());
    }

    @GetMapping("/ingredient/args")
    public ResponseEntity<?> getPeriod(@RequestParam(value = "period") Long period, @RequestParam(value = "timeTakenId") Long timeTabkenId) {
        return ResponseEntity.ok().body( recipeService.findByPeriod(period) );
    }
}
