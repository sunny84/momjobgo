package kr.momjobgo.eyou.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.momjobgo.eyou.web.common.FileService;
import kr.momjobgo.eyou.web.dto.Token;
import kr.momjobgo.eyou.web.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(AuthController.PREFIX)
public class AuthController {

    public static final String PREFIX = "/user";

    private final UserService userService;
    private final FileService fileService;

    public AuthController(UserService userService, FileService fileService){
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Token.TokenRequest request) throws JsonProcessingException {
        return ResponseEntity.ok().body(userService.login(request));
    }


    @PostMapping("/upload")
    public ResponseEntity<?> upload(HttpServletRequest req) {


        fileService.upload(req).forEach(System.out::println);

        return ResponseEntity.ok().body(null);
    }

}
