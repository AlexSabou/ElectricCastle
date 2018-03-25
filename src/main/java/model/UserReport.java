package model;

public class UserReport {
    private User user;
    private Long tickets;

    public UserReport() {
        tickets = 0L;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTickets() {
        return tickets;
    }

    public void setTickets(Long tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return user.getUsername() + ": " + tickets + " tickets.";
    }
}
