package panda.repositoty.imp;

import panda.domain.entities.Receipt;
import panda.repositoty.ReceiptsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class ReceiptsRepositoryImpl implements ReceiptsRepository {
    private final EntityManager entityManager;


    @Inject
    public ReceiptsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Receipt save(Receipt entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public List<Receipt> findAll() {
        entityManager.getTransaction().begin();
        List<Receipt> receipts = this.entityManager
                .createQuery("select r from Receipt r",Receipt.class)
                .getResultList();
        entityManager.getTransaction().commit();
        return receipts;
    }

    @Override
    public Receipt findById(String id) {
        entityManager.getTransaction().begin();
        Receipt receipt = this.entityManager
                .createQuery("select r from Receipt r where r.id = :id",Receipt.class)
                .setParameter("id",id)
                .getSingleResult();
        entityManager.getTransaction().commit();
        return receipt;
    }

    @Override
    public List<Receipt> findAllByUsername(String username) {
        entityManager.getTransaction().begin();
        List<Receipt> receipts = this.entityManager
                .createQuery("select r from Receipt r where r.recipient.username = :username",Receipt.class)
                .setParameter("username",username)
                .getResultList();
        entityManager.getTransaction().commit();
        return receipts;
    }

    @Override
    public Long size() {
        entityManager.getTransaction().begin();
        Long size = this.entityManager.createQuery("select count(r) from Receipt as r",Long.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        return size;
    }
}
