package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.jpa.entity.ContentsEntity;
import kr.momjobgo.eyou.web.jpa.entity.TestEntity;
import kr.momjobgo.eyou.web.service.ContentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contents")
public class ContentsController {

    private ContentsService contentsService;

    public ContentsController(ContentsService contentsService) {
        this.contentsService = contentsService;
    }

    @PostMapping("/contents")   // insert contents
    public ResponseEntity<?> insertContent(@RequestBody ContentsEntity contentsEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(contentsService.insertContent(contentsEntity));
    }
}
