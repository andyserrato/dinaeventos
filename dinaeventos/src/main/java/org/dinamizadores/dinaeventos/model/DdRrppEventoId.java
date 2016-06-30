package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DdRrppEventoId generated by hbm2java
 */
@Embeddable
public class DdRrppEventoId  implements java.io.Serializable {

	private static final long serialVersionUID = -6318874044785318090L;
	private int idevento;
     private int idrrpp;

    public DdRrppEventoId() {
    }

    public DdRrppEventoId(int idevento, int idrrpp) {
       this.idevento = idevento;
       this.idrrpp = idrrpp;
    }

    @Column(name="idevento", nullable=false)
    public int getIdevento() {
        return this.idevento;
    }
    
    public void setIdevento(int idevento) {
        this.idevento = idevento;
    }


    @Column(name="idrrpp", nullable=false)
    public int getIdrrpp() {
        return this.idrrpp;
    }
    
    public void setIdrrpp(int idrrpp) {
        this.idrrpp = idrrpp;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DdRrppEventoId) ) return false;
		 DdRrppEventoId castOther = ( DdRrppEventoId ) other; 
         
		 return (this.getIdevento()==castOther.getIdevento())
 && (this.getIdrrpp()==castOther.getIdrrpp());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdevento();
         result = 37 * result + this.getIdrrpp();
         return result;
   }   


}


