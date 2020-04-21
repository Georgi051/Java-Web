package panda.service.impl;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Package;
import panda.domain.entities.Receipt;
import panda.domain.entities.User;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.service.ReceiptServiceModel;
import panda.domain.models.service.UserServiceModel;
import panda.repositoty.ReceiptsRepository;
import panda.service.PackageService;
import panda.service.ReceiptService;
import panda.service.UserService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptServiceImpl implements ReceiptService {
    private final UserService userService;
    private final PackageService packageService;
    private final ReceiptsRepository receiptsRepository;
    private final ModelMapper modelMapper;

    @Inject
    public ReceiptServiceImpl(UserService userService, PackageService packageService, ReceiptsRepository receiptsRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.packageService = packageService;
        this.receiptsRepository = receiptsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createReceipt(ReceiptServiceModel receiptServiceModel) {
        Receipt receipt = this.modelMapper.map(receiptServiceModel,Receipt.class);
        this.receiptsRepository.save(receipt);
    }

    @Override
    public List<ReceiptServiceModel> findAllByUserName(String username) {
        List<ReceiptServiceModel> allReceipts = this.receiptsRepository.findAllByUsername(username)
                .stream()
                .map(receipt -> this.modelMapper.map(receipt,ReceiptServiceModel.class))
                .collect(Collectors.toList());
        return allReceipts;
    }

    @Override
    public void create(String username, String packageId) {
        this.packageService.changePackageStatus(packageId);
        PackageServiceModel aPackage = this.packageService.findById(packageId);
        Package dataBasePackage = this.modelMapper.map(aPackage, Package.class);

        UserServiceModel userServiceModel = this.userService.findByUserName(username);
        User user = this.modelMapper.map(userServiceModel,User.class);

        Receipt receipt = new Receipt();
        receipt.setaPackage(dataBasePackage);
        receipt.setRecipient(user);
        receipt.setIssuedOn(LocalDate.now());
        BigDecimal multiplicationValue = BigDecimal.valueOf(2.67);
        BigDecimal fee = BigDecimal.valueOf(aPackage.getWeight()).multiply(multiplicationValue);
        receipt.setFee(fee);
        this.receiptsRepository.save(receipt);
    }

    @Override
    public ReceiptServiceModel findById(String id) {
        return this.modelMapper.map(receiptsRepository.findById(id),ReceiptServiceModel.class);
    }
}
