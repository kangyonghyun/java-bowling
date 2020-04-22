package bowling.domain.pitch;

public class Pitch {
    private Pin pin;
    private State state;

    private Pitch(Pin pin, State state) {
        this.pin = pin;
        this.state = state.bowl(this.pin);
    }

    public Pitch(int pinCount) {
        this(Pin.valueOf(pinCount), new ReadyState());
    }

    public Pitch(Pitch pitch) {
        this.pin = pitch.pin;
        this.state = pitch.state;
    }

    public int getPinCount() {
        return pin.getCount();
    }

    public boolean isStrike() {
        return state.isStrike();
    }

    public boolean isSpare() {
        return state.isSpare();
    }

    public boolean isGutter() {
        return pin.isMin();
    }

    public Pitch next(Pin pin) {
        return new Pitch(pin, this.state);
    }

    public Pitch next(int pinCount) {
        return next(Pin.valueOf(pinCount));
    }
}