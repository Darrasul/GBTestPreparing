package org.buzas.lesson4.entities.schedules;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.buzas.lesson4.entities.films.Film;

import java.sql.Time;
import java.util.Objects;

@Getter
@Setter
public class Session {
    private Long id;
    private Film film;
    private Time startTime;
//    Прописал tt_id вручную, чтобы не возникло ошибок
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private int tt_id;

    public Session() {
    }

    public Session(Film film, TicketType type, Time startTime) {
        this.film = film;
        setTt_id(type);
        this.startTime = startTime;
    }

    public void setTt_id(int tt_id) {
        this.tt_id = tt_id;
    }

    public int getTt_id() {
        return this.tt_id;
    }

    public void setTt_id(TicketType type) {
        switch (type) {
            case NIGHT -> this.tt_id = 1;
            case MOURNING -> this.tt_id = 2;
            case DAY -> this.tt_id = 3;
            case EVENING -> this.tt_id = 4;
        }
    }

    public TicketType getTt_id_type() {
        switch (this.tt_id) {
            case 1 -> {
                return TicketType.NIGHT;
            }
            case 2 -> {
                return TicketType.MOURNING;
            }
            case 3 -> {
                return TicketType.DAY;
            }
            case 4 -> {
                return TicketType.EVENING;
            }
            default -> {
                throw new NullPointerException();
            }
        }
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", film=" + film +
                ", tt_id=" + getTt_id_type() +
                ", time=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return tt_id == session.tt_id && Objects.equals(id, session.id) && Objects.equals(film, session.film) && Objects.equals(startTime, session.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, film, startTime, tt_id);
    }
}
