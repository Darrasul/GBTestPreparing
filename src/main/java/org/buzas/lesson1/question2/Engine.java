package org.buzas.lesson1.question2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Engine {
    private boolean isOn;

    public Engine() {
        this.isOn = false;
    }

    public Engine(boolean isOn) {
        this.isOn = isOn;
    }

    public void turnPower() {
        this.isOn = !isOn;
    }
}
