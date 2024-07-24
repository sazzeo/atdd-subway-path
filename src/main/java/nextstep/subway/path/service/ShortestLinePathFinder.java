package nextstep.subway.path.service;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class ShortestLinePathFinder implements PathFinder {
    private final WeightedMultigraph<Long, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);

    public GraphPath<Long, DefaultWeightedEdge> setup(final List<Line> lines, Long source, Long target) {
        lines.stream()
                .flatMap(it -> it.getStationIds().stream())
                .distinct()
                .forEach(graph::addVertex);

        lines.stream().flatMap(Line::sectionStream)
                .forEach(it -> graph.setEdgeWeight(graph.addEdge(it.getUpStationId(), it.getDownStationId()), it.getDistance()));
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        return dijkstraShortestPath.getPath(source, target);
    }
}
