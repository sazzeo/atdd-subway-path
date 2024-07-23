package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class SectionDistanceNotValidException extends BaseException {
    public SectionDistanceNotValidException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
