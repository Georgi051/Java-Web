package app.exodia.web.controllers;

import app.exodia.domain.models.binding.DocumentBindingModel;
import app.exodia.domain.models.service.DocumentServiceModel;
import app.exodia.domain.models.view.DocumentDetailsViewModel;
import app.exodia.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView, HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else {
            modelAndView.setViewName("/schedule");
        }
        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView scheduleConfirm(@ModelAttribute DocumentBindingModel model, ModelAndView modelAndView){
        DocumentServiceModel documentServiceModel = this.documentService.save(this.modelMapper.map(model,DocumentServiceModel.class));
        if (documentServiceModel == null){
            modelAndView.setViewName("redirect:/schedule");
        }else {
            modelAndView.setViewName("redirect:/details/" + documentServiceModel.getId());
        }
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id, ModelAndView modelAndView,HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else {
            DocumentServiceModel serviceModel = this.documentService.findById(id);
            if (serviceModel == null){
                throw  new IllegalArgumentException("Document not found!");
            }else{
                modelAndView.setViewName("/details");
                modelAndView.addObject("model",this.modelMapper.map(serviceModel, DocumentDetailsViewModel.class));
            }
        }
        return modelAndView;
    }

    @GetMapping("/print/{id}")
    public ModelAndView print(@PathVariable("id")String id, ModelAndView modelAndView ,HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else {
            DocumentServiceModel serviceModel = this.documentService.findById(id);
            if (serviceModel == null){
                throw  new IllegalArgumentException("Document not found!");
            }else{
                modelAndView.setViewName("/print");
                modelAndView.addObject("model",this.modelMapper.map(serviceModel,
                        DocumentDetailsViewModel.class));
            }
        }
        return modelAndView;
    }

    @PostMapping("/print/{id}")
    public ModelAndView confirmPrint(@PathVariable("id")String id, ModelAndView modelAndView){
        if (!this.documentService.printDocumentById(id)){
            throw new IllegalArgumentException("Something went wrong!");
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
