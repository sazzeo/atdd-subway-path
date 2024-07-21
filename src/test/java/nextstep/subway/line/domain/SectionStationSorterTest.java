package nextstep.subway.line.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SectionStationSorterTest {

    private List<Section> sections;

    @BeforeEach
    void setUp() {
        //Given 정렬되지 않은 구간의 역을
        sections = new ArrayList<>();
        sections.add(new Section(1L, 2L, 10L));
        sections.add(new Section(3L, 4L, 10L));
        sections.add(new Section(2L, 3L, 10L));
    }

    @Test
    @DisplayName("정렬되지 않은 구간의 역을 순서대로 반환한다")
    void test() {
        //when 정렬 메소드 실행시
        var sortedStationIds = SectionStationSorter.getSortedStationIds(sections);
        //then 순서대로 반환한다
        assertThat(sortedStationIds).containsExactly(1L, 2L, 3L, 4L);
    }


}
