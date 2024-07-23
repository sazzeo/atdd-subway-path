package nextstep.subway.exceptions;

public class BaseException extends RuntimeException {
    public BaseException(final String message) {
        super(message);
    }

    public BaseException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
