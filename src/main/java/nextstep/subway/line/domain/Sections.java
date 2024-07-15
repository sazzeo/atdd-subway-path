package nextstep.subway.line.domain;


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

        if (!getAllStationIds().contains(section.getUpStationId())) {
            throw new LineHasNoStationException("기존 역이 존재하지 않습니다.");
        }

        if (isUpStationAlreadyExists(section.getDownStationId())) {
            throw new InvalidDownStationException("하행역으로 등록하려는 역이 이미 존재합니다.");
        }

        //만약 마지막 역에 추가하는거면 추가
        if(isLastStation(section.getUpStationId())) {
            sections.add(section);
            return;
        }

        Section originSection = getSectionByUpStationId(section.getUpStationId());
        //기존 역의 상행역과 거리를 조정한다.
        originSection.updateForNewSection(section);
        //이후 새 역을 추가한다
        sections.add(section);
    }


    private Section getSectionByUpStationId(final Long stationId) {
        for (Section section : sections) {
            if(section.getUpStationId().equals(stationId)) {
                return section;
            }
        }
        throw new SectionNotFoundException("존재하지 않은 구간입니다.");
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
            throw new NotTerminusStationException("삭제하려는 역이 종착역이 아닙니다.");
        }
        if (hasOnlyOneSection()) {
            throw new InsufficientStationsException("구간이 1개밖에 없어 역을 삭제할 수 없습니다.");
        }
        sections.remove(sections.size() - 1);
    }

    public boolean hasOnlyOneSection() {
        return sections.size() == 1;
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
