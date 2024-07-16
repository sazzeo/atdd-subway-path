package nextstep.subway.exceptions;

public enum ErrorMessage {

    INSUFFICIENT_STATIONS("구간이 1개밖에 없어 역을 삭제할 수 없습니다."),
    NOT_TERMINUS_STATION("삭제하려는 역이 종착역이 아닙니다.");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
