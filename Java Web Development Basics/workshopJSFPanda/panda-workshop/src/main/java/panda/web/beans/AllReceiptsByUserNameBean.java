package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.models.views.ReceiptViewModel;
import panda.service.ReceiptService;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class AllReceiptsByUserNameBean {
    private UserService userService;
    private ReceiptService receiptService;
    private List<ReceiptViewModel> receiptViewModels;
    private ModelMapper modelMapper;

    public AllReceiptsByUserNameBean() {
    }

    @Inject
    public AllReceiptsByUserNameBean(UserService userService, ReceiptService receiptService, ModelMapper modelMapper) {
        this.userService = userService;
        this.receiptService = receiptService;
        this.modelMapper = modelMapper;
        initReceipts();
    }

    public List<ReceiptViewModel> getReceiptViewModels() {
        return receiptViewModels;
    }

    public void setReceiptViewModel(List<ReceiptViewModel> receiptViewModels) {
        this.receiptViewModels = receiptViewModels;
    }

    private void initReceipts() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String username = (String) session.getAttribute("username");

        this.receiptViewModels = receiptService.findAllByUserName(username)
                .stream()
                .map(receiptServiceModel -> {
                    ReceiptViewModel receiptViewModel = modelMapper.map(receiptServiceModel, ReceiptViewModel.class);
                    receiptViewModel.setRecipient(receiptServiceModel.getRecipient().getUsername());
                    String date = receiptViewModel.getIssuedOn();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    receiptViewModel.setIssuedOn(LocalDate.parse(date, formatter).format(formatter2));
                    return receiptViewModel;
                }).collect(Collectors.toList());
    }
}
