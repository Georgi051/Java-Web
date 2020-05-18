package com.example.residentevil.services;

import com.example.residentevil.services.serviceModels.VirusServiceModel;
import com.example.residentevil.services.serviceModels.VirusServiceWithCapitalsModel;

import java.util.List;

public interface VirusService {
    boolean addVirus(VirusServiceModel virus);

    List<VirusServiceWithCapitalsModel> allViruses();

    void removeVirusById(String id);

    VirusServiceWithCapitalsModel findById(String id);

    void changeVirusParams(VirusServiceModel virus,String id);
}
