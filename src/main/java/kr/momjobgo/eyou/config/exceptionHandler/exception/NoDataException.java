package kr.momjobgo.eyou.config.exceptionHandler.exception;

public class NoDataException extends RuntimeException{

    public NoDataException(){
        super("해당 글이 존재하지 않습니다.");
    }

    public NoDataException(String message){
        super(message);
    }
}
