package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.models.binding.PackageCreateBindingModel;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.service.UserServiceModel;
import panda.service.PackageService;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class PackageCreateBean {
    private List<String> users;
    private PackageService packageService;
    private UserService userService;
    private PackageCreateBindingModel packageCreateBindingModel;
    private ModelMapper modelMapper;


    public PackageCreateBean() {
    }

    @Inject
    public PackageCreateBean(PackageService packageService, UserService userService, ModelMapper modelMapper) {
        this.packageService = packageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.initUnits();
        this.initModel();
        this.initReceipt();
    }

    private void initReceipt() {

    }

    private void initUnits() {
        this.users  = this.userService.findAll().stream()
                .map(UserServiceModel::getUsername)
                .collect(Collectors.toList());
    }

    private void initModel() {
        this.packageCreateBindingModel = new PackageCreateBindingModel();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public PackageCreateBindingModel getPackageCreateBindingModel() {
        return packageCreateBindingModel;
    }

    public void setPackageCreateBindingModel(PackageCreateBindingModel packageCreateBindingModel) {
        this.packageCreateBindingModel = packageCreateBindingModel;
    }

    public void create() throws IOException {
        PackageServiceModel model = this.modelMapper.map(this.packageCreateBindingModel, PackageServiceModel.class);
        model.setRecipient(this.userService.findByUserName(this.packageCreateBindingModel.getRecipient()));

        this.packageService.createPackage(model);

        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("/faces/view/home.xhtml");
    }
}
