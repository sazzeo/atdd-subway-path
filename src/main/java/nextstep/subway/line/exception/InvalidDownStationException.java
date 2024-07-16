package nextstep.subway.line.exception;


import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class InvalidDownStationException extends BaseException {
    public InvalidDownStationException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
