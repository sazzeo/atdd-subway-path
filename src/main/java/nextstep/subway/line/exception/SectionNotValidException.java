package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class SectionNotValidException extends BaseException {
    public SectionNotValidException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
