package nextstep.subway.line.exception;


import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class LineNotFoundException extends BaseException {

    public LineNotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
