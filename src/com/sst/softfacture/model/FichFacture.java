/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sst.softfacture.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Neji Med Walid << medwalid.neji@bitaka.com.tn >>
 */
@Entity
@Table(name = "fich_facture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichFacture.findAll", query = "SELECT f FROM FichFacture f"),
    @NamedQuery(name = "FichFacture.findByNumFact", query = "SELECT f FROM FichFacture f WHERE f.numFact = :numFact"),
    @NamedQuery(name = "FichFacture.findByLibelleClient", query = "SELECT f FROM FichFacture f WHERE f.libelleClient = :libelleClient"),
    @NamedQuery(name = "FichFacture.findByDateFacture", query = "SELECT f FROM FichFacture f WHERE f.dateFacture = :dateFacture"),
    @NamedQuery(name = "FichFacture.findByDateFactureD", query = "SELECT f FROM FichFacture f WHERE f.dateFactureD = :dateFactureD"),
    @NamedQuery(name = "FichFacture.findByMntHt", query = "SELECT f FROM FichFacture f WHERE f.mntHt = :mntHt"),
    @NamedQuery(name = "FichFacture.findByMntTtc", query = "SELECT f FROM FichFacture f WHERE f.mntTtc = :mntTtc"),
    @NamedQuery(name = "FichFacture.findByTimbre", query = "SELECT f FROM FichFacture f WHERE f.timbre = :timbre"),
    @NamedQuery(name = "FichFacture.findByTotTva", query = "SELECT f FROM FichFacture f WHERE f.totTva = :totTva")})
public class FichFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "num_fact")
    private Integer numFact;
    @Column(name = "libelle_client")
    private String libelleClient;
    @Column(name = "date_facture")
    private String dateFacture;
    @Column(name = "date_facture_d")
    @Temporal(TemporalType.DATE)
    private Date dateFactureD;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mnt_ht")
    private float mntHt;
    @Column(name = "mnt_ttc")
    private float mntTtc;
    private float timbre;
    @Column(name = "tot_tva")
    private float totTva;
    @OneToMany(mappedBy = "fkFacture", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<FichDetailFacture> fichDetailFactureList;

    public FichFacture() {
    }

    public FichFacture(Integer numFact) {
        this.numFact = numFact;
    }

    public Integer getNumFact() {
        return numFact;
    }

    public void setNumFact(Integer numFact) {
        this.numFact = numFact;
    }

    public String getLibelleClient() {
        return libelleClient;
    }

    public void setLibelleClient(String libelleClient) {
        this.libelleClient = libelleClient;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Date getDateFactureD() {
        return dateFactureD;
    }

    public void setDateFactureD(Date dateFactureD) {
        this.dateFactureD = dateFactureD;
    }

    public float getMntHt() {
        return mntHt;
    }

    public void setMntHt(float mntHt) {
        this.mntHt = mntHt;
    }

    public float getMntTtc() {
        return mntTtc;
    }

    public void setMntTtc(float mntTtc) {
        this.mntTtc = mntTtc;
    }

    public float getTimbre() {
        return timbre;
    }

    public void setTimbre(float timbre) {
        this.timbre = timbre;
    }

    public float getTotTva() {
        return totTva;
    }

    public void setTotTva(float totTva) {
        this.totTva = totTva;
    }

    @XmlTransient
    public List<FichDetailFacture> getFichDetailFactureList() {
        return fichDetailFactureList;
    }

    public void setFichDetailFactureList(List<FichDetailFacture> fichDetailFactureList) {
        this.fichDetailFactureList = fichDetailFactureList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numFact != null ? numFact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichFacture)) {
            return false;
        }
        FichFacture other = (FichFacture) object;
        if ((this.numFact == null && other.numFact != null) || (this.numFact != null && !this.numFact.equals(other.numFact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sst.softfacture.model.FichFacture[ numFact=" + numFact + " ]";
    }
    
}
