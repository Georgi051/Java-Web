package application.heroes.service;

import application.heroes.service.serviceModels.ItemServiceModel;

import java.util.List;

public interface ItemService {
    ItemServiceModel createItem(ItemServiceModel item);

    List<ItemServiceModel> getAllItems();

    void deleteItemById(String id);

    ItemServiceModel getItem(String itemId);

}
