package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class LineHasNoStationException extends BaseException {

    public LineHasNoStationException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
