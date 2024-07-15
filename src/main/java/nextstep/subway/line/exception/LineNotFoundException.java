package nextstep.subway.line.exception;


import nextstep.subway.exceptions.BaseException;

public class LineNotFoundException extends BaseException {

    public LineNotFoundException(final String message) {
        super(message);
    }

}
