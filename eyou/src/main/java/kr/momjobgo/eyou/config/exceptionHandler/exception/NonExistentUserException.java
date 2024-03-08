package kr.momjobgo.eyou.config.exceptionHandler.exception;

public class NonExistentUserException extends RuntimeException{

    public NonExistentUserException(){
        super("존재하지 않는 사용자 입니다.");
    }

    public NonExistentUserException(String message){
        super(message);
    }
}
