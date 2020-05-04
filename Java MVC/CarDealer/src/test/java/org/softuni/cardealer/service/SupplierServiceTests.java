package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;

import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTests {

    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private SupplierService supplierService;
    private Supplier supplier;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        supplierService  = new SupplierServiceImpl(this.supplierRepository,this.modelMapper);

        this.supplier = new Supplier();
        this.supplier.setName("Pesho");
        this.supplier.setImporter(true);
        this.supplier = this.supplierRepository.saveAndFlush(this.supplier);
    }

    @Test
    public void supplierService_saveSupplierWithCurrentValuesIntoDateBase(){
        SupplierServiceModel toBeSave = new SupplierServiceModel();
        toBeSave.setName("Pesho");
        toBeSave.setImporter(true);

        SupplierServiceModel actual = this.supplierService.saveSupplier(toBeSave);
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(1),SupplierServiceModel.class);

        Assert.assertEquals(expected.getId(),actual.getId());
        Assert.assertEquals(expected.getName(),actual.getName());
        Assert.assertEquals(expected.isImporter(),actual.isImporter());
    }


    @Test(expected = Exception.class)
    public void supplierService_saveSupplierWithIncorrectValuesInDataBaseShouldThrowException(){
        SupplierServiceModel toBeSave = new SupplierServiceModel();
        toBeSave.setName(null);
        toBeSave.setImporter(true);
        this.supplierService.saveSupplier(toBeSave);
    }

    @Test
    public void supplierService_editSupplierWithCorrectValuesShouldReturnCurrentResult(){
        SupplierServiceModel editedSupplier = new SupplierServiceModel();
        editedSupplier.setId(this.supplier.getId());
        editedSupplier.setName("Gosho");
        editedSupplier.setImporter(false);

        SupplierServiceModel actual = supplierService.editSupplier(editedSupplier);
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0),SupplierServiceModel.class);

        Assert.assertEquals(expected.getId(),actual.getId());
        Assert.assertEquals(expected.getName(),actual.getName());
        Assert.assertEquals(expected.isImporter(),actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_editSuppliersWithNullValuesShouldThrowException(){

        SupplierServiceModel editedSupplier = new SupplierServiceModel();
        editedSupplier.setId(this.supplier.getId());
        editedSupplier.setName(null);
        editedSupplier.setImporter(false);

       this.supplierService.editSupplier(editedSupplier);
    }


    @Test
    public void supplierService_deleteSupplierWithValidIdReturnsCorrectResult(){
        this.supplierService.deleteSupplier(this.supplier.getId());
        Assert.assertEquals(0,this.supplierRepository.count());
    }

    @Test(expected = Exception.class)
    public void supplierService_deleteSupplierWithIncorrectIdShouldThrowException(){
        this.supplierService.deleteSupplier("sdadasd");
    }

    @Test
    public void supplierService_findSupplierWithCorrectIdReturnsCorrectResult(){
        SupplierServiceModel actual = this.supplierService.findSupplierById(this.supplier.getId());
        Assert.assertEquals(this.supplier.getId(),actual.getId());
    }

    @Test(expected = Exception.class)
    public void supplierService_findSupplierWithIncorrectIdShouldThrowException(){
      this.supplierService.findSupplierById("sdadadada");
    }
}
