package nextstep.subway.acceptance;

import io.restassured.path.json.JsonPath;
import nextstep.subway.line.payload.AddSectionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.subway.acceptance.LineApiRequest.*;
import static nextstep.subway.acceptance.LineApiRequest.노선을_조회한다;
import static nextstep.subway.acceptance.SectionApiRequest.구간을_추가한다;
import static nextstep.subway.acceptance.SectionApiRequest.노선에서_역을_삭제한다;
import static nextstep.subway.acceptance.StationApiRequest.*;
import static nextstep.subway.utils.HttpStatusAssertion.assertBadRequest;
import static nextstep.subway.utils.HttpStatusAssertion.assertNoContent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("지하철 구간 관련 기능")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SectionAcceptanceTest {

    private Long 최초상행종점역;
    private Long 최초하행종점역;
    private Long 삼성역;
    private Long 잠실역;

    private Long 수인분당선;

    @BeforeEach
    void setUp() {
        최초상행종점역 = 역을_생성한다("최초상행종점역").jsonPath().getLong("id");
        최초하행종점역 = 역을_생성한다("최초하행종점역").jsonPath().getLong("id");
        삼성역 = 역을_생성한다("삼성역").jsonPath().getLong("id");
        잠실역 = 역을_생성한다("잠실역").jsonPath().getLong("id");

        수인분당선 = 노선을_생성한다("수인분당선", "yellow", 최초상행종점역, 최초하행종점역, 10L).jsonPath().getLong("id");
    }

    @DisplayName("추가시")
    @Nested
    class WhenAddSection {

        @DisplayName("새로운 구간 등록후 해당 노선을 조회하면 등록된 모든 역을 확인 할 수 있다.")
        @Test
        void addSection() {
            //given 새로운 구간 등록에 성공하면
            var 신규구간 = new AddSectionRequest(최초하행종점역, 삼성역, 10L);
            var 생성_결과 = 구간을_추가한다(수인분당선, 신규구간);

            //when 노선 조회시
            var 노선 = 노선을_조회한다(String.format("/lines/%d", 수인분당선)).jsonPath();

            //then 등록된 모든 역을 확인할 수 있다.
            assertAll(() -> {
                        assertNoContent(생성_결과.statusCode());
                        역_이름_순서_검증(노선, "최초상행종점역", "최초하행종점역", "삼성역");
                    }
            );

        }

        @DisplayName("기존 구간 중간에 새로운 역을 추가할 수 있다.")
        @Test
        void addMiddleSection() {
            //given 기존 구간에
            //when 새로운 역을 추가하면
            var 신규구간 = new AddSectionRequest(최초상행종점역, 삼성역, 5L);
            구간을_추가한다(수인분당선, 신규구간);

            var 노선 = 노선을_조회한다(String.format("/lines/%d", 수인분당선)).jsonPath();

            //then 다시 조회시 새로운 구간이 함께 조회된다.
            역_이름_순서_검증(노선, "최초상행종점역", "삼성역", "최초하행종점역");

        }

        @DisplayName("기존 구간에 새롭게 추가할 상행종점역이 존재하지 않으면 400 상태코드를 반환한다")
        @Test
        void failTest1() {
            //given 기존 구간에
            //when 새롭게 추가할 상행종점역이 존재하지 않으면
            var 신규구간 = new AddSectionRequest(잠실역, 삼성역, 10L);
            var 생성_결과 = 구간을_추가한다(수인분당선, 신규구간);

            //Then 400 상태코드를 반환한다
            assertBadRequest(생성_결과.statusCode());
        }


        @DisplayName("이미 노선에 등록되어있는 역을 하행종점역으로 등록하면 400 상태코드를 반환한다.")
        @Test
        void failTest2() {
            //given 기존 구간에
            //when 이미 노선에 등록되어있는 역을 하행종점역으로 등록하면
            var 신규구간 = new AddSectionRequest(최초하행종점역, 최초상행종점역, 5L);
            var 생성_결과 = 구간을_추가한다(수인분당선, 신규구간);

            //then 400 상태코드를 반환한다
            assertBadRequest(생성_결과.statusCode());
        }
    }

    @DisplayName("삭제시")
    @Nested
    class WhenDeleteSection {

        @DisplayName("마지막역을 삭제할 수 있다")
        @Test
        void successTest1() {
            //given 신규구간 추가 후
            var 신규구간 = new AddSectionRequest(최초하행종점역, 삼성역, 10L);
            구간을_추가한다(수인분당선, 신규구간);


            //when 마지막 역 삭제에 성공하면 노선 조회시
            var 삭제_결과 = 노선에서_역을_삭제한다(수인분당선, 삼성역);
            var 노선 = 노선을_조회한다(String.format("/lines/%d", 수인분당선)).jsonPath();

            //then 삭제된 역이 다시 조회되지 않는다
            assertAll(() -> {
                        assertNoContent(삭제_결과.statusCode());
                        역_이름_순서_검증(노선, "최초상행종점역", "최초하행종점역");
                    }
            );
        }


        @DisplayName("중간역을 삭제할 수 있다.")
        @Test
        void successTest2() {
            //given 신규구간 추가 후
            var 신규구간 = new AddSectionRequest(최초하행종점역, 삼성역, 10L);
            구간을_추가한다(수인분당선, 신규구간);


            //when 중간역 삭제에 성공하면 노선 조회시
            var 삭제_결과 = 노선에서_역을_삭제한다(수인분당선, 최초하행종점역);
            var 노선 = 노선을_조회한다(String.format("/lines/%d", 수인분당선)).jsonPath();

            //then 삭제된 역이 다시 조회되지 않는다
            assertAll(() -> {
                        assertNoContent(삭제_결과.statusCode());
                        역_이름_순서_검증(노선, "최초상행종점역", "삼성역");
                    }
            );
        }

        @DisplayName("첫번째역을 삭제할 수 있다.")
        @Test
        void successTest3() {
            //given 신규구간 추가 후
            var 신규구간 = new AddSectionRequest(최초하행종점역, 삼성역, 10L);
            구간을_추가한다(수인분당선, 신규구간);


            //when 첫번째 역 삭제에 성공하면 노선 조회시
            var 삭제_결과 = 노선에서_역을_삭제한다(수인분당선, 최초상행종점역);
            var 노선 = 노선을_조회한다(String.format("/lines/%d", 수인분당선)).jsonPath();

            //then 삭제된 역이 다시 조회되지 않는다
            assertAll(() -> {
                        assertNoContent(삭제_결과.statusCode());
                        역_이름_순서_검증(노선, "삼성역", "최초하행종점역");
                    }
            );
        }


        @DisplayName("역이 2개 이하로 존재하는 경우 삭제시 400 상태코드를 반환한다.")
        @Test
        void failTest2() {
            //given 역이 2개 이하로 존재하는 경우

            //when 삭제시
            var 삭제_결과 = 노선에서_역을_삭제한다(수인분당선, 최초하행종점역);

            //then 400 상태코드를 반환한다
            assertBadRequest(삭제_결과.statusCode());
        }

    }

    private void 역_이름_순서_검증(JsonPath jsonPath, String... values) {
        assertThat(jsonPath.getList("stations.name", String.class)).containsExactly(values);
    }

}
