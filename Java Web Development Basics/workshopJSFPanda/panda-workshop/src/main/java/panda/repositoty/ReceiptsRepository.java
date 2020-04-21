package panda.repositoty;

import panda.domain.entities.Receipt;

import java.util.List;

public interface ReceiptsRepository extends GenericRepository<Receipt,String>{

    Receipt findById(String id);

    List<Receipt>  findAllByUsername(String username);
}
