package com.example.residentevil.services.impl;

import com.example.residentevil.domain.entities.Capitals;
import com.example.residentevil.domain.entities.Virus;
import com.example.residentevil.repository.VirusRepository;
import com.example.residentevil.services.CapitalService;
import com.example.residentevil.services.VirusService;
import com.example.residentevil.services.serviceModels.CapitalServiceModel;
import com.example.residentevil.services.serviceModels.VirusServiceModel;
import com.example.residentevil.services.serviceModels.VirusServiceWithCapitalsModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class VirusServiceImpl implements VirusService {
    private final VirusRepository virusRepository;
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, CapitalService capitalService, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addVirus(VirusServiceModel virus) {

        if (this.virusRepository.findByName(virus.getName()) == null) {
            List<Capitals> capitals = findCapitalsById(virus.getCapitals());
            Virus virusSave = this.modelMapper.map(virus, Virus.class);
            virusSave.setCapitals(capitals);
            this.virusRepository.saveAndFlush(virusSave);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<VirusServiceWithCapitalsModel> allViruses() {
        return this.virusRepository.findAll().stream()
                .map(v -> this.modelMapper.map(v, VirusServiceWithCapitalsModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeVirusById(String id) {
        this.virusRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public VirusServiceWithCapitalsModel findById(String id) {
        Virus virus = this.virusRepository.findById(Long.parseLong(id)).orElse(null);
        if (virus != null) {
            VirusServiceWithCapitalsModel v = this.modelMapper.map(virus, VirusServiceWithCapitalsModel.class);
            v.setCapitals(virus.getCapitals().stream().map(c -> this.modelMapper.map(c, CapitalServiceModel.class))
                    .collect(Collectors.toList()));
            return v;
        } else {
            throw new IllegalArgumentException("Virus with this Id not found!");
        }
    }

    @Override
    public void changeVirusParams(VirusServiceModel virus, String id) {
        List<Capitals> capitals = findCapitalsById(virus.getCapitals());
        Virus dataBaseVirus = this.virusRepository.findById(Long.parseLong(id)).orElse(null);

        assert dataBaseVirus != null;
        for (Capitals capital : dataBaseVirus.getCapitals()) {
            capitals.removeIf(c -> c.equals(capital));
        }
        dataBaseVirus.getCapitals().addAll(capitals);
        Virus virusSave = this.modelMapper.map(virus, Virus.class);
        virusSave.setCapitals(dataBaseVirus.getCapitals());
        virusSave.setId(dataBaseVirus.getId());
        virusSave.setReleasedOn(dataBaseVirus.getReleasedOn());
        this.virusRepository.saveAndFlush(virusSave);
    }

    private List<Capitals> findCapitalsById(List<Long> id) {
        return this.capitalService.findCapitalsById(id);
    }
}
