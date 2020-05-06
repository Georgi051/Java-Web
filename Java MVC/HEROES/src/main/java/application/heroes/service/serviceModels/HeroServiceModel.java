package application.heroes.service.serviceModels;

import java.util.List;

public class HeroServiceModel {
    private String name;
    private String gender;
    private String level;
    private String stamina;
    private String strength;
    private String attack;
    private String defence;
    private List<ItemServiceModel> items;

    public HeroServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStamina() {
        return stamina;
    }

    public void setStamina(String stamina) {
        this.stamina = stamina;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefence() {
        return defence;
    }

    public void setDefence(String defence) {
        this.defence = defence;
    }

    public List<ItemServiceModel> getItems() {
        return items;
    }

    public void setItems(List<ItemServiceModel> items) {
        this.items = items;
    }
}
