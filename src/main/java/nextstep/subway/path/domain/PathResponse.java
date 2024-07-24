package nextstep.subway.path.domain;

import nextstep.subway.line.domain.ChainSorter;
import org.jgrapht.GraphPath;

import java.util.List;

public class PathResponse {
    private static final ChainSorter<LineSectionEdge, Long> chainSorter =
            new ChainSorter<>(LineSectionEdge::getSource, LineSectionEdge::getTarget);
    private final List<Long> path;
    private final Long distance;

    private PathResponse(final List<Long> path, final Long distance) {
        this.path = path;
        this.distance = distance;
    }

    public static PathResponse from(GraphPath<Long, LineSectionEdge> graphPath) {
        return new PathResponse(
                chainSorter.getSortedStationIds(graphPath.getEdgeList())
                , (long) graphPath.getWeight());
    }

    public List<Long> getPath() {
        return path;
    }

    public Long getDistance() {
        return distance;
    }
}
