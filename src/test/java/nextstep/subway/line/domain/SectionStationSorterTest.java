package nextstep.subway.line.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SectionStationSorterTest {

    @DisplayName("정렬되지 않은 구간의 역을 정렬한다")
    @Test
    void test() {
        //When 정렬되지 않은 구간의 역을
        List<Section> sections = new ArrayList<>();
        sections.add(new Section(1L, 2L, 10L));
        sections.add(new Section(3L, 4L, 10L));
        sections.add(new Section(2L, 3L, 10L));

        //When 정렬 시키면
        SectionStationSorter sectionStationSorter = new SectionStationSorter();
        List<Long> sortedStationIds = sectionStationSorter.getSortedStationIds(sections);

        //Then 정렬된 데이터가 반환된다
        assertThat(sortedStationIds).containsExactly(1L, 2L, 3L, 4L);
    }

}
