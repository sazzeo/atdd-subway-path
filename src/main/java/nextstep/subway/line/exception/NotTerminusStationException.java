package nextstep.subway.line.exception;


import nextstep.subway.exceptions.BaseException;
import nextstep.subway.exceptions.ErrorMessage;

public class NotTerminusStationException extends BaseException {

    public NotTerminusStationException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
