package repository;

import model.Ticket;

import java.util.List;

public interface TicketRepository {
    public List<Ticket> findAll();
}
