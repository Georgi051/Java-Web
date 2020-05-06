package application.heroes.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@Table(name = "heros")
public class Hero extends BaseEntity{
    private String name;
    private Gender gender;
    private Integer level;
    private Integer stamina;
    private Integer strength;
    private Integer attack;
    private Integer defence;
    private Set<Item> heroItems;
    private User user;

    public Hero() {
    }

    @Column(name = "name",nullable = false,unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "gender",nullable = false)
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(nullable = false)
    @Min(value = 0)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level == null ? 0 : level;
    }

    @Column(nullable = false)
    @Min(value = 0)
    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina == null ? 0 : stamina;
    }

    @Column(nullable = false)
    @Min(value = 0)
    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength == null ? 0 : strength;
    }

    @Column(nullable = false)
    @Min(value = 0)
    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack == null ? 0 : attack;
    }

    @Column(nullable = false)
    @Min(value = 0)
    public Integer getDefence() {
        return defence;
    }

    public void setDefence(Integer defence) {
        this.defence = defence == null ? 0 : defence;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "hero_items",
    joinColumns = @JoinColumn(name = "hero_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "item_id",referencedColumnName = "id"))
    public Set<Item> getHeroItems() {
        return heroItems;
    }

    public void setHeroItems(Set<Item> heroItems) {
        this.heroItems = heroItems;
    }

    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
