package nextstep.subway.line.exception;

import nextstep.subway.exceptions.BaseException;

public class LineHasNoStationException extends BaseException {
    public LineHasNoStationException(final String message) {
        super(message);
    }
}
