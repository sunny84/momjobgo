package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.service.ContentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contents")
public class ContentsApiController {
    private final ContentsService contentsService;

    public ContentsApiController(ContentsService contentsService) {
        this.contentsService = contentsService;
    }

    @GetMapping("")
    public ResponseEntity<?> getByUserId() {
        return ResponseEntity.ok().body( contentsService.getByUserId() );
    }
}
