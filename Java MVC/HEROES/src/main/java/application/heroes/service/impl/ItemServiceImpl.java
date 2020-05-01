package application.heroes.service.impl;

import application.heroes.domain.entities.Item;
import application.heroes.repository.ItemRepository;
import application.heroes.service.ItemService;
import application.heroes.service.serviceModels.ItemServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ItemServiceModel createItem(ItemServiceModel item) {
        if (this.itemRepository.findByName(item.getName()) != null){
           throw new IllegalArgumentException("Item is already created!");
        }else {
            this.itemRepository.saveAndFlush(this.modelMapper.map(item, Item.class));
            return item;
        }
    }

    @Override
    public List<ItemServiceModel> getAllItems() {
        return this.itemRepository.findAll().stream()
                .map(i -> this.modelMapper.map(i,ItemServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ItemServiceModel getItem(String itemId) {
        Item item = this.itemRepository.findById(Long.parseLong(itemId)).orElse(null);

        if (item != null){
            return this.modelMapper.map(item,ItemServiceModel.class);
        }
        return null;
    }

    @Override
    public void deleteItemById(String id) {
        Item item = this.itemRepository.findById(Long.parseLong(id)).orElse(null);
        if (item != null){
          item.setDeletedItem("deleted");
          this.itemRepository.saveAndFlush(item);
        }
    }
}
