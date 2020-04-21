package panda.repositoty;

import panda.domain.entities.User;

public interface UserRepository extends GenericRepository<User, String> {

    User findByName(String username);
}
