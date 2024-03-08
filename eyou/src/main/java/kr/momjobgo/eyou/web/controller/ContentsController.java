package kr.momjobgo.eyou.web.controller;


import kr.momjobgo.eyou.web.service.ContentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contents")
public class ContentsController {
    private final ContentsService contentsService;

    public ContentsController(ContentsService contentsService) { this.contentsService = contentsService; }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body( contentsService.getAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) { return ResponseEntity.ok().body( contentsService.getById(id) ); }

    @GetMapping("/writer={writer}")
    public ResponseEntity<?> getByWriter(@PathVariable Long writer) { return ResponseEntity.ok().body( contentsService.getByWriter(writer) ); }

    @GetMapping("/join/Recipe")
    public ResponseEntity<?> getContentsByRecipeId(@RequestParam(value = "recipeId") Long recipeId){
        return ResponseEntity.ok().body( contentsService.getContentsByRecipeId(recipeId));
    }
}
