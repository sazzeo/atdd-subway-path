package nextstep.subway.path.service;

import nextstep.subway.line.repository.LineRepository;
import nextstep.subway.station.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class PathQueryServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private LineRepository lineRepository;

    private ShortestLinePathFinder shortestLinePathFinder;

    private PathQueryService pathQueryService;

    @BeforeEach
    void setUp() {
        shortestLinePathFinder= new ShortestLinePathFinder();
        pathQueryService = new PathQueryService(stationRepository, lineRepository, shortestLinePathFinder);
    }

    @Test
    @DisplayName("테스트")
    void test() {

    }
}
