package nextstep.subway.path.service;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.path.domain.PathResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ShortestLinePathFinderTest {

    private Long 교대역 = 1L;
    private Long 강남역 = 2L;
    private Long 양재역 = 3L;
    private Long 남부터미널역 = 4L;
    private Line 이호선;
    private Line 신분당선;
    private Line 삼호선;

    /**
     * 교대역    --- *2호선* (10) ---    강남역
     * |                                 |
     * *3호선* (2)                   *신분당선* (10)
     * |                                 |
     * 남부터미널역  --- *3호선* (3) ---   양재
     */

    @BeforeEach
    void setUp() {
        이호선 = new Line("2호선", "green", new Section(교대역, 강남역, 10L));
        신분당선 = new Line("신분당선", "red", new Section(강남역, 양재역, 10L));
        삼호선 = new Line("3호선", "orange", new Section(교대역, 남부터미널역, 2L));
        삼호선.addSection(남부터미널역, 양재역, 3L);
    }


    @DisplayName("최단 거리를 반환한다")
    @Test
    void test() {
        ShortestLinePathFinder pathFinder = new ShortestLinePathFinder();
        PathResponse pathResponse = pathFinder.getPathResponse(List.of(이호선, 신분당선, 삼호선), 교대역, 양재역);
        assertThat(pathResponse.getDistance()).isEqualTo(5L);
    }

    @DisplayName("최단거리를 정렬해 vertex만 반환한다")
    @Test
    void test2() {
        ShortestLinePathFinder pathFinder = new ShortestLinePathFinder();
        PathResponse pathResponse = pathFinder.getPathResponse(List.of(이호선, 신분당선, 삼호선), 교대역, 양재역);
        assertThat(pathResponse.getPath()).containsExactly(교대역, 남부터미널역, 양재역);
    }

}
