package panda.service;

import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.service.PackageServiceModelWithDate;

import java.util.List;

public interface PackageService {

    void createPackage(PackageServiceModel packageServiceModel);

    List<PackageServiceModel> findAllPackagesByStatus(Status status);

    List<PackageServiceModelWithDate> findAllPackagesByStatusWithDate(Status status);

    void changePackageStatus(String id);

    List<PackageServiceModel> findAllByUserName(String username);

    PackageServiceModel findById(String id);
}
