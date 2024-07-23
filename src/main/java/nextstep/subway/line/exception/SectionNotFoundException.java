package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class SectionNotFoundException extends BaseException {
    public SectionNotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
