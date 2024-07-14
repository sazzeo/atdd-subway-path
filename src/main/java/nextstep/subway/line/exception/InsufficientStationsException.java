package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;

public class InsufficientStationsException extends BaseException {
    public InsufficientStationsException(final String message) {
        super(message);
    }
}
