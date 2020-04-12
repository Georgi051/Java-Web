package cofing;

import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Produces;

public class ModelMapperConfig {

    @Produces
    public ModelMapper modelMapper(){
      return  new ModelMapper();
    }
}

