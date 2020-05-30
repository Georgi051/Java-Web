package com.example.residentevil.services;

        import com.example.residentevil.domain.entities.Capitals;
        import com.example.residentevil.services.serviceModels.CapitalServiceModel;

        import java.util.List;

public interface CapitalService {
    List<CapitalServiceModel> getAllCapitals();

    List<Capitals> findCapitalsById(List<String> capitalsId);
}
