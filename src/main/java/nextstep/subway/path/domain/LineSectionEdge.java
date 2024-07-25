package nextstep.subway.path.domain;

public class LineSectionEdge implements WeightedEdge<Long> {

    private final Long source;

    private final Long target;
    private final double weight;

    public LineSectionEdge(final Long source, final Long target, final double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    @Override
    public Long getSource() {
        return source;
    }

    @Override
    public Long getTarget() {
        return target;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
