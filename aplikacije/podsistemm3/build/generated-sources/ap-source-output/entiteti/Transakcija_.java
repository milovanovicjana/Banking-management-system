package entiteti;

import entiteti.Isplata;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Uplata;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-16T12:38:03")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Isplata> isplata;
    public static volatile SingularAttribute<Transakcija, Integer> iznos;
    public static volatile SingularAttribute<Transakcija, String> svrha;
    public static volatile SingularAttribute<Transakcija, Integer> idT;
    public static volatile SingularAttribute<Transakcija, Date> datumIVreme;
    public static volatile SingularAttribute<Transakcija, Integer> redniBr;
    public static volatile SingularAttribute<Transakcija, Prenos> prenos;
    public static volatile SingularAttribute<Transakcija, Uplata> uplata;
    public static volatile SingularAttribute<Transakcija, Racun> idRac;

}