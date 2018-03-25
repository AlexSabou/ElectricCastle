package service;

import model.Sale;
import model.Ticket;
import model.User;

import java.util.List;
import java.util.Map;

public interface SaleService {
    public Sale add(Sale sale);
    public List<Sale> findByUserOrderByDate(User user);
    public Map<Ticket, Integer> getTicketsSales();
    public void validateSale(Sale sale) throws Exception;
}
