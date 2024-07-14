package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;

public class SectionNotFoundException extends BaseException {
    public SectionNotFoundException(final String message) {
        super(message);
    }
}
