package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class InsufficientStationsException extends BaseException {
    public InsufficientStationsException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
