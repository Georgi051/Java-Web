package panda.service;

import panda.domain.models.service.ReceiptServiceModel;

import java.util.List;

public interface ReceiptService {

    void createReceipt(ReceiptServiceModel receiptServiceModel);

    List<ReceiptServiceModel> findAllByUserName(String username);

    void create(String username, String id);

    ReceiptServiceModel findById(String id);
}
