package service.impl;

import model.Sale;
import model.Ticket;
import model.User;
import repository.SaleRepository;
import repository.impl.SaleRepositoryImpl;
import service.SaleService;
import service.validator.Validator;
import service.validator.impl.SaleValidator;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

public class SaleServiceImpl implements SaleService {
    private SaleRepository saleRepository;
    private List<Validator<Sale>> validators;

    public SaleServiceImpl() {
        saleRepository = new SaleRepositoryImpl();
        validators = new ArrayList<>();
        validators.add(new SaleValidator(this));
    }

    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
        validators = new ArrayList<>();
        validators.add(new SaleValidator(this));
    }

    @Override
    public Sale add(Sale sale) {
        sale = saleRepository.save(sale);
        createReceipt(sale);
        return sale;
    }

    @Override
    public List<Sale> findByUserOrderByDate(User user) {
        if(user != null) {
            return this.saleRepository.findByUserOrderByDate(user);
        }
        return null;
    }

    @Override
    public Map<Ticket, Integer> getTicketsSales() {
        return this.saleRepository.findTicketsSales();
    }

    private void createReceipt(Sale sale) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("ticket_" + sale.getTicket().getId() + ".txt"), "utf-8"))) {
            String[] lines = sale.toString().split("\n");
            for(String line: lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateSale(Sale sale) throws Exception {
        for(Validator<Sale> validator: validators)
            validator.validate(sale);
    }
}
