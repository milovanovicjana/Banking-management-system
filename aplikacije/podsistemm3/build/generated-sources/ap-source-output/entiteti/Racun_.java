package entiteti;

import entiteti.Komitent;
import entiteti.Prenos;
import entiteti.Transakcija;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-16T12:38:03")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile SingularAttribute<Racun, Komitent> idK;
    public static volatile ListAttribute<Racun, Prenos> prenosList1;
    public static volatile SingularAttribute<Racun, Integer> idM;
    public static volatile SingularAttribute<Racun, Integer> stanje;
    public static volatile SingularAttribute<Racun, Date> datumIVremeOtvaranja;
    public static volatile SingularAttribute<Racun, Integer> idR;
    public static volatile SingularAttribute<Racun, Integer> brTransakcija;
    public static volatile ListAttribute<Racun, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Racun, Integer> dozvoljeniMinus;
    public static volatile ListAttribute<Racun, Prenos> prenosList;
    public static volatile SingularAttribute<Racun, String> status;

}