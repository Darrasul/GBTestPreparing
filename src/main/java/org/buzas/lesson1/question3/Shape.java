package org.buzas.lesson1.question3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shape {

    public void tellAboutShape() {
        System.out.println("It is an amorphous shape");
    }

    public void tellThatIsShape() {
        System.out.println("It is shape in fact");
    }
}
