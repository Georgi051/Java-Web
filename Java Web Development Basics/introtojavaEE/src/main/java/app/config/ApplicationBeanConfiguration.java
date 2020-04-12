package app.config;

import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Produces;

public class ApplicationBeanConfiguration {

    @Produces
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
