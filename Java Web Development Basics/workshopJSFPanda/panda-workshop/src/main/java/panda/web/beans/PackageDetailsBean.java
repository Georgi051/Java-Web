package panda.web.beans;

import jdk.jfr.Registered;
import org.modelmapper.ModelMapper;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.views.PackageViewModel;
import panda.service.PackageService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Registered
public class PackageDetailsBean {
    private PackageViewModel packageViewModel;
    private PackageService packageService;
    private ModelMapper modelMapper;

    public PackageDetailsBean() {
    }

    @Inject
    public PackageDetailsBean(PackageService packageService, ModelMapper modelMapper) {
        this.packageService = packageService;
        this.modelMapper = modelMapper;
        getDetails();
    }

    public PackageViewModel getPackageViewModel() {
        return packageViewModel;
    }

    public void setPackageViewModel(PackageViewModel packageViewModel) {
        this.packageViewModel = packageViewModel;
    }

    private void getDetails() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageId");
        PackageServiceModel packageServiceModel = packageService.findById(id);

        this.packageViewModel = modelMapper.map(packageServiceModel,PackageViewModel.class);
        this.packageViewModel.setRecipient(packageServiceModel.getRecipient().getUsername());
        if (this.packageViewModel.getEstimatedDeliveryDate() != null){
            String date = changeDate(this.packageViewModel);
            this.packageViewModel.setEstimatedDeliveryDate(date);
        }
    }

    private String changeDate(PackageViewModel model) {
            String date = model.getEstimatedDeliveryDate();
            String dateToRefactor = date.substring(0,10);
            String year = dateToRefactor.substring(0,4);
            String mouth= dateToRefactor.substring(5,7);
            String day = dateToRefactor.substring(8,10);
            return  day+"/"+mouth+"/"+year;
    }
}
