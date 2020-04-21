package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.models.service.ReceiptServiceModel;
import panda.domain.models.views.ReceiptViewModel;
import panda.service.ReceiptService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named
@RequestScoped
public class ReceiptDetailsBean {
    private ReceiptViewModel receiptViewModel;
    private ReceiptService receiptService;
    private ModelMapper modelMapper;

    public ReceiptDetailsBean() {
    }

    @Inject
    public ReceiptDetailsBean(ReceiptService receiptService, ModelMapper modelMapper) {
        this.receiptService = receiptService;
        this.modelMapper = modelMapper;
        receiptDetails();
    }

    public ReceiptViewModel getReceiptViewModel() {
        return receiptViewModel;
    }

    public void setReceiptViewModel(ReceiptViewModel receiptViewModel) {
        this.receiptViewModel = receiptViewModel;
    }

    public void  receiptDetails(){
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("receiptId");
        ReceiptServiceModel model = this.receiptService.findById(id);
        this.receiptViewModel = this.modelMapper.map(model,ReceiptViewModel.class);
        this.receiptViewModel.setRecipient(model.getRecipient().getUsername());
        String date = receiptViewModel.getIssuedOn();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.receiptViewModel.setIssuedOn(LocalDate.parse(date, formatter).format(formatter2));
    }
}
