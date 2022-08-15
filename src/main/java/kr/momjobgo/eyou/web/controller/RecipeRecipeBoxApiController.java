package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.service.RecipeRecipeBoxService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reciperecipebox")
public class RecipeRecipeBoxApiController {
    private final RecipeRecipeBoxService recipeRecipeBoxService;

    public RecipeRecipeBoxApiController(RecipeRecipeBoxService recipeRecipeBoxService) { this.recipeRecipeBoxService = recipeRecipeBoxService; }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body( recipeRecipeBoxService.getAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeRecipeBox(@PathVariable Long id) { return ResponseEntity.ok().body( recipeRecipeBoxService.getRecipeRecipeBox(id) ); }

    @GetMapping("")
    public ResponseEntity<?> findByRecipeBoxId(@RequestParam(value = "box", required = false) Long boxId) {

        System.out.println("===> user ID : "+ UserManager.getUser().getId());

        return ResponseEntity.ok().body( recipeRecipeBoxService.findByRecipeBoxId(boxId) );
    }

    @GetMapping("/recipe")
    public ResponseEntity<?> findByRecipeContents(@RequestParam(value = "box", required = false) Long boxId,
                                                  @RequestParam(value = "page") int page) {
        return ResponseEntity.ok().body( recipeRecipeBoxService.findByRecipeContents(boxId, PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"Id")) ));
    }

    @GetMapping("/recipe/mine")
    public ResponseEntity<?> findByRecipeContentsByUserId() {
        return ResponseEntity.ok().body( recipeRecipeBoxService.findByRecipeContentsByUserId() );
    }

    @PostMapping("")
    public ResponseEntity<?> insertRecipeBox(
            @RequestParam(value = "box", required = false) Long boxId,
            @RequestParam(value = "recipe", required = false) Long recipeId
    ) { return ResponseEntity.ok().body( recipeRecipeBoxService.insertRecipeBox(boxId, recipeId) ); }

    @GetMapping("/subscribe")
    public ResponseEntity<?> getSubscribeList(
            @RequestParam(value = "period", required = false) Long period) {
        Long userId = UserManager.getUser().getId();
//        System.out.println("===> user ID : "+ userId);
        return ResponseEntity.ok().body(recipeRecipeBoxService.getSubscribeList(userId, period));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> moveRecipeBox(
            @PathVariable Long id,
            @RequestParam(value = "recipe", required = false) Long recipeId,
            @RequestParam(value = "to", required = false) Long toBoxId
    ) {
        return ResponseEntity.ok().body( recipeRecipeBoxService.moveRecipeBox(id, recipeId, toBoxId) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(
            @PathVariable Long id,
            @RequestParam(value = "recipe", required = false) Long recipeId) {
        return ResponseEntity.ok().body(recipeRecipeBoxService.deleteRecipe(id, recipeId));
    }
}
