package nextstep.subway.path.service;

import nextstep.subway.exceptions.ErrorMessage;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.repository.LineRepository;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.payload.SearchPathRequest;
import nextstep.subway.path.payload.ShortestPathResponse;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.exception.NonExistentStationException;
import nextstep.subway.station.payload.StationResponse;
import nextstep.subway.station.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class PathQueryService {

    private final StationRepository stationRepository;
    private final LineRepository lineRepository;
    private final ShortestPathFinder<LineSectionEdge, Long> shortestPathFinder;

    public PathQueryService(final StationRepository stationRepository, final LineRepository lineRepository, final ShortestPathFinder<LineSectionEdge, Long> shortestPathFinder) {
        this.stationRepository = stationRepository;
        this.lineRepository = lineRepository;
        this.shortestPathFinder = shortestPathFinder;
    }

    public ShortestPathResponse findShortestPath(final SearchPathRequest request) {
        Long source = request.getSource();
        Long target = request.getTarget();
        List<Station> stations = stationRepository.findByIdIn(List.of(source, target));
        if (stations.size() != 2) {
            throw new NonExistentStationException(ErrorMessage.NON_EXISTENT_STATION);
        }
        List<Line> lines = lineRepository.findAll();
        List<LineSectionEdge> edges = lines.stream()
                .flatMap(Line::sectionStream)
                .map(it -> new LineSectionEdge(it.getUpStationId(), it.getDownStationId(),
                        it.getDistance().doubleValue()))
                .collect(Collectors.toList());

        var shortestPath = shortestPathFinder.find(edges, source, target);
        List<Long> stationIds = shortestPath.getVertexList();
        Map<Long, Station> stationMap = getStationMap(stationIds);
        return new ShortestPathResponse(
                shortestPath.getVertexList().stream()
                        .map(stationMap::get)
                        .map(StationResponse::from)
                        .collect(Collectors.toList()),
                (long) shortestPath.getWeight()
        );
    }

    private Map<Long, Station> getStationMap(final Collection<Long> stationsIds) {
        return stationRepository.findByIdIn(stationsIds)
                .stream()
                .collect(Collectors.toMap(Station::getId, (station -> station)));
    }
}
