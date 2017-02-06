/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sst.softfacture.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Neji Med Walid << medwalid.neji@bitaka.com.tn >>
 */
@Entity
@Table(name = "fich_detail_facture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichDetailFacture.findAll", query = "SELECT f FROM FichDetailFacture f"),
    @NamedQuery(name = "FichDetailFacture.findByNumDetailFacture", query = "SELECT f FROM FichDetailFacture f WHERE f.numDetailFacture = :numDetailFacture"),
    @NamedQuery(name = "FichDetailFacture.findByLibelleProduit", query = "SELECT f FROM FichDetailFacture f WHERE f.libelleProduit = :libelleProduit"),
    @NamedQuery(name = "FichDetailFacture.findByQte", query = "SELECT f FROM FichDetailFacture f WHERE f.qte = :qte"),
    @NamedQuery(name = "FichDetailFacture.findByPuHt", query = "SELECT f FROM FichDetailFacture f WHERE f.puHt = :puHt"),
    @NamedQuery(name = "FichDetailFacture.findByTva", query = "SELECT f FROM FichDetailFacture f WHERE f.tva = :tva")})
public class FichDetailFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_detail_facture")
    private Integer numDetailFacture;
    @Column(name = "libelle_produit")
    private String libelleProduit;
    private float qte;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pu_ht")
    private float puHt;
    private Integer tva;
    @JoinColumn(name = "fk_facture", referencedColumnName = "num_fact")
    @ManyToOne(fetch = FetchType.LAZY)
    private FichFacture fkFacture;

    public FichDetailFacture() {
    }

    public FichDetailFacture(Integer numDetailFacture) {
        this.numDetailFacture = numDetailFacture;
    }

    public Integer getNumDetailFacture() {
        return numDetailFacture;
    }

    public void setNumDetailFacture(Integer numDetailFacture) {
        this.numDetailFacture = numDetailFacture;
    }

    public String getLibelleProduit() {
        return libelleProduit;
    }

    public void setLibelleProduit(String libelleProduit) {
        this.libelleProduit = libelleProduit;
    }

    public float getQte() {
        return qte;
    }

    public void setQte(float qte) {
        this.qte = qte;
    }

    public float getPuHt() {
        return puHt;
    }

    public void setPuHt(float puHt) {
        this.puHt = puHt;
    }

    public Integer getTva() {
        return tva;
    }

    public void setTva(Integer tva) {
        this.tva = tva;
    }

    public FichFacture getFkFacture() {
        return fkFacture;
    }

    public void setFkFacture(FichFacture fkFacture) {
        this.fkFacture = fkFacture;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numDetailFacture != null ? numDetailFacture.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichDetailFacture)) {
            return false;
        }
        FichDetailFacture other = (FichDetailFacture) object;
        if ((this.numDetailFacture == null && other.numDetailFacture != null) || (this.numDetailFacture != null && !this.numDetailFacture.equals(other.numDetailFacture))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sst.softfacture.model.FichDetailFacture[ numDetailFacture=" + numDetailFacture + " ]";
    }
    
}
