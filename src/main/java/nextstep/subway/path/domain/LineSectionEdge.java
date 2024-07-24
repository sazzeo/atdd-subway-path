package nextstep.subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class LineSectionEdge extends DefaultWeightedEdge {

    private String name;
    private Long id;

    public LineSectionEdge() {
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Long getSource() {
        return (Long) super.getSource();
    }

    @Override
    public Long getTarget() {
        return (Long) super.getTarget();
    }
}
