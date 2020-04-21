package panda.service.impl;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Package;
import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.service.PackageServiceModelWithDate;
import panda.repositoty.PackageRepository;
import panda.service.PackageService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PackageServiceImpl implements PackageService {
    private final PackageRepository packageRepository;
    private final ModelMapper modelMapper;

    @Inject
    public PackageServiceImpl(PackageRepository packageRepository, ModelMapper modelMapper) {
        this.packageRepository = packageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createPackage(PackageServiceModel packageServiceModel) {
        Package aPackage = this.modelMapper.map(packageServiceModel,Package.class);
        aPackage.setStatus(Status.Pending);
        this.packageRepository.save(aPackage);
    }

    @Override
    public List<PackageServiceModel> findAllPackagesByStatus(Status status) {
        return this.packageRepository.findAllPackagesByStatus(status).stream()
                .map(p -> this.modelMapper.map(p, PackageServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PackageServiceModelWithDate> findAllPackagesByStatusWithDate(Status status) {
        List<PackageServiceModelWithDate> collect = this.packageRepository.findAllPackagesByStatus(status).stream()
                .map(p -> this.modelMapper.map(p,PackageServiceModelWithDate.class))
                .collect(Collectors.toList());

        collect.forEach(p -> {
            String date = p.getEstimatedDeliveryDate();
            String dateToRefactor = date.substring(0,10);
            String year = dateToRefactor.substring(0,4);
            String mouth= dateToRefactor.substring(5,7);
            String day = dateToRefactor.substring(8,10);
            String dateAfterRefactoring = day+"/"+mouth+"/"+year;
            p.setEstimatedDeliveryDate(dateAfterRefactoring);
        });
        return collect;
    }

    @Override
    public void changePackageStatus(String id) {
        Package aPackage = this.packageRepository.findById(id);
        changeStatus(aPackage);
        changeDeliveryDate(aPackage);
        this.packageRepository.updatePackage(aPackage);
    }

    @Override
    public List<PackageServiceModel> findAllByUserName(String username) {
        return this.packageRepository.findAllPackagesByUser(username).stream()
                .map(p -> this.modelMapper.map(p,PackageServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PackageServiceModel findById(String id) {
        return this.modelMapper.map(this.packageRepository.findById(id),PackageServiceModel.class);
    }


    private void changeStatus(Package  aPackage){
        switch (aPackage.getStatus().name()){
            case "Pending": aPackage.setStatus(Status.Shipped);break;
            case "Shipped": aPackage.setStatus(Status.Delivered);break;
            case "Delivered":aPackage.setStatus(Status.Acquired);break;
        }
    }

    private void changeDeliveryDate(Package aPackage) {
        Random random = new Random();
        long days = random.nextInt((40-20) + 1) + 20;
        aPackage.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(days));
    }
}
