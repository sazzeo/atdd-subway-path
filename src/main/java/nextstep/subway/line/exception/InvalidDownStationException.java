package nextstep.subway.line.exception;


import nextstep.subway.exceptions.BaseException;

public class InvalidDownStationException extends BaseException {
    public InvalidDownStationException(final String message) {
        super(message);
    }
}
