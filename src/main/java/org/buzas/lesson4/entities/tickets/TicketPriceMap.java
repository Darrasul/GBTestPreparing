package org.buzas.lesson4.entities.tickets;

import org.buzas.lesson4.entities.schedules.TicketType;

import java.util.HashMap;

public class TicketPriceMap {
    HashMap<Integer, Double> prices = new HashMap<>();

    public void correctPrice(int tt_id, Double price) {
        if (tt_id <= 0 || tt_id > 4){
            throw new NullPointerException();
        }
        prices.put(tt_id, price);
    }

    public Double getPrice(int tt_id) {
        if (tt_id <= 0 || tt_id > 4){
            throw new NullPointerException();
        }
        return prices.get(tt_id);
    }
}
