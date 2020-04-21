package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Status;
import panda.domain.models.views.PackageViewModel;
import panda.service.PackageService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class HomeBean {
    private List<PackageViewModel> pendingPackages;
    private List<PackageViewModel> deliveredPackages;
    private List<PackageViewModel> shippedPackages;
    private PackageService packageService;
    private ModelMapper modelMapper;

    public HomeBean() {
    }

    @Inject
    public HomeBean(PackageService packageService, ModelMapper modelMapper) {
        this.packageService = packageService;
        this.modelMapper = modelMapper;
        String username = findUser();
        initPendingPackages(username);
        initShippedPackages(username);
        initDeliveredPackages(username);
    }

    public List<PackageViewModel> getPendingPackages() {
        return pendingPackages;
    }

    public void setPendingPackages(List<PackageViewModel> pendingPackages) {
        this.pendingPackages = pendingPackages;
    }


    public List<PackageViewModel> getDeliveredPackages() {
        return deliveredPackages;
    }

    public void setDeliveredPackages(List<PackageViewModel> deliveredPackages) {
        this.deliveredPackages = deliveredPackages;
    }

    public List<PackageViewModel> getShippedPackages() {
        return shippedPackages;
    }

    public void setShippedPackages(List<PackageViewModel> shippedPackages) {
        this.shippedPackages = shippedPackages;
    }

    private String findUser() {
        return  (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true)).getAttribute("username");
    }


    public void initPendingPackages(String username) {
        this.pendingPackages =
                this.packageService.findAllByUserName(username).stream()
                        .filter(p -> p.getStatus().name().equals(Status.Pending.name()))
                        .map(p -> modelMapper.map(p,PackageViewModel.class)).collect(Collectors.toList());
    }

    public void initShippedPackages(String username) {
        this.shippedPackages =
                this.packageService.findAllByUserName(username).stream()
                        .filter(p -> p.getStatus().name().equals(Status.Shipped.name()))
                        .map(p -> modelMapper.map(p,PackageViewModel.class)).collect(Collectors.toList());
    }


    public void initDeliveredPackages(String username) {
        this.deliveredPackages =
                this.packageService.findAllByUserName(username).stream()
                        .filter(p -> p.getStatus().name().equals(Status.Delivered.name()))
                        .map(p -> modelMapper.map(p,PackageViewModel.class)).collect(Collectors.toList());
    }
}
