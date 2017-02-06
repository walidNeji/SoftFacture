/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sst.softfacture.controller;

import com.sst.softfacture.controller.exceptions.NonexistentEntityException;
import com.sst.softfacture.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sst.softfacture.model.FichDetailFacture;
import com.sst.softfacture.model.FichFacture;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Neji Med Walid << medwalid.neji@bitaka.com.tn >>
 */
public class FichFactureJpaController implements Serializable {

    public FichFactureJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichFacture fichFacture) throws PreexistingEntityException, Exception {
        if (fichFacture.getFichDetailFactureList() == null) {
            fichFacture.setFichDetailFactureList(new ArrayList<FichDetailFacture>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            List<FichDetailFacture> attachedFichDetailFactureList = new ArrayList<>();
//            for (FichDetailFacture fichDetailFactureListFichDetailFactureToAttach : fichFacture.getFichDetailFactureList()) {
//                fichDetailFactureListFichDetailFactureToAttach = em.getReference(fichDetailFactureListFichDetailFactureToAttach.getClass(), fichDetailFactureListFichDetailFactureToAttach.getNumDetailFacture());
//                attachedFichDetailFactureList.add(fichDetailFactureListFichDetailFactureToAttach);
//            }
//            fichFacture.setFichDetailFactureList(attachedFichDetailFactureList);
            em.persist(fichFacture);
//            for (FichDetailFacture fichDetailFactureListFichDetailFacture : fichFacture.getFichDetailFactureList()) {
//                FichFacture oldFkFactureOfFichDetailFactureListFichDetailFacture = fichDetailFactureListFichDetailFacture.getFkFacture();
//                fichDetailFactureListFichDetailFacture.setFkFacture(fichFacture);
//                fichDetailFactureListFichDetailFacture = em.merge(fichDetailFactureListFichDetailFacture);
//                if (oldFkFactureOfFichDetailFactureListFichDetailFacture != null) {
//                    oldFkFactureOfFichDetailFactureListFichDetailFacture.getFichDetailFactureList().remove(fichDetailFactureListFichDetailFacture);
//                    oldFkFactureOfFichDetailFactureListFichDetailFacture = em.merge(oldFkFactureOfFichDetailFactureListFichDetailFacture);
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFichFacture(fichFacture.getNumFact()) != null) {
                throw new PreexistingEntityException("FichFacture " + fichFacture + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichFacture fichFacture) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichFacture persistentFichFacture = em.find(FichFacture.class, fichFacture.getNumFact());
            List<FichDetailFacture> fichDetailFactureListOld = persistentFichFacture.getFichDetailFactureList();
            List<FichDetailFacture> fichDetailFactureListNew = fichFacture.getFichDetailFactureList();
            List<FichDetailFacture> attachedFichDetailFactureListNew = new ArrayList<FichDetailFacture>();
            for (FichDetailFacture fichDetailFactureListNewFichDetailFactureToAttach : fichDetailFactureListNew) {
                fichDetailFactureListNewFichDetailFactureToAttach = em.getReference(fichDetailFactureListNewFichDetailFactureToAttach.getClass(), fichDetailFactureListNewFichDetailFactureToAttach.getNumDetailFacture());
                attachedFichDetailFactureListNew.add(fichDetailFactureListNewFichDetailFactureToAttach);
            }
            fichDetailFactureListNew = attachedFichDetailFactureListNew;
            fichFacture.setFichDetailFactureList(fichDetailFactureListNew);
            fichFacture = em.merge(fichFacture);
            for (FichDetailFacture fichDetailFactureListOldFichDetailFacture : fichDetailFactureListOld) {
                if (!fichDetailFactureListNew.contains(fichDetailFactureListOldFichDetailFacture)) {
                    fichDetailFactureListOldFichDetailFacture.setFkFacture(null);
                    fichDetailFactureListOldFichDetailFacture = em.merge(fichDetailFactureListOldFichDetailFacture);
                }
            }
            for (FichDetailFacture fichDetailFactureListNewFichDetailFacture : fichDetailFactureListNew) {
                if (!fichDetailFactureListOld.contains(fichDetailFactureListNewFichDetailFacture)) {
                    FichFacture oldFkFactureOfFichDetailFactureListNewFichDetailFacture = fichDetailFactureListNewFichDetailFacture.getFkFacture();
                    fichDetailFactureListNewFichDetailFacture.setFkFacture(fichFacture);
                    fichDetailFactureListNewFichDetailFacture = em.merge(fichDetailFactureListNewFichDetailFacture);
                    if (oldFkFactureOfFichDetailFactureListNewFichDetailFacture != null && !oldFkFactureOfFichDetailFactureListNewFichDetailFacture.equals(fichFacture)) {
                        oldFkFactureOfFichDetailFactureListNewFichDetailFacture.getFichDetailFactureList().remove(fichDetailFactureListNewFichDetailFacture);
                        oldFkFactureOfFichDetailFactureListNewFichDetailFacture = em.merge(oldFkFactureOfFichDetailFactureListNewFichDetailFacture);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichFacture.getNumFact();
                if (findFichFacture(id) == null) {
                    throw new NonexistentEntityException("The fichFacture with id " + id + " no longer exists.");
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
            FichFacture fichFacture;
            try {
                fichFacture = em.getReference(FichFacture.class, id);
                fichFacture.getNumFact();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichFacture with id " + id + " no longer exists.", enfe);
            }
            List<FichDetailFacture> fichDetailFactureList = fichFacture.getFichDetailFactureList();
            for (FichDetailFacture fichDetailFactureListFichDetailFacture : fichDetailFactureList) {
                fichDetailFactureListFichDetailFacture.setFkFacture(null);
                fichDetailFactureListFichDetailFacture = em.merge(fichDetailFactureListFichDetailFacture);
            }
            em.remove(fichFacture);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichFacture> findFichFactureEntities() {
        return findFichFactureEntities(true, -1, -1);
    }

    public List<FichFacture> findFichFactureEntities(int maxResults, int firstResult) {
        return findFichFactureEntities(false, maxResults, firstResult);
    }

    private List<FichFacture> findFichFactureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichFacture.class));
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

    public FichFacture findFichFacture(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichFacture.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichFactureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichFacture> rt = cq.from(FichFacture.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public FichFacture lastNumFacture() {

        List<FichFacture> factures = getEntityManager().createQuery("select a from FichFacture as a order by a.numFact desc").getResultList();
        if (factures.isEmpty()) {
            return null;
        } else {
            return factures.get(0);
        }

    }
    
    public List<FichFacture> findbyDate(Date d){
        return getEntityManager().createNamedQuery("FichFacture.findByDateFactureD", FichFacture.class).setParameter("dateFactureD", d).getResultList();
    }
    
     public List<FichFacture> findbyNumFacture(int num){
        return getEntityManager().createNamedQuery("FichFacture.findByNumFact", FichFacture.class).setParameter("numFact", num).getResultList();
    }
}
