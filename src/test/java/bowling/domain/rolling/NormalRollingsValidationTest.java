package bowling.domain.rolling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NormalRollingsValidationTest {

    @ParameterizedTest(name = "투구별 점수 검증 {index} [{arguments}]")
    @MethodSource("rollings_validation")
    @DisplayName("투구별 점수 검증")
    void rollings_validation(int first, int second, NormalRollingsValidation expected) {
        //given

        //when
        NormalRollingsValidation normalRollingsValidation = NormalRollingsValidation.of(first, second);

        //then
        assertThat(normalRollingsValidation).isEqualTo(expected);

    }

    private static Stream<Arguments> rollings_validation() {
        return Stream.of(
                Arguments.of(10, 0, NormalRollingsValidation.NONE),
                Arguments.of(5, 6, NormalRollingsValidation.EXCEED_LIMIT_SUM),
                Arguments.of(10, 1, NormalRollingsValidation.POST_STRIKE_ROLLING),
                Arguments.of(10, 0, NormalRollingsValidation.NONE),
                Arguments.of(0, 10, NormalRollingsValidation.NONE),
                Arguments.of(5, 4, NormalRollingsValidation.NONE),
                Arguments.of(0, 0, NormalRollingsValidation.NONE)
        );
    }
}