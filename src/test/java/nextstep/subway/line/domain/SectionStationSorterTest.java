package nextstep.subway.line.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class SectionStationSorterTest {

    private SectionStationSorter sectionStationSorter;

    @BeforeEach
    void setUp() {
        sectionStationSorter = new SectionStationSorter();
    }

    @DisplayName("정렬되지 않은 구간의 역을 정렬한다")
    @Test
    void test() {
        //Given 정렬되지 않은 구간의 역을
        var sections = new ArrayList<Section>();
        sections.add(new Section(1L, 2L, 10L));
        sections.add(new Section(3L, 4L, 10L));
        sections.add(new Section(2L, 3L, 10L));

        //When 정렬 시키면
        List<Long> sortedStationIds = sectionStationSorter.getSortedStationIds(sections);

        //Then 정렬된 데이터가 반환된다
        assertThat(sortedStationIds).containsExactly(1L, 2L, 3L, 4L);
    }

    @DisplayName("첫번째 역과 마지막 역을 반환한다")
    @Test
    void getFirstAndLastStationIdTest() {
        //Given 정렬되지 않은 구간의
        var sections = new ArrayList<Section>();
        sections.add(new Section(1L, 2L, 10L));
        sections.add(new Section(3L, 4L, 10L));
        sections.add(new Section(2L, 3L, 10L));

        //When 첫번째 역과 마지막역을 반환하면
        var firstAndLastStation = sectionStationSorter.getFirstAndLastStationId(sections);
        //정렬돼서 반환된다
        assertAll(
                () -> assertThat(firstAndLastStation.getFirst()).isEqualTo(1L),
                () -> assertThat(firstAndLastStation.getSecond()).isEqualTo(4L)
        );

    }


}
