package application.heroes.service.serviceModels;

import javax.validation.constraints.NotNull;

public class ItemServiceModel {
    private String id;
    private String name;
    private String slot;
    private Integer stamina;
    private Integer strength;
    private Integer attack;
    private Integer defence;
    private String deletedItem;

    public ItemServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefence() {
        return defence;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public String getDeletedItem() {
        return deletedItem;
    }

    public void setDeletedItem(String deletedItem) {
        this.deletedItem = deletedItem;
    }
}
