package softuni.realestateagency.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import softuni.realestateagency.domain.models.binding.OfferFindBindingModel;
import softuni.realestateagency.domain.models.service.OfferFindServiceModel;
import softuni.realestateagency.domain.models.view.OfferViewModel;
import softuni.realestateagency.service.OfferService;
import softuni.realestateagency.util.HtmlReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller()
public class HomeController {
    private final OfferService offerService;
    private final HtmlReader htmlReader;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(OfferService offerService, HtmlReader htmlReader, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.htmlReader = htmlReader;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {
        return this.prepareHtml();
    }

    private String prepareHtml() throws IOException {
        List<OfferViewModel> offers = this.offerService.findAllOffers()
                .stream()
                .map(o -> modelMapper.map(o, OfferViewModel.class))
                .collect(Collectors.toList());

        StringBuilder offerHtml = new StringBuilder();

        if (offers.size() == 0) {
            offerHtml
                    .append("<div class=\"apartment\" style=\"border: red solid 1px\">")
                    .append("There aren't any offers!")
                    .append("</div>");

            return this.htmlReader.readHtmlFile("src/main/resources/static/index.html")
                    .replace("{{offers}}", offerHtml.toString().trim());
        } else {
            for (OfferViewModel offer : offers) {
                offerHtml.append("<div class=\"apartment\">")
                        .append(String.format("<p>Rent: %s</p>", offer.getApartmentRent()))
                        .append(String.format("<p>Type: %s</p>", offer.getApartmentType()))
                        .append(String.format("<p>Commission: %s</p>", offer.getAgencyCommission()))
                        .append("</div>")
                        .append(System.lineSeparator());
            }
        }
        return this.htmlReader.readHtmlFile("src/main/resources/static/index.html")
                .replace("{{offers}}", offerHtml.toString().trim());
    }

    @GetMapping("/find")
    public String find() {
        return "find.html";
    }

    @PostMapping("/find")
    public String findConfirm(@ModelAttribute(name = "model") OfferFindBindingModel offerFindBindingModel) {
        try {
            this.offerService.findOffer(this.modelMapper.map(offerFindBindingModel, OfferFindServiceModel.class));
        } catch (IllegalArgumentException ex) {
            return "redirect:/find";
        }
        return "redirect:/";
    }
}
