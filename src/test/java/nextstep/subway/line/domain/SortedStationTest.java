package nextstep.subway.line.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SortedStationTest {

    private List<Section> sections;
    private SortedStation sortedStation;

    @BeforeEach
    void setUp() {
        //Given 정렬되지 않은 구간의 역을
        sections = new ArrayList<>();
        sections.add(new Section(1L, 2L, 10L));
        sections.add(new Section(3L, 4L, 10L));
        sections.add(new Section(2L, 3L, 10L));
        sortedStation = new SortedStation(sections);
    }

    @DisplayName("정렬된 역들을 반환한다")
    @Test
    void test() {
        //Given 정렬되지 않은 구간의 역을
        //When 생성시키면

        //Then 정렬된 데이터가 반환된다
        assertThat(sortedStation.value()).containsExactly(1L, 2L, 3L, 4L);
    }

    @DisplayName("첫번째 역을 반환한다")
    @Test
    void getFirstStationId() {
        assertThat(sortedStation.getFirstStationId()).isEqualTo(1L);
    }

    @DisplayName("마지막 역을 반환한다")
    @Test
    void getLastStationId() {
        assertThat(sortedStation.getLastStationId()).isEqualTo(4L);
    }

    @DisplayName("첫번째 역인지 판단한다")
    @Test
    void isFirstStation() {
        assertThat(sortedStation.isFirstStation(1L)).isTrue();
    }

    @DisplayName("마지막 역인지 판단한다")
    @Test
    void isLastStation() {
        assertThat(sortedStation.isLastStation(4L)).isTrue();
    }

    @DisplayName("새로운 구간데이터로 재정렬한다")
    @Test
    void sort() {
        sections.add(new Section(4L, 5L, 10L));
        sortedStation.setSortedStation(sections);

        assertThat(sortedStation.value()).containsExactly(1L, 2L, 3L, 4L, 5L);
    }

    @DisplayName("첫번째 구간을 반환한다")
    @Test
    void getFirstSection() {
        var firstSection = sortedStation.getFirstSection();
        assertAll(
                () -> assertThat(firstSection.getUpStationId()).isEqualTo(1L),
                () -> assertAll(() -> assertThat(firstSection.getDownStationId()).isEqualTo(2L))
        );
    }

    @DisplayName("마지막 구간을 반환한다")
    @Test
    void getLastSection() {
        var lastSection = sortedStation. getLastSection();
        assertAll(
                () -> assertThat(lastSection.getUpStationId()).isEqualTo(3L),
                () -> assertAll(() -> assertThat(lastSection.getDownStationId()).isEqualTo(4L))
        );
    }

    @DisplayName("구간이 하나면 역을 그대로 반환한다")
    @Test
    void oneSectionTest() {
        sections = new ArrayList<>();
        sections.add(new Section(1L, 2L, 10L));
        SortedStation sortedStation = new SortedStation(sections);

        assertThat(sortedStation.value()).containsExactly(1L, 2L);
    }

}
