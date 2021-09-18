package bowling.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class FrameNumberTest {

    @DisplayName("볼링 넘버에 맞지않는 수로 생성하면 에러가 발생한다. ")
    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    void validate(int number){
        assertThatThrownBy(()-> new FrameNumber(number)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("현재 frameNumber 의 다음 번호를 int 로 반환한다.")
    @Test
    void nextNumber() {
        assertThat(new FrameNumber(1).nextNumber()).isEqualTo(2);
    }

    @DisplayName("노멀 프레임의 마지막 번호인지 확인한다.")
    @Test
    void isLastNormalNumber(){
        assertThat(new FrameNumber(9).isLastNormalNumber()).isTrue();
        assertThat(new FrameNumber(8).isLastNormalNumber()).isFalse();
    }

}