package nextstep.subway.line.domain;

import nextstep.subway.line.exception.InsufficientStationsException;
import nextstep.subway.line.exception.InvalidDownStationException;
import nextstep.subway.line.exception.LineHasNoStationException;
import nextstep.subway.line.exception.NotTerminusStationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LineTest {

    private Line line;
    private Long 상행역;
    private Long 하행역;

    @BeforeEach
    void setUp() {
        상행역 = 1L;
        하행역 = 2L;
        line = new Line("2호선", "green", new Section(상행역, 하행역, 10L));
    }

    @Nested
    class WhenShow {

    }

    @Nested
    class WhenAdd {

    }

    @Nested
    class WhenDelete {

        @DisplayName("역이 2개 이하로 존재하는 경우 하행역 삭제시 에러를 발생시킨다.")
        @Test
        void test4() {
            //역이 2개 이하로 존재하는 경우
            //when 하행역 삭제시
            //then 에러를 발생시킨다
            assertThrows(InsufficientStationsException.class, () ->
                    line.removeLastStation(하행역)
            );
        }

        @DisplayName("역이 3개 이상일때 마지막역을 삭제하면 조회되지 않는다")
        @Test
        void test5() {
            //역이 3개 이상일때
            var 역3 = 3L;
            line.addSection(하행역, 역3, 10L);

            //마지막역을 삭제하면
            line.removeLastStation(역3);

            //then 조회되지 않는다
            assertThat(line.getStationIds()).containsExactly(상행역, 하행역);
        }

    }

}
