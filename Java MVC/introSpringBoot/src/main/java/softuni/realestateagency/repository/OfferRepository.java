package softuni.realestateagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.realestateagency.domain.entitys.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer,String> {

}
