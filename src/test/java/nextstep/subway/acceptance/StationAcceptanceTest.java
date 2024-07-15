package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철역 관련 기능")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StationAcceptanceTest {

    @DisplayName("지하철역을 생성한다.")
    @Test
    void createStation() {
        // When  지하철역을 생성하면
        var response = StationApiRequest.역을_생성한다("강남역");
        // Then 지하철역이 생성된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // Then 지하철역 목록 조회 시 생성한 역을 찾을 수 있다
        List<String> stationNames = StationApiRequest.역을_모두_조회한다();
        assertThat(stationNames).containsAnyOf("강남역");
    }

    @DisplayName("지하철역을 모두 조회한다.")
    @Test
    void showStations() {
        // Given 2개의 지하철역을 생성하고
        StationApiRequest.역을_생성한다("강남역");
        StationApiRequest.역을_생성한다("선릉역");

        // When 지하철역 목록을 조회하면
        List<String> stationNames = StationApiRequest.역을_모두_조회한다();

        // Then 2개의 지하철역을 응답 받는다
        assertThat(stationNames).containsOnly("강남역", "선릉역");
    }


    @DisplayName("지하철역을 삭제한다")
    @Test
    void deleteStation() {
        // Given 지하철역을 2개를 생성하고
        String location = StationApiRequest.역을_생성한다("강남역").header("location");
        StationApiRequest.역을_생성한다("선릉역");

        // When 그중 하나의 지하철역을 삭제하면
        var response = StationApiRequest.역을_삭제한다(location);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        // Then 역을 재조회시 해당 역은 빠진 채로 조회된다.
        List<String> stationNames = StationApiRequest.역을_모두_조회한다();
        assertThat(stationNames).containsOnly("선릉역");
    }


}
