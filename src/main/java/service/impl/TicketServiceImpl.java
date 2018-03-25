package service.impl;

import model.Ticket;
import repository.TicketRepository;
import repository.impl.TicketRepositoryImpl;
import service.TicketService;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;

    public TicketServiceImpl() {
        this.ticketRepository = new TicketRepositoryImpl();
    }

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
