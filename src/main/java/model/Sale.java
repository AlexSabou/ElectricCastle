package model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sale {
    private Long id;
    private User user;
    private Ticket ticket;
    private LocalDateTime date;

    public Sale() {
    }

    public Sale(User user, Ticket ticket) {
        this.user = user;
        this.ticket = ticket;
        this.date = LocalDateTime.now();
    }

    public Sale(User user, Ticket ticket, LocalDateTime date) {
        this.user = user;
        this.ticket = ticket;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ticket: " + ticket.getName() +
                "\t\t\tDate: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "\nSale nr. " + this.getId() +
                "\nSeller: Electric Castle" +
                "\nCashier: " + user.getUsername() +
                "\nTotal: " + ticket.getPrice();
    }
}
