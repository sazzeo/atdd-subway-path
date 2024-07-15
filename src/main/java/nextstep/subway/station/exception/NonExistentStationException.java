package nextstep.subway.station.exception;

import nextstep.subway.exceptions.BaseException;

public class NonExistentStationException extends BaseException {

    public NonExistentStationException(final String message) {
        super(message);
    }

}
