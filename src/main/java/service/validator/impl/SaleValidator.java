package service.validator.impl;

import model.Sale;
import model.Ticket;
import repository.RulesRepository;
import repository.impl.RulesRepositoryImpl;
import service.RulesService;
import service.SaleService;
import service.impl.RulesServiceImpl;
import service.impl.SaleServiceImpl;
import service.validator.Validator;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SaleValidator implements Validator<Sale> {
    private RulesService rulesService;
    private SaleService saleService;
    private static final Long ALL_DAYS_ID = 4L;

    public SaleValidator(SaleService saleService) {
        this.saleService = saleService;
        this.rulesService = new RulesServiceImpl();
    }

    public SaleValidator(RulesService rulesService, SaleService saleService) {
        this.rulesService = rulesService;
        this.saleService = saleService;
    }

    @Override
    public void validate(Sale sale) {
        Integer maxCapacity = this.rulesService.getCapacity();
        Map<Ticket, Integer> ticketsSales = this.saleService.getTicketsSales();
        Integer ticketsAll = 0;
        //Get and remove the all days tickets
        for(Map.Entry<Ticket, Integer> entry: ticketsSales.entrySet()) {
            if(entry.getKey().getId().equals(ALL_DAYS_ID)) {
                ticketsAll = entry.getValue();
                ticketsSales.remove(entry.getKey());
                break;
            }
        }
        //
        if(!sale.getTicket().getId().equals(ALL_DAYS_ID)) {
            Integer count = ticketsSales.entrySet().stream().filter(entry -> entry.getKey().equals(sale.getTicket())).mapToInt(Map.Entry::getValue).sum();
            if(count + ticketsAll + 1 > maxCapacity)
                throw new IllegalArgumentException("There are no more tickets available.");
        }
        else {
            for(Map.Entry<Ticket, Integer> entry: ticketsSales.entrySet())
                if(entry.getValue() + ticketsAll + 1 > maxCapacity)
                    throw new IllegalArgumentException("There are no more tickets available.");

        }
        //
    }
}
