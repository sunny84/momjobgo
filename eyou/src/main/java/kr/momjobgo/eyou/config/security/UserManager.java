package kr.momjobgo.eyou.config.security;

import kr.momjobgo.eyou.web.jpa.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class UserManager {

    public static void setUser(UserEntity user, HttpServletRequest request){
        UserAuthentication authentication = new UserAuthentication(user.getSnsId(), user, null); //id를 인증한다.
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //기본적으로 제공한 details 세팅
        SecurityContextHolder.getContext().setAuthentication(authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
    }

    public static UserEntity getUser(){
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    public static boolean isCurrentUser(String id){
        return getUser().getSnsId().equals(id);
    }

}
