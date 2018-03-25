package repository;

import model.Sale;
import model.Ticket;
import model.User;

import java.util.List;
import java.util.Map;

public interface SaleRepository {
    public List<Sale> findAll();
    public List<Sale> findByUser(User user);
    public Map<Ticket, Integer> findTicketsSales();
    public Sale save(Sale sale);
    public Sale update(Sale sale);
    public boolean delete(Sale sale);
    public List<Sale> findByUserOrderByDate(User user);
}
