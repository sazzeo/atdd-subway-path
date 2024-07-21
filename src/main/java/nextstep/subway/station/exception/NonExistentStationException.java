package nextstep.subway.station.exception;

import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class NonExistentStationException extends BaseException {

    public NonExistentStationException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
