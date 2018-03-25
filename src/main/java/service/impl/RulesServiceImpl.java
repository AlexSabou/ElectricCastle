package service.impl;

import repository.RulesRepository;
import repository.impl.RulesRepositoryImpl;
import service.RulesService;
import service.validator.Validator;

import java.util.Arrays;
import java.util.List;

public class RulesServiceImpl implements RulesService {
    private RulesRepository rulesRepository;

    public RulesServiceImpl() {
        this.rulesRepository = new RulesRepositoryImpl();
    }

    public RulesServiceImpl(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @Override
    public Integer getCapacity() {
        return this.rulesRepository.getCapacity();
    }

    @Override
    public boolean updateCapacity(Integer capacity) throws IllegalArgumentException {
        if(capacity > 0)
            return this.rulesRepository.updateCapacity(capacity);
        else
            throw new IllegalArgumentException("Capacity must be a positive number.");
    }
}
