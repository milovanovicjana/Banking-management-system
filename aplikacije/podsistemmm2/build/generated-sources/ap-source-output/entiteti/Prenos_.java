package entiteti;

import entiteti.Racun;
import entiteti.Transakcija;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-15T16:37:43")
@StaticMetamodel(Prenos.class)
public class Prenos_ { 

    public static volatile SingularAttribute<Prenos, Racun> idRacSa;
    public static volatile SingularAttribute<Prenos, Transakcija> transakcija;
    public static volatile SingularAttribute<Prenos, Integer> idT;
    public static volatile SingularAttribute<Prenos, Racun> idRacKa;

}