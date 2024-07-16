package nextstep.subway.line.domain;


import nextstep.subway.exceptions.ErrorMessage;
import nextstep.subway.line.exception.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Sections {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "line_id")
    private List<Section> sections = new ArrayList<>();
    @Transient
    private final SectionStationSorter sectionStationSorter = new SectionStationSorter();

    public void add(final Section section) {
        if (this.sections.isEmpty()) {
            sections.add(section);
            return;
        }
        validateBeforeAdd(section);

        //만약 마지막 역에 추가하는거면 추가
        if(isLastStation(section.getUpStationId())) {
            sections.add(section);
            return;
        }

        Section originSection = getSectionByUpStationId(section.getUpStationId());
        originSection.updateForNewSection(section);
        sections.add(section);
    }

    public List<Long> getAllStationIds() {
        List<Long> stationIds = sections.stream()
                .map(Section::getUpStationId)
                .collect(Collectors.toList());

        stationIds.add(getLastDownStationId());
        return stationIds;
    }

    public List<Long> getSortedStationIds() {
        return sectionStationSorter.getSortedStationIds(this.sections);
    }

    public boolean isLastStation(final Long stationId) {
        return this.getLastDownStationId().equals(stationId);
    }

    public void removeLastStation(final Long stationId) {
        if (!isLastStation(stationId)) {
            throw new NotTerminusStationException(ErrorMessage.NOT_TERMINUS_STATION);
        }
        if (hasOnlyOneSection()) {
            throw new InsufficientStationsException(ErrorMessage.INSUFFICIENT_STATIONS);
        }
        sections.remove(sections.size() - 1);
    }

    public boolean hasOnlyOneSection() {
        return sections.size() == 1;
    }

    private void validateBeforeAdd(final Section section) {
        if (!getAllStationIds().contains(section.getUpStationId())) {
            throw new LineHasNoStationException(ErrorMessage.LINE_HAS_NO_STATION);
        }

        if (isUpStationAlreadyExists(section.getDownStationId())) {
            throw new InvalidDownStationException(ErrorMessage.INVALID_DOWN_STATION);
        }
    }

    private Section getSectionByUpStationId(final Long stationId) {
        for (Section section : sections) {
            if(section.getUpStationId().equals(stationId)) {
                return section;
            }
        }
        throw new SectionNotFoundException(ErrorMessage.SECTION_NOT_FOUND);
    }

    private Long getLastDownStationId() {
        return sections.get(sections.size() - 1).getDownStationId();
    }

    private boolean isUpStationAlreadyExists(Long stationId) {
        return sections.stream()
                .map(Section::getUpStationId)
                .anyMatch(id -> id.equals(stationId));
    }

}
