package nextstep.subway.path.service;

import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.domain.PathResponse;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShortestLinePathFinder {
    private final WeightedMultigraph<Long, LineSectionEdge> graph = new WeightedMultigraph<>(LineSectionEdge.class);

    public PathResponse getPathResponse(final List<Line> lines, Long source, Long target) {
        lines.stream()
                .flatMap(it -> it.getStationIds().stream())
                .distinct()
                .forEach(graph::addVertex);

        lines.stream().flatMap(Line::sectionStream)
                .forEach(it -> {
                    LineSectionEdge edge = graph.addEdge(it.getUpStationId(), it.getDownStationId());
                    edge.setId(it.getId());
                    graph.setEdgeWeight(edge, it.getDistance());
                });

        DijkstraShortestPath<Long, LineSectionEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        return PathResponse.from(dijkstraShortestPath.getPath(source, target));
    }

}
