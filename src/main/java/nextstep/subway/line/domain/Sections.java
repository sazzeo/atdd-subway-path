package nextstep.subway.line.domain;


import nextstep.subway.exceptions.ErrorMessage;
import nextstep.subway.line.exception.InsufficientStationsException;
import nextstep.subway.line.exception.InvalidDownStationException;
import nextstep.subway.line.exception.LineHasNoStationException;
import nextstep.subway.line.exception.SectionNotFoundException;

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
    private final SortedStation sortedSectionIds = new SortedStation(sections);

    public void add(final Section section) {
        if (this.sections.isEmpty()) {
            addAndSort(section);
            return;
        }
        validateBeforeAdd(section);

        //만약 마지막 역에 추가하는거면 추가
        if (sortedSectionIds.isLastStation(section.getUpStationId())) {
            addAndSort(section);
            return;
        }

        Section originSection = getSectionByUpStationId(section.getUpStationId());
        originSection.updateForNewSection(section);
        addAndSort(section);
    }

    public void addAndSort(final Section section) {
        sections.add(section);
        sortedSectionIds.setSortedStation(this.sections);
    }

    public List<Long> getAllStationIds() {
        List<Long> stationIds = sections.stream()
                .map(Section::getUpStationId)
                .collect(Collectors.toList());

        stationIds.add(sortedSectionIds.getLastStationId());
        return stationIds;
    }

    public List<Long> getSortedStationIds() {
        return sortedSectionIds.value();
    }


    public void removeStation(final Long stationId) {
        if (hasOnlyOneSection()) {
            throw new InsufficientStationsException(ErrorMessage.INSUFFICIENT_STATIONS);
        }
        if (sortedSectionIds.isFirstStation(stationId)) {
        }

        if (sortedSectionIds.isLastStation(stationId)) {

        }

    }

    public boolean hasOnlyOneSection() {
        return sections.size() == 1;
    }

    private void validateBeforeAdd(final Section section) {
        if (!sortedSectionIds.value().contains(section.getUpStationId())) {
            throw new LineHasNoStationException(ErrorMessage.LINE_HAS_NO_STATION);
        }

        if (isUpStationAlreadyExists(section.getDownStationId())) {
            throw new InvalidDownStationException(ErrorMessage.INVALID_DOWN_STATION);
        }
    }

    Section getSectionByUpStationId(final Long stationId) {
        for (Section section : sections) {
            if (section.getUpStationId().equals(stationId)) {
                return section;
            }
        }
        throw new SectionNotFoundException(ErrorMessage.SECTION_NOT_FOUND);
    }


    private boolean isUpStationAlreadyExists(Long stationId) {
        return sections.stream()
                .map(Section::getUpStationId)
                .anyMatch(id -> id.equals(stationId));
    }

}
