package com.example.residentevil.services.impl;

import com.example.residentevil.domain.entities.Capitals;
import com.example.residentevil.repository.CapitalsRepository;
import com.example.residentevil.services.CapitalService;
import com.example.residentevil.services.serviceModels.CapitalServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {
    private final CapitalsRepository capitalsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CapitalServiceImpl(CapitalsRepository capitalsRepository, ModelMapper modelMapper) {
        this.capitalsRepository = capitalsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalServiceModel> getAllCapitals() {
        return this.capitalsRepository.findByNameOrderByNameAsc().stream()
                .map(c -> this.modelMapper.map(c,CapitalServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Capitals> findCapitalsById(List<Long> capitalsId) {
        List<Capitals> capitalsFound = new ArrayList<>();
        capitalsId.
                forEach(id -> capitalsFound.add(this.modelMapper.map(this.capitalsRepository.getById(id)
                        ,Capitals.class)));
        return capitalsFound;
    }
}
