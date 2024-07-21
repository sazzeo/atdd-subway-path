package nextstep.subway.line.domain;

import java.util.*;
import java.util.stream.Collectors;

public class SortedStation {

    private List<Long> sortedStationIds;
    private Section firstSection;
    private Section lastSection;

    public SortedStation(final List<Section> sections) {
        setSortedStation(sections);
    }

    public List<Long> value() {
        return Collections.unmodifiableList(this.sortedStationIds);
    }


    public Long getFirstStationId() {
        return sortedStationIds.get(0);
    }

    public boolean isFirstStation(final Long stationId) {
        return getFirstStationId().equals(stationId);
    }

    public Long getLastStationId() {
        return sortedStationIds.get(sortedStationIds.size() - 1);
    }

    public boolean isLastStation(final Long stationId) {
        return getLastStationId().equals(stationId);
    }

    public Section getFirstSection() {
        return firstSection;
    }

    public Section getLastSection() {
        return lastSection;
    }

    public void setSortedStation(final List<Section> sections) {
        List<Long> stationIds = new ArrayList<>();
        if (sections.isEmpty()) {
            this.sortedStationIds = stationIds;
            return;
        }

        Map<Long, Section> upDownMap = sections.stream()
                .collect(Collectors.toMap(Section::getUpStationId, section -> section));
        Map<Long, Section> downStationMap = sections.stream()
                .collect(Collectors.toMap(Section::getDownStationId, section -> section));

        Long currentStationId = getFirstUpStationId(upDownMap, downStationMap);
        this.firstSection = upDownMap.get(currentStationId);

        stationIds.add(currentStationId);
        for (int i = 0; i < upDownMap.size(); i++) {
            Long nextStationId = upDownMap.get(currentStationId).getDownStationId();
            stationIds.add(nextStationId);
            currentStationId = nextStationId;
        }
        this.lastSection = downStationMap.get(currentStationId);
        this.sortedStationIds = stationIds;
    }

    private Long getFirstUpStationId(final Map<Long, Section> upDownMap, final Map<Long, Section> downStationMap) {
        Set<Long> downStationIds = downStationMap.keySet();
        for (Long upStationId : upDownMap.keySet()) {
            if (!downStationIds.contains(upStationId)) {
                return upStationId;
            }
        }
        throw new IllegalStateException("첫번째 역을 찾을 수 없습니다.");
    }
}
