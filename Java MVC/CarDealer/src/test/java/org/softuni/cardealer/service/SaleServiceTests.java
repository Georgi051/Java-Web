package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.*;
import org.softuni.cardealer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SaleServiceTests {

    @Autowired
    private CarSaleRepository carSaleRepository;
    @Autowired
    private PartSaleRepository partSaleRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private PartRepository partRepository;

    private ModelMapper modelMapper;
    private SaleService saleService;
    private Car car;
    private Customer customer;
    private Part part;

    @Before
    public void init() {
        modelMapper = new ModelMapper();
        saleService = new SaleServiceImpl(this.carSaleRepository, this.partSaleRepository, this.modelMapper);

        car = new Car();
        car.setMake("BMW");
        car.setModel("3");
        car.setTravelledDistance(2000L);
        this.carRepository.saveAndFlush(this.car);

        this.customer = new Customer();
        this.customer.setName("Pesho");
        this.customer.setBirthDate(LocalDate.now());
        this.customer.setYoungDriver(true);
        customerRepository.saveAndFlush(this.customer);

        Supplier supplier = new Supplier();
        supplier.setName("Gosho");
        supplier.setImporter(false);
        this.supplierRepository.saveAndFlush(supplier);
        this.part = new Part();
        this.part.setSupplier(supplier);
        this.part.setPrice(new BigDecimal("20.0"));
        this.part.setName("Pipe");
        this.partRepository.saveAndFlush(this.part);
    }


    @Test
    public void saleService_saveCarSaleInDataBase() {
        CarSaleServiceModel toBeSave = new CarSaleServiceModel();
        CarServiceModel carModel = this.modelMapper.map(this.car, CarServiceModel.class);
        toBeSave.setCar(carModel);
        toBeSave.setDiscount(5.0);
        toBeSave.setCustomer(this.modelMapper.map(this.customer, CustomerServiceModel.class));

        CarSaleServiceModel actual = this.saleService.saleCar(toBeSave);
        CarSaleServiceModel expected = this.modelMapper.map(this.carSaleRepository.findAll().get(0), CarSaleServiceModel.class);

        Assert.assertEquals(expected.getCar().getMake(), actual.getCar().getMake());
        Assert.assertEquals(expected.getCar().getModel(), actual.getCar().getModel());
        Assert.assertEquals(expected.getCar().getTravelledDistance(), actual.getCar().getTravelledDistance());
    }


    @Test
    public void saleService_salePartSaveInDataBase() {
        PartSaleServiceModel tobeSave = new PartSaleServiceModel();
        PartServiceModel partModel = this.modelMapper.map(this.part, PartServiceModel.class);
        tobeSave.setPart(partModel);
        tobeSave.setQuantity(20);
        tobeSave.setDiscount(5.0);

        PartSaleServiceModel actual = this.saleService.salePart(tobeSave);
        PartSaleServiceModel expected = this.modelMapper.map(this.partSaleRepository.findAll().get(0), PartSaleServiceModel.class);

        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
        Assert.assertEquals(expected.getPart().getName(), actual.getPart().getName());
        Assert.assertEquals(expected.getPart().getPrice(), actual.getPart().getPrice());
    }
}
