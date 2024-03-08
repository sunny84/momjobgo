package kr.momjobgo.eyou.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

@Component
@Aspect
@Slf4j
public class ParameterAspect {

    @Pointcut("execution(* kr.momjobgo.eyou.web.controller.*Controller.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.debug("{}", getPath(joinPoint));
        log.info("{} {}",getHttpServletRequest(request), getParams(joinPoint));
    }

    /**
     * 호출하는 메소드의 HttpServletRequest request param 출력
     * @param request
     */
    private String getHttpServletRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("method -> ");
        sb.append(request.getMethod());
        sb.append(", API -> ");
        sb.append(request.getRequestURI());
        sb.append(", Call ip -> ");
        sb.append(request.getRemoteAddr());
        sb.append(", params [");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()){
            String name = params.nextElement();
            sb.append(" ");
            sb.append(name);
            sb.append(" : ");
            sb.append(request.getParameter(name));
            if(params.hasMoreElements()) {
                sb.append(",");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * 호출하는 메소드의 경로
     * @param joinPoint
     * @return
     */
    private String getPath(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String type = signature.getDeclaringTypeName();

        StringBuilder sb = new StringBuilder();
        sb.append("path -> ");
        sb.append(type);
        sb.append(".");
        sb.append(method.getName());
        return sb.toString();
    }

    /**
     * 메소드의 모든 parameter 값을 문자열로 반환
     * @param joinPoint
     * @return
     */
    private String getParams(JoinPoint joinPoint) {
        Object[] parameterValues = joinPoint.getArgs();

        StringBuilder sb = new StringBuilder();
        sb.append("method params [");
        for (int i = 0; i < parameterValues.length; i++) {
            Object param = parameterValues[i];
            sb.append(i);
            sb.append(" : ");
            sb.append(param);
        }
        sb.append(" ]");

        return sb.toString();
    }

}
