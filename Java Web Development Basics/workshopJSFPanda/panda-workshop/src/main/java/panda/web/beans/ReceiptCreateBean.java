package panda.web.beans;

import panda.service.ReceiptService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class ReceiptCreateBean {
    private ReceiptService receiptService;

    public ReceiptCreateBean() {
    }

    @Inject
    public ReceiptCreateBean(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public void userDelivered (String id) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String username = (String) session.getAttribute("username");
        this.receiptService.create(username, id);
    }
}
