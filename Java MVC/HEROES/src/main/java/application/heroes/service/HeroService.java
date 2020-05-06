package application.heroes.service;

import application.heroes.service.serviceModels.HeroServiceModel;

public interface HeroService {
    HeroServiceModel getHeroByName(String name);

    HeroServiceModel createHero(String username, HeroServiceModel map);

    boolean addItemInHeroAndReturnHero(String itemId, String heroname);
}
