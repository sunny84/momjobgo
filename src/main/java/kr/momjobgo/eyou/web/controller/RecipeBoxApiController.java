package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipebox")
public class RecipeBoxApiController {
    private final RecipeBoxService recipeBoxService;

    public RecipeBoxApiController(RecipeBoxService recipeBoxService) { this.recipeBoxService = recipeBoxService; }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body( recipeBoxService.getAll() );
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getReceipeBoxList() {
        return ResponseEntity.ok().body( recipeBoxService.getReceipeBoxList() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) { return ResponseEntity.ok().body( recipeBoxService.getById(id) ); }

    @PostMapping("/{name}")
    public ResponseEntity<?> insertRecipeBoxName(
            @PathVariable String name){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeBoxService.insertRecipeBoxName(name));
    }

    @PostMapping("/default")
    public ResponseEntity<?> insertRecipeBoxDefault(){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeBoxService.insertRecipeBox());
    }

    @GetMapping("/default")
    public ResponseEntity<?> getRecipeBoxDefault(){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeBoxService.findByIsDefault());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRecipeBoxTest(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false)  String name){
        return ResponseEntity.ok().body(recipeBoxService.updateRecipeBox(id, name));
    }

        @DeleteMapping("/{id}")    // id에 해당되는 데이터 삭제. 없는 id일 경유 500 에러 발생
        public ResponseEntity<?> deleteRecipeBoxById(@PathVariable Long id) {
            return ResponseEntity.ok().body(recipeBoxService.deleteRecipeBoxById(id));
        }

//        @DeleteMapping("/{name}")
//        public ResponseEntity<?> deleteRecipeBoxByName(@PathVariable String name) {
//            return ResponseEntity.ok().body(recipeBoxService.deleteRecipeBoxByName(name));
//        }

        @DeleteMapping("/all")
        public ResponseEntity<?> deleteRecipeBoxAll() {
            return ResponseEntity.ok().body(recipeBoxService.deleteRecipeBoxAll());
        }

//        @GetMapping("/{name}")
//        public ResponseEntity<?> getRecipeBoxByName(@PathVariable String name){
//            return ResponseEntity.ok().body(recipeBoxService.findByName(name));
//        }

        @GetMapping("/contains?name={name}")
        public ResponseEntity<?> getRecipeBoxByNameContains(@RequestParam String name){
            return ResponseEntity.ok().body(recipeBoxService.findByNameContains(name));
        }
}

