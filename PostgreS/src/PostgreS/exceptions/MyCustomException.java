package PostgreS.exceptions;

public final class MyCustomException extends RuntimeException {

    public MyCustomException(Exception e) {
        super(e);
    }

    public MyCustomException(String message, Exception cause) {
        super(message, cause);
    }

}
