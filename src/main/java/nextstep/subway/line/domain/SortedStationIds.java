package nextstep.subway.line.domain;

import java.util.*;
import java.util.stream.Collectors;

public class SortedStationIds {

    private List<Long> sortedStationIds;

    public SortedStationIds(final List<Section> sections) {
        this.sortedStationIds = createSortedStationIds(sections);
    }

    public List<Long> value() {
        return Collections.unmodifiableList(this.sortedStationIds);
    }

    public void sort(final List<Section> sections) {
        sortedStationIds = createSortedStationIds(sections);
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
    private List<Long> createSortedStationIds(final List<Section> sections) {
        List<Long> stationIds = new ArrayList<>();
        Map<Long, Long> upDownMap = sections.stream()
                .collect(Collectors.toMap(Section::getUpStationId, Section::getDownStationId));
        Set<Long> downStationIds = sections.stream()
                .map(Section::getDownStationId).collect(Collectors.toSet());

        Long currentStationId = getFirstUpStationId(upDownMap, downStationIds);
        stationIds.add(currentStationId);
        for (int i = 0; i < upDownMap.size(); i++) {
            Long nextStationId = upDownMap.get(currentStationId);
            stationIds.add(nextStationId);
            currentStationId = nextStationId;
        }
        return stationIds;
    }

    private Long getFirstUpStationId(final Map<Long, Long> upDownMap, final Set<Long> downStationIds) {
        for (Long upStationId : upDownMap.keySet()) {
            if (!downStationIds.contains(upStationId)) {
                return upStationId;
            }
        }
        throw new IllegalStateException("첫번째 역을 찾을 수 없습니다.");
    }
}
