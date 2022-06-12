package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.jpa.entity.SubscribeRecipeEntity;
import kr.momjobgo.eyou.web.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/subscribe")
public class SubscribeController {
    @GetMapping("/all")
    public List<SubscribeRecipeEntity> getAll(@AuthenticationPrincipal UserEntity entity){
        return null;
    }
}
