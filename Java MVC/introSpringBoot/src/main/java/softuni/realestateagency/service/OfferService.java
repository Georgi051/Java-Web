package softuni.realestateagency.service;

import softuni.realestateagency.domain.models.service.OfferFindServiceModel;
import softuni.realestateagency.domain.models.service.OfferServiceModel;

import java.util.List;

public interface OfferService {

    void registerOffer(OfferServiceModel offerServiceModel);

    List<OfferServiceModel> findAllOffers();

    void findOffer(OfferFindServiceModel offerFindServiceModel);
}
