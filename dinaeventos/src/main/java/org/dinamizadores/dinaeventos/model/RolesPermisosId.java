package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RolesPermisosId generated by hbm2java
 */
@Embeddable
public class RolesPermisosId  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = -8236332414697090552L;
	private int idrol;
     private int idpermisos;

    public RolesPermisosId() {
    }

    public RolesPermisosId(int idrol, int idpermisos) {
       this.idrol = idrol;
       this.idpermisos = idpermisos;
    }
   


    @Column(name="idrol", nullable=false)
    public int getIdrol() {
        return this.idrol;
    }
    
    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }


    @Column(name="idpermisos", nullable=false)
    public int getIdpermisos() {
        return this.idpermisos;
    }
    
    public void setIdpermisos(int idpermisos) {
        this.idpermisos = idpermisos;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RolesPermisosId) ) return false;
		 RolesPermisosId castOther = ( RolesPermisosId ) other; 
         
		 return (this.getIdrol()==castOther.getIdrol())
 && (this.getIdpermisos()==castOther.getIdpermisos());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdrol();
         result = 37 * result + this.getIdpermisos();
         return result;
   }   


}

