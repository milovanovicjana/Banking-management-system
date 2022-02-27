/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author xxx
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIdR", query = "SELECT r FROM Racun r WHERE r.idR = :idR"),
    @NamedQuery(name = "Racun.findByIdM", query = "SELECT r FROM Racun r WHERE r.idM = :idM"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByDozvoljeniMinus", query = "SELECT r FROM Racun r WHERE r.dozvoljeniMinus = :dozvoljeniMinus"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByDatumIVremeOtvaranja", query = "SELECT r FROM Racun r WHERE r.datumIVremeOtvaranja = :datumIVremeOtvaranja"),
    @NamedQuery(name = "Racun.findByBrTransakcija", query = "SELECT r FROM Racun r WHERE r.brTransakcija = :brTransakcija")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdR")
    private Integer idR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdM")
    private int idM;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Stanje")
    private int stanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DozvoljeniMinus")
    private int dozvoljeniMinus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumIVremeOtvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIVremeOtvaranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrTransakcija")
    private int brTransakcija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRac")
    private List<Transakcija> transakcijaList;
    @JoinColumn(name = "IdK", referencedColumnName = "IdK")
    @ManyToOne(optional = false)
    private Komitent idK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRacKa")
    private List<Prenos> prenosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRacSa")
    private List<Prenos> prenosList1;

    public Racun() {
    }

    public Racun(Integer idR) {
        this.idR = idR;
    }

    public Racun(Integer idR, int idM, int stanje, int dozvoljeniMinus, String status, Date datumIVremeOtvaranja, int brTransakcija) {
        this.idR = idR;
        this.idM = idM;
        this.stanje = stanje;
        this.dozvoljeniMinus = dozvoljeniMinus;
        this.status = status;
        this.datumIVremeOtvaranja = datumIVremeOtvaranja;
        this.brTransakcija = brTransakcija;
    }

    public Integer getIdR() {
        return idR;
    }

    public void setIdR(Integer idR) {
        this.idR = idR;
    }

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }

    public int getStanje() {
        return stanje;
    }

    public void setStanje(int stanje) {
        this.stanje = stanje;
    }

    public int getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(int dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatumIVremeOtvaranja() {
        return datumIVremeOtvaranja;
    }

    public void setDatumIVremeOtvaranja(Date datumIVremeOtvaranja) {
        this.datumIVremeOtvaranja = datumIVremeOtvaranja;
    }

    public int getBrTransakcija() {
        return brTransakcija;
    }

    public void setBrTransakcija(int brTransakcija) {
        this.brTransakcija = brTransakcija;
    }

    @XmlTransient
    public List<Transakcija> getTransakcijaList() {
        return transakcijaList;
    }

    public void setTransakcijaList(List<Transakcija> transakcijaList) {
        this.transakcijaList = transakcijaList;
    }

    public Komitent getIdK() {
        return idK;
    }

    public void setIdK(Komitent idK) {
        this.idK = idK;
    }

    @XmlTransient
    public List<Prenos> getPrenosList() {
        return prenosList;
    }

    public void setPrenosList(List<Prenos> prenosList) {
        this.prenosList = prenosList;
    }

    @XmlTransient
    public List<Prenos> getPrenosList1() {
        return prenosList1;
    }

    public void setPrenosList1(List<Prenos> prenosList1) {
        this.prenosList1 = prenosList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idR != null ? idR.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.idR == null && other.idR != null) || (this.idR != null && !this.idR.equals(other.idR))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Racun[ idR=" + idR + " ]";
    }
    
}
