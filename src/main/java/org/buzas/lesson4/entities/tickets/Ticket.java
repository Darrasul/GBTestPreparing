package org.buzas.lesson4.entities.tickets;

import lombok.Getter;
import lombok.Setter;
import org.buzas.lesson4.entities.schedules.Session;

@Getter
@Setter
public class Ticket {
    private Long id;
    private Session session;
    private int seat;
    private boolean sold;
    private double cost;

    public Ticket() {
    }

    public Ticket(Session session, int seat) {
        this.session = session;
        this.seat = seat;
        this.sold = true;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", session_film=" + session.getFilm() +
                ", session_start_at=" + session.getStartTime() +
                ", seat=" + seat +
                ", cost=" + cost +
                ", sold=" + sold +
                '}';
    }
}
