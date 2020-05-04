package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTests {

    @Autowired
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
    private CustomerService customerService;
    private Customer customer;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.customerService = new CustomerServiceImpl(this.customerRepository,this.modelMapper);

        this.customer = new Customer();
        this.customer.setName("Pesho");
        this.customer.setYoungDriver(true);
        this.customer.setBirthDate(LocalDate.of(2007,5,3));
        this.customerRepository.saveAndFlush(this.customer);
    }

    @Test
    public void customerService_saveCustomerInDataBase(){
        CustomerServiceModel toBeSave = new CustomerServiceModel();
        toBeSave.setName("Tosho");
        toBeSave.setYoungDriver(true);
        toBeSave.setBirthDate(LocalDate.now());

        CustomerServiceModel actual = this.customerService.saveCustomer(toBeSave);
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(1),CustomerServiceModel.class);

        Assert.assertEquals(expected.getName(),actual.getName());
        Assert.assertEquals(expected.getBirthDate(),actual.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void customerService_saveCustomerWithInvalidNameShouldThrowException(){
        CustomerServiceModel toBeSave = new CustomerServiceModel();
        toBeSave.setName(null);
        toBeSave.setYoungDriver(true);
        toBeSave.setBirthDate(LocalDate.now());
        this.customerService.saveCustomer(toBeSave);
    }

    @Test(expected = Exception.class)
    public void customerService_saveCustomerWithInvalidBirthDateShouldThrowException(){
        CustomerServiceModel toBeSave = new CustomerServiceModel();
        toBeSave.setName("Tosho");
        toBeSave.setYoungDriver(true);
        toBeSave.setBirthDate(null);
        this.customerService.saveCustomer(toBeSave);
    }

    @Test
    public void customerService_editCustomerWithValidValuesShouldReturnCorrectResult(){
        CustomerServiceModel toBeSave = new CustomerServiceModel();
        toBeSave.setId(this.customer.getId());
        toBeSave.setName("Gosho");
        toBeSave.setBirthDate(LocalDate.of(1994,10,5));
        toBeSave.setYoungDriver(false);

        CustomerServiceModel actual = this.customerService.saveCustomer(toBeSave);
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(0),CustomerServiceModel.class);

        Assert.assertEquals(expected.getName(),actual.getName());
        Assert.assertEquals(expected.getBirthDate(),actual.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void customerService_editCustomerWithInvalidValueForNameShouldThrowException(){
        CustomerServiceModel toBeSave = new CustomerServiceModel();
        toBeSave.setId(this.customer.getId());
        toBeSave.setName(null);
        toBeSave.setBirthDate(this.customer.getBirthDate());
        toBeSave.setYoungDriver(false);
        this.customerService.saveCustomer(toBeSave);
    }

    @Test(expected = Exception.class)
    public void customerService_editCustomerWithInvalidValueForBirthDateShouldThrowException(){
        CustomerServiceModel toBeSave = new CustomerServiceModel();
        toBeSave.setId(this.customer.getId());
        toBeSave.setName(this.customer.getName());
        toBeSave.setBirthDate(null);
        toBeSave.setYoungDriver(false);
        this.customerService.saveCustomer(toBeSave);
    }

    @Test
    public void customerService_deleteCustomerWithCorrectId(){
        this.customerService.deleteCustomer(this.customer.getId());
        Assert.assertEquals(0,this.customerRepository.count());
    }

    @Test(expected = Exception.class)
    public void customerService_deleteCustomerWithIncorrectIdShouldThrowException(){
        this.customerService.deleteCustomer("sdasdasas");
    }

    @Test
    public void customerService_findCustomerById(){
        CustomerServiceModel expectedCustomer = this.customerService.findCustomerById(this.customer.getId());
        Assert.assertEquals(this.customer.getId(),expectedCustomer.getId());
    }

    @Test(expected = Exception.class)
    public void customerService_findCustomerWithIncorrectIdShouldThrowException(){
        this.customerService.findCustomerById("ffafaafafafafa");
    }
}
