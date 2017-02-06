/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sst.softfacture.controller;

import com.sst.softfacture.controller.exceptions.NonexistentEntityException;
import com.sst.softfacture.model.FichDetailFacture;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sst.softfacture.model.FichFacture;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Neji Med Walid << medwalid.neji@bitaka.com.tn >>
 */
public class FichDetailFactureJpaController implements Serializable {

    public FichDetailFactureJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichDetailFacture fichDetailFacture) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichFacture fkFacture = fichDetailFacture.getFkFacture();
            if (fkFacture != null) {
                fkFacture = em.getReference(fkFacture.getClass(), fkFacture.getNumFact());
                fichDetailFacture.setFkFacture(fkFacture);
            }
            em.persist(fichDetailFacture);
            if (fkFacture != null) {
                fkFacture.getFichDetailFactureList().add(fichDetailFacture);
                fkFacture = em.merge(fkFacture);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichDetailFacture fichDetailFacture) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichDetailFacture persistentFichDetailFacture = em.find(FichDetailFacture.class, fichDetailFacture.getNumDetailFacture());
            FichFacture fkFactureOld = persistentFichDetailFacture.getFkFacture();
            FichFacture fkFactureNew = fichDetailFacture.getFkFacture();
            if (fkFactureNew != null) {
                fkFactureNew = em.getReference(fkFactureNew.getClass(), fkFactureNew.getNumFact());
                fichDetailFacture.setFkFacture(fkFactureNew);
            }
            fichDetailFacture = em.merge(fichDetailFacture);
            if (fkFactureOld != null && !fkFactureOld.equals(fkFactureNew)) {
                fkFactureOld.getFichDetailFactureList().remove(fichDetailFacture);
                fkFactureOld = em.merge(fkFactureOld);
            }
            if (fkFactureNew != null && !fkFactureNew.equals(fkFactureOld)) {
                fkFactureNew.getFichDetailFactureList().add(fichDetailFacture);
                fkFactureNew = em.merge(fkFactureNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichDetailFacture.getNumDetailFacture();
                if (findFichDetailFacture(id) == null) {
                    throw new NonexistentEntityException("The fichDetailFacture with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichDetailFacture fichDetailFacture;
            try {
                fichDetailFacture = em.getReference(FichDetailFacture.class, id);
                fichDetailFacture.getNumDetailFacture();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichDetailFacture with id " + id + " no longer exists.", enfe);
            }
            FichFacture fkFacture = fichDetailFacture.getFkFacture();
            if (fkFacture != null) {
                fkFacture.getFichDetailFactureList().remove(fichDetailFacture);
                fkFacture = em.merge(fkFacture);
            }
            em.remove(fichDetailFacture);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichDetailFacture> findFichDetailFactureEntities() {
        return findFichDetailFactureEntities(true, -1, -1);
    }

    public List<FichDetailFacture> findFichDetailFactureEntities(int maxResults, int firstResult) {
        return findFichDetailFactureEntities(false, maxResults, firstResult);
    }

    private List<FichDetailFacture> findFichDetailFactureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichDetailFacture.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FichDetailFacture findFichDetailFacture(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichDetailFacture.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichDetailFactureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichDetailFacture> rt = cq.from(FichDetailFacture.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
