package application.heroes.service.impl;

import application.heroes.domain.entities.*;
import application.heroes.repository.HeroRepository;
import application.heroes.service.HeroService;
import application.heroes.service.ItemService;
import application.heroes.service.UserService;
import application.heroes.service.serviceModels.HeroServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HeroServiceImpl implements HeroService {
    private final HeroRepository heroRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ItemService itemService;


    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, ModelMapper modelMapper, UserService userService, ItemService itemService) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public HeroServiceModel getHeroByName(String name) {
        return this.modelMapper.map(this.heroRepository.findByName(name), HeroServiceModel.class);
    }

    @Override
    public HeroServiceModel createHero(String username, HeroServiceModel model) {

        if (this.heroRepository.findByName(model.getName()) != null) {
            throw new IllegalArgumentException("Hero with that name already exist!");
        }

        Hero hero = this.modelMapper.map(model, Hero.class);
        hero.setGender(Gender.valueOf(model.getGender().toUpperCase()));

        User userByName = this.userService.findUserByName(username);
        if (userByName == null) {
            throw new IllegalArgumentException("User not found!");
        }
        hero.setUser(userByName);
        this.heroRepository.saveAndFlush(hero);
        this.userService.addHeroToUser(username, hero);
        return this.modelMapper.map(hero, HeroServiceModel.class);
    }

    @Override
    public boolean addItemInHeroAndReturnHero(String itemId, String heroname) {
        Item item = this.modelMapper.map(this.itemService.getItem(itemId), Item.class);
        Hero hero = this.heroRepository.findByName(heroname);
        boolean findExistItem = hero.getHeroItems().stream()
                .anyMatch(i -> (Slot.valueOf(i.getSlot().toString()).equals(Slot.valueOf(item.getSlot().toString()))));
        if (!findExistItem) {
            hero.getHeroItems().add(item);
            this.heroRepository.saveAndFlush(hero);
            return true;
        } else {
            return false;
        }
    }
}
