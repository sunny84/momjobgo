package kr.momjobgo.eyou.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.momjobgo.eyou.web.jpa.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.

            if (StringUtils.isNotEmpty(jwt) && JwtTokenProvider.validateToken(jwt)) {

                String jsonInString = JwtTokenProvider.getUserIdFromJWT(jwt);

                UserEntity user = new ObjectMapper().readValue(jsonInString, UserEntity.class);

                UserManager.setUser(user, request);
            }

        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
