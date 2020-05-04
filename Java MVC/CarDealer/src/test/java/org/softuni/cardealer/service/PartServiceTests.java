package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTests {

    @Autowired
    private PartRepository partRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private PartService partService;
    private Part part;
    private SupplierServiceModel supplierServiceModel;


    @Before
    public void init(){
        modelMapper = new ModelMapper();
        partService = new PartServiceImpl(this.partRepository,this.modelMapper);

        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(false);
        this.supplierRepository.saveAndFlush(supplier);
        supplierServiceModel = this.modelMapper.map(supplier,SupplierServiceModel.class);

        part = new Part();
        this.part.setName("wheel");
        this.part.setPrice(new BigDecimal("20.0"));
        this.part.setSupplier(this.modelMapper.map(this.supplierServiceModel,Supplier.class));
        this.partRepository.saveAndFlush(this.part);
    }

    @Test
    public void partService_savePartWithCurrentValuesIntoDataBase(){
        PartServiceModel tobeSave = new PartServiceModel();
        tobeSave.setName("mirror");
        tobeSave.setPrice(new BigDecimal("15.0"));
        tobeSave.setSupplier(this.supplierServiceModel);

        PartServiceModel actual = this.partService.savePart(tobeSave);
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(1),PartServiceModel.class);

        Assert.assertEquals(expected.getName(),actual.getName());
        Assert.assertEquals(expected.getPrice(),actual.getPrice());
        Assert.assertEquals(expected.getSupplier().getId(),actual.getSupplier().getId());
    }

    @Test(expected = Exception.class)
    public void partService_savePartWithIncorrectValueForNameShouldThrowException(){
        PartServiceModel part = new PartServiceModel();
        part.setName(null);
        part.setPrice(new BigDecimal("15.0"));
        part.setSupplier(this.supplierServiceModel);
        this.partService.savePart(part);
    }

    @Test(expected = Exception.class)
    public void partService_savePartWithIncorrectValueForPriceShouldThrowException(){
        PartServiceModel part = new PartServiceModel();
        part.setName("wheel");
        part.setPrice(null);
        part.setSupplier(this.supplierServiceModel);
        this.partService.savePart(part);
    }

    @Test
    public void partService_editPartWithCurrentValues(){
        PartServiceModel editedPart = new PartServiceModel();
        editedPart.setId(part.getId());
        editedPart.setName("tyre");
        editedPart.setPrice(new BigDecimal("15.0"));

        PartServiceModel actual = this.partService.editPart(editedPart);
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0),PartServiceModel.class);

        Assert.assertEquals(expected.getId(),actual.getId());
        Assert.assertEquals(expected.getName(),actual.getName());
        Assert.assertEquals(expected.getPrice(),actual.getPrice());
        Assert.assertEquals(expected.getSupplier().getId(),actual.getSupplier().getId());
    }

    @Test(expected = Exception.class)
    public void partService_editPartWithIncorrectValueForNameShouldThrowException(){
        PartServiceModel editedPart = new PartServiceModel();
        editedPart.setId(part.getId());
        editedPart.setName(null);
        editedPart.setPrice(new BigDecimal("15.0"));
        this.partService.editPart(editedPart);
    }

    @Test(expected = Exception.class)
    public void partService_editPartWithIncorrectValueForPriceShouldThrowException(){
        PartServiceModel editedPart = new PartServiceModel();
        editedPart.setId(this.part.getId());
        editedPart.setName("tyre");
        editedPart.setPrice(null);
        this.partService.editPart(editedPart);
    }

    @Test
    public void partService_deletePartWithValidIdShouldReturnCorrectResults(){
        this.partService.deletePart(this.part.getId());
        Assert.assertEquals(0,this.partRepository.count());
    }

    @Test(expected = NoSuchElementException.class)
    public void partService_deletePartWithIncorrectIdShouldThrowException(){
        this.partService.deletePart("sdadasda");
    }

    @Test
    public void partService_findPartByIdShouldReturnCorrectResult(){
        PartServiceModel actual = this.partService.findPartById(this.part.getId());
        Assert.assertEquals(this.part.getId(),actual.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void partService_findPartWithNotExistIdShouldThrowException(){
        this.partService.findPartById("dasdasdasd");
    }
}
