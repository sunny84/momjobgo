package kr.momjobgo.eyou.config.exceptionHandler.exception;

public class AlreadyExistAccountException extends RuntimeException{

    public AlreadyExistAccountException(){
        super("이미 존재하는 계정입니다.");
    }

    public AlreadyExistAccountException(String message){
        super(message);
    }
}
