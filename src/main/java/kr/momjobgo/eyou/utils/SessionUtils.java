package kr.momjobgo.eyou.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionUtils {
	
	/**
	 * 서버의 절대경로 가져오기
	 * @return
	 */
	public static String getRealPath()
	{
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		return sra.getRequest().getSession().getServletContext().getRealPath("");
	}
	
	/**
	 * 현재 request 정보 반환
	 * @return
	 */
	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return sra.getRequest();
	}
	
	/**
	 * 현재 response 정보 반환
	 * @return
	 */
	public static HttpServletResponse getCurrentResponse() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return sra.getResponse();
	}

}
