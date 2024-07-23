package nextstep.subway.line.domain;

import java.util.*;
import java.util.stream.Collectors;

public class SectionStationSorter {
    public static List<Long> getSortedStationIds(final List<Section> sections) {
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

    private static Long getFirstUpStationId(final Map<Long, Long> upDownMap, final Set<Long> downStationIds) {
        for (Long upStationId : upDownMap.keySet()) {
            if (!downStationIds.contains(upStationId)) {
                return upStationId;
            }
        }
        throw new IllegalStateException("첫번째 역을 찾을 수 없습니다.");
    }

}
