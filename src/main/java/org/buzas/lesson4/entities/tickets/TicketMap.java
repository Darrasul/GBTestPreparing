package org.buzas.lesson4.entities.tickets;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class TicketMap {
    HashMap<Long, Ticket> tickets = new HashMap<>();

    public void addTicket(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public Ticket getTicket(Long id) {
        return tickets.get(id);
    }

    public void updateTicket(Long id, Ticket ticket) {
        tickets.put(id, ticket);
    }

    public void deleteTicket(Long id) {
        tickets.remove(id);
    }

    public boolean isSeatAvailable(Ticket ticket) {
        for (Ticket value : tickets.values()) {
            if (value.isSold() && value.getSeat() == ticket.getSeat() && value.getSession().equals(ticket.getSession())){
              return false;
            }
        }
        return true;
    }
}
