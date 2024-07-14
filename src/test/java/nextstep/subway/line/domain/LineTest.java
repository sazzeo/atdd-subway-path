package nextstep.subway.line.domain;

import nextstep.subway.line.exception.*;
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
    class whenShow {
        @DisplayName("모든 구간에 해당하는 역을 순서대로 반환한다.")
        @Test
        void test() {
            //Given 구간이 3개일때
            var 신규역1 = 3L;
            var 신규역2 = 4L;
            line.addSection(하행역, 신규역1, 10L );
            line.addSection(신규역1, 신규역2, 10L );

            //When 모든역 조회시
            var stationIds = line.getStationIds();

            //Then 역을 4개를 모두 반환한다
            assertThat(stationIds).containsExactly(상행역, 하행역, 신규역1, 신규역2);

        }
    }

    @Nested
    class whenAdd {

        @DisplayName("새로 등록하려는 상행역이 기존 구간 역에 존재하지 않으면 에러를 발생시킨다")
        @Test
        void test1() {
            //when 새로 등록하려는 상행역이 기존 구간 역에 존재하지 않으면
            var 새상행역 = 3L;
            var 새하행역 = 4L;
            //then 에러를 발생시킨다
            assertThrows(LineHasNoStationException.class, () -> {
                line.addSection(새상행역, 새하행역, 20L);
            });
        }

        @DisplayName("새로 등록하려는 하행역이 이미 등록된 경우 에러를 발생시킨다")
        @Test
        void test2() {
            //when 새로 등록하려는 하행역이 이미 등록된 경우
            var 새상행역 = 2L;
            var 새하행역 = 1L;
            //then 에러를 발생시킨다
            assertThrows(InvalidDownStationException.class, () ->
                    line.addSection(새상행역, 새하행역, 20L)
            );

        }

    }

    @Nested
    class whenDelete {

        @DisplayName("역이 3개 이상일 때 삭제하려는 역이 기존 종착역이 아닌 경우 에러를 발생시킨다")
        @Test
        void test3() {
            var 역3 = 3L;
            line.addSection(하행역, 역3, 10L);

            //when 삭제하려는 역이 현재 종착역이 아닌 경우
            //then 에러를 발생시킨다
            assertThrows(NotTerminusStationException.class, () ->
                    line.removeLastStation(하행역)
            );
        }

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
