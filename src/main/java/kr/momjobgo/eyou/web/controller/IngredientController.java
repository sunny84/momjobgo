package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Ingredient")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/join/category")
    public ResponseEntity<?> joinCategory() {
        return ResponseEntity.ok(ingredientService.joinCategory());
    }

//    @GetMapping("/join/recipeMap")
//    public ResponseEntity<?> joinRecipeMap(@PathVariable List<Long> Ids) { return ResponseEntity.ok(ingredientService.joinRecipeMap(Ids)); }

    @GetMapping("/join/recipeMap")
    public ResponseEntity<?> joinRecipeMap(@RequestParam String ids) {
        String[] arr = ids.split(",");
        List<Long> Ids = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            Ids.add(Long.parseLong(arr[i]));
        }
        return ResponseEntity.ok(ingredientService.joinRecipeMap(Ids));
    }
}
