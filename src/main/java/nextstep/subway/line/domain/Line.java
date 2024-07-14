package nextstep.subway.line.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    @Embedded
    private Sections sections = new Sections();

    public Line() {
    }

    public Line(final String name, final String color, final Section section) {
        this.name = name;
        this.color = color;
        sections.add(section);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }


    public void update(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public void addSection(final Long upStationId, final Long downStationId, final Long distance) {
        sections.add(new Section(upStationId, downStationId, distance));
    }

    public List<Long> getStationIds() {
        return sections.getStationIds();
    }

    public void removeLastStation(final Long stationId) {
        sections.removeLastStation(stationId);
    }

}