package nextstep.subway.line.domain;

import nextstep.subway.line.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SectionsTest {

    private Long 역1;
    private Long 역2;
    private Long 역3;
    private Long 역4;

    @BeforeEach
    void setUp() {
        역1 = 1L;
        역2 = 2L;
        역3 = 3L;
        역4 = 4L;
    }

    @Nested
    class WhenShow {
        @DisplayName("모든 구간에 해당하는 역 Id을 반환한다")
        @Test
        void getLastStationIdTest() {
            //Given 역 4개가 있을때
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));
            sections.add(new Section(역2, 역3, 10L));
            sections.add(new Section(역3, 역4, 10L));

            //when 모든 구간을 조회하면
            var sectionIds = sections.getAllStationIds();

            // Then 역id 를 반환한다
            assertThat(sectionIds).containsOnly(역1, 역2, 역3, 역4);
        }

    }

    @Nested
    class WhenAdd {
        @DisplayName("새로 등록하려는 상행역이 기존 구간 역에 존재하지 않으면 에러를 발생시킨다")
        @Test
        void failTest1() {
            //Given 기존 노선에
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));

            //When 새로 등록하려는 상행역이 기존 구간 역에 존재하지 않으면
            //Then 에러를 발생시킨다
            assertThrows(LineHasNoStationException.class, () ->
                    sections.add(new Section(역4, 역3, 5L))
            );
        }

        @DisplayName("하행역으로 등록하려는 역이 이미 존재하는 경우 에러를 발생시킨다")
        @Test
        void failTest2() {
            //Given 기존 노선에
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));

            //When 하행역으로 등록하려는 역이 이미 존재하는 경우
            //Then 에러를 발생시킨다
            assertThrows(InvalidDownStationException.class, () ->
                    sections.add(new Section(역2, 역1, 20L))
            );
        }

        @DisplayName("기존 구간 사이에 새로 등록하려는 구간의 거리가 원래 구간의 거리보다 크거나 같으면 에러를 발생시킨다")
        @Test
        void failTest3() {
            //Given 기존 구간 사이에
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));
            sections.add(new Section(역2, 역3, 10L));

            //When 새로 등록하려는 구간의 거리가 원래 구간의 거리보다 크거나 같으면
            //Then 에러를 발생시킨다
            assertThrows(SectionDistanceNotValidException.class, () ->
                    sections.add(new Section(역2, 역4, 10L))
            );
        }

        @DisplayName("원래 거리가 1 미만인 노선 중간에 구간 추가시 에러를 발생시킨다")
        @Test
        void failTest4() {
            //Given 원래 거리가 1 미만인 노선
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 1L));

            //Given 중간에 구간 추가시
            //Then 에러를 발생시킨다
            assertThrows(SectionDistanceNotValidException.class, () ->
                    sections.add(new Section(역1, 역3, 1L))
            );
        }

        @DisplayName("기존 구간 사이에 새로 등록하려는 역을 추가하고 다시 조회하면 역이 추가되어있다.")
        @Test
        void addSuccessTest1() {
            //Given 기존 구간 사이에
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));
            sections.add(new Section(역2, 역3, 10L));

            //When 새로 등록하려는 역을 추가하고
            sections.add(new Section(역2, 역4, 9L));

            //Then 다시 조회하면 역이 추가되어있다.
            assertThat(sections.getSortedStationIds()).containsOnly(역1, 역2, 역4, 역3);
        }


        @DisplayName("기존 하행역을 상행역으로 하는 구간을 추가한다")
        @Test
        void addSuccessTest2() {
            //Given
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));

            //When 기존 하행역을 상행역으로 하는 구간을 추가시
            sections.add(new Section(역2, 역3, 10L));

            //Then 다시 조회했을때 추가된 역을 확인 할 수 있다
            assertThat(sections.getAllStationIds()).containsExactly(역1, 역2, 역3);
        }

    }

    @Nested
    class WhenDelete {
        @DisplayName("삭제 하려는 역이 종착역이 아닌 경우 에러를 발생시킨다")
        @Test
        void removeTest1() {
            //Given 기존 노선에
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));

            //When 삭제 하려는 역이 종착역이 아닌 경우
            //Then 에러를 발생시킨다
            assertThrows(NotTerminusStationException.class, () ->
                    sections.removeLastStation(역1)
            );
        }

        @DisplayName("구간이 1개밖에 없는 경우 종착역 삭제시 에러를 발생시킨다")
        @Test
        void removeTest2() {
            //Given 구간이 1개밖에 없는 경우
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));

            //When  종착역 삭제시
            //Then 에러를 발생시킨다
            assertThrows(InsufficientStationsException.class, () ->
                    sections.removeLastStation(역2)
            );
        }

        @DisplayName("구간이 2개 이상일 떄는 종착역 삭제에 성공한다")
        @Test
        void removeSuccessTest() {
            //Given 구간이 2개 이상일 때
            var sections = new Sections();
            sections.add(new Section(역1, 역2, 10L));
            sections.add(new Section(역2, 역3, 10L));

            //When 종착역 삭제시
            sections.removeLastStation(역3);

            //Then 삭제에 성공한다
            assertThat(sections.getAllStationIds()).containsExactly(역1, 역2);

        }


    }


}
