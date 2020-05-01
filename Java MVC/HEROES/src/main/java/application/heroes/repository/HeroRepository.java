package application.heroes.repository;

import application.heroes.domain.entities.Hero;
import application.heroes.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {
    Hero findByName(String name);

    Hero findByUser(User user);
}
