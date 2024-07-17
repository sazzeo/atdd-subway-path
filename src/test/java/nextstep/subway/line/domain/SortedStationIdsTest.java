package nextstep.subway.line.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SortedStationIdsTest {

    private List<Section> sections;
    private SortedStationIds sortedStationIds;

    @BeforeEach
    void setUp() {
        //Given 정렬되지 않은 구간의 역을
        sections = new ArrayList<>();
        sections.add(new Section(1L, 2L, 10L));
        sections.add(new Section(3L, 4L, 10L));
        sections.add(new Section(2L, 3L, 10L));
        sortedStationIds = new SortedStationIds(sections);
    }

    @DisplayName("정렬된 역들을 반환한다")
    @Test
    void test() {
        //Given 정렬되지 않은 구간의 역을
        //When 생성시키면

        //Then 정렬된 데이터가 반환된다
        assertThat(sortedStationIds.value()).containsExactly(1L, 2L, 3L, 4L);
    }

    @DisplayName("첫번째 역을 반환한다")
    @Test
    void getFirstStationId() {
        assertThat(sortedStationIds.getFirstStationId()).isEqualTo(1L);
    }

    @DisplayName("마지막 역을 반환한다")
    @Test
    void getLastStationId() {
        assertThat(sortedStationIds.getLastStationId()).isEqualTo(4L);
    }

    @DisplayName("첫번째 역인지 판단한다")
    @Test
    void isFirstStation() {
        assertThat(sortedStationIds.isFirstStation(1L)).isTrue();
    }

    @DisplayName("마지막 역인지 판단한다")
    @Test
    void isLastStation() {
        assertThat(sortedStationIds.isLastStation(4L)).isTrue();
    }

    @DisplayName("새로운 구간데이터로 재정렬한다")
    @Test
    void sort() {
        sections.add(new Section(4L, 5L, 10L));
        sortedStationIds.sort(sections);

        assertThat(sortedStationIds.value()).containsExactly(1L, 2L, 3L, 4L, 5L);
    }


}
