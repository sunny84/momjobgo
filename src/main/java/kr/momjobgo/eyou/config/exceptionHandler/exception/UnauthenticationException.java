package kr.momjobgo.eyou.config.exceptionHandler.exception;

public class UnauthenticationException extends RuntimeException{
    public UnauthenticationException(){
        super("해당 글에 권한이 없습니다.");
    }

    public UnauthenticationException(String message){
        super(message);
    }
}
