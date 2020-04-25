package softuni.realestateagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.realestateagency.domain.entitys.Offer;
import softuni.realestateagency.domain.models.service.OfferFindServiceModel;
import softuni.realestateagency.domain.models.service.OfferServiceModel;
import softuni.realestateagency.repository.OfferRepository;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, Validator validator) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public void registerOffer(OfferServiceModel offerServiceModel) {
        if (this.validator.validate(offerServiceModel).size() != 0) {
            throw new IllegalArgumentException("Input data is wrong");
        }
        this.offerRepository.saveAndFlush(this.modelMapper.map(offerServiceModel, Offer.class));
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {
        return this.offerRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void findOffer(OfferFindServiceModel offerFindServiceModel) {
        if (this.validator.validate(offerFindServiceModel).size() != 0) {
            throw new IllegalArgumentException("Input data is wrong");
        }

        Offer offer = this.offerRepository.findAll().stream()
                .filter(o -> o.getApartmentType().toLowerCase().equals(offerFindServiceModel.getFamilyApartmentType().toLowerCase()) &&
                        offerFindServiceModel.getFamilyBudget()
                                .compareTo(o.getApartmentRent().add(o.getAgencyCommission()
                                        .divide(new BigDecimal("100")).multiply(o.getApartmentRent()))) >= 0)
                .findFirst()
                .orElse(null);

        if (offer == null) {
            throw new IllegalArgumentException("Input data is wrong");
        }
        this.offerRepository.delete(offer);
    }
}
