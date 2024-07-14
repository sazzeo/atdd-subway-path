package nextstep.subway.line.exception;


import nextstep.subway.exceptions.BaseException;

public class NonExistentLineException extends BaseException {

    public NonExistentLineException(final String message) {
        super(message);
    }

}
