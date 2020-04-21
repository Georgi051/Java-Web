package panda.repositoty.imp;

import panda.domain.entities.Package;
import panda.domain.entities.Status;
import panda.repositoty.PackageRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class PackageRepositoryImpl implements PackageRepository {
    private final EntityManager entityManager;

    @Inject
    public PackageRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Package save(Package entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }


    @Override
    public List<Package> findAll() {
        entityManager.getTransaction().begin();
        List<Package> packages = this.entityManager
                .createQuery("select p from Package p",Package.class)
                .getResultList();
        entityManager.getTransaction().commit();
        return packages;
    }

    @Override
    public Package findById(String id) {
        entityManager.getTransaction().begin();
        Package aPackage = this.entityManager
                .createQuery("select p from Package p where p.id = :id",Package.class)
                .setParameter("id",id)
                .getSingleResult();
        entityManager.getTransaction().commit();
        return aPackage;
    }

    @Override
    public Long size() {
        entityManager.getTransaction().begin();
        Long size = entityManager.createQuery("SELECT count(p) from Package p",Long.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        return size;
    }

    @Override
    public List<Package> findAllPackagesByStatus(Status status) {
        entityManager.getTransaction().begin();
        List<Package> packages = this.entityManager
                .createQuery("select p from Package p where p.status = :status",Package.class)
                .setParameter("status",status)
                .getResultList();
        entityManager.getTransaction().commit();
        return packages;
    }

    @Override
    public Package updatePackage(Package aPackage) {
        entityManager.getTransaction().begin();
        entityManager.merge(aPackage);
        entityManager.getTransaction().commit();
        return aPackage;
    }

    @Override
    public List<Package> findAllPackagesByUser(String username) {
        entityManager.getTransaction().begin();
        List<Package> packages = this.entityManager
                .createQuery("select p from Package p where p.recipient.username = :username",Package.class)
                .setParameter("username",username)
                .getResultList();
        entityManager.getTransaction().commit();
        return packages;
    }
}
