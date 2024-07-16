package nextstep.subway.exceptions;

public enum ErrorMessage {

    INSUFFICIENT_STATIONS("구간이 1개밖에 없어 역을 삭제할 수 없습니다."),
    NOT_TERMINUS_STATION("삭제하려는 역이 종착역이 아닙니다."),
    LINE_HAS_NO_STATION("기존 역이 존재하지 않습니다."),
    INVALID_DOWN_STATION("하행역으로 등록하려는 역이 이미 존재합니다."),

    SECTION_NOT_FOUND("존재하지 않는 구간입니다.")
    ;
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
