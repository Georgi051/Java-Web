package panda.repositoty.imp;

import panda.domain.entities.User;
import org.modelmapper.ModelMapper;
import panda.repositoty.UserRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;


    @Inject
    public UserRepositoryImpl(EntityManager entityManager, ModelMapper modelMapper) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public User save(User entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public List<User> findAll() {
        entityManager.getTransaction().begin();
        List<User> users = this.entityManager
                .createQuery("select u from User u",User.class)
                .getResultList();
        entityManager.getTransaction().commit();
        return users;
    }

    @Override
    public User findById(String id) {
        entityManager.getTransaction().begin();
        User user = this.entityManager
                .createQuery("select u from User u where u.id = :id",User.class)
                .setParameter("id",id)
                .getSingleResult();
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public Long size() {
        entityManager.getTransaction().begin();
        Long size = entityManager.createQuery("SELECT count(u) from User u",Long.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        return size;
    }

    @Override
    public User findByName(String username) {
        entityManager.getTransaction().begin();
        try {
            User user = this.entityManager
                    .createQuery("select u from User u where u.username = :username",User.class)
                    .setParameter("username",username)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return user;
        }catch (Exception e){
            entityManager.getTransaction().commit();
            return null;
        }
    }
}
