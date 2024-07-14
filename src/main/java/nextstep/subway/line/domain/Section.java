package nextstep.subway.line.domain;

import nextstep.subway.line.exception.SectionDistanceNotValidException;
import nextstep.subway.line.exception.SectionNotValidException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long upStationId;

    private Long downStationId;

    private Long distance;

    protected Section() {

    }

    public Section(final Long upStationId, final Long downStationId, final Long distance) {
        validate(upStationId, downStationId, distance);
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    private void validate(final Long upStationId, final Long downStationId, final Long distance) {
        if(upStationId.equals(downStationId)) {
            throw new SectionNotValidException("상행역과 하행역은 다른 역이어야 합니다.");
        }
        assertDistancePositive(distance);
    }

    public Long getId() {
        return id;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public Long getDistance() {
        return distance;
    }

    public void assertDistancePositive(Long distance) {
        if(distance < 1) {
            throw new SectionDistanceNotValidException("거리는 1이상이어야 합니다.");
        }
    }

    public void updateForNextSection(final Section nextStation) {
        Long newDistance = this.distance - nextStation.distance;
        assertDistancePositive(newDistance);
        this.downStationId =nextStation.upStationId;
        this.distance = newDistance;
    }

}
