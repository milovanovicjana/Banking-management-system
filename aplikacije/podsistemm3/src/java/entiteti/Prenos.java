/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xxx
 */
@Entity
@Table(name = "prenos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prenos.findAll", query = "SELECT p FROM Prenos p"),
    @NamedQuery(name = "Prenos.findByIdT", query = "SELECT p FROM Prenos p WHERE p.idT = :idT")})
public class Prenos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdT")
    private Integer idT;
    @JoinColumn(name = "IdRacKa", referencedColumnName = "IdR")
    @ManyToOne(optional = false)
    private Racun idRacKa;
    @JoinColumn(name = "IdRacSa", referencedColumnName = "IdR")
    @ManyToOne(optional = false)
    private Racun idRacSa;
    @JoinColumn(name = "IdT", referencedColumnName = "IdT", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Transakcija transakcija;

    public Prenos() {
    }

    public Prenos(Integer idT) {
        this.idT = idT;
    }

    public Integer getIdT() {
        return idT;
    }

    public void setIdT(Integer idT) {
        this.idT = idT;
    }

    public Racun getIdRacKa() {
        return idRacKa;
    }

    public void setIdRacKa(Racun idRacKa) {
        this.idRacKa = idRacKa;
    }

    public Racun getIdRacSa() {
        return idRacSa;
    }

    public void setIdRacSa(Racun idRacSa) {
        this.idRacSa = idRacSa;
    }

    public Transakcija getTransakcija() {
        return transakcija;
    }

    public void setTransakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idT != null ? idT.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prenos)) {
            return false;
        }
        Prenos other = (Prenos) object;
        if ((this.idT == null && other.idT != null) || (this.idT != null && !this.idT.equals(other.idT))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Prenos[ idT=" + idT + " ]";
    }
    
}
