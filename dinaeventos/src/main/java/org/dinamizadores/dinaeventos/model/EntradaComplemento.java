package org.dinamizadores.dinaeventos.model;
// Generated 12-jul-2016 13:51:50 by Hibernate Tools 4.3.1.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EntradaComplemento generated by hbm2java
 */
@Entity
@Table(name="entrada_complemento"
    ,catalog="jbossforge"
)
public class EntradaComplemento  implements java.io.Serializable {

	private static final long serialVersionUID = -1331211661403260294L;
	private Integer idEntradaComplemento;
    private DdTipoComplemento ddTipoComplemento;
    private Entrada entrada;

    public EntradaComplemento() {
    }

    public EntradaComplemento(DdTipoComplemento ddTipoComplemento, Entrada entrada) {
       this.ddTipoComplemento = ddTipoComplemento;
       this.entrada = entrada;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="identradacomplemento", unique=true, nullable=false)
    public Integer getIdEntradaComplemento() {
        return this.idEntradaComplemento;
    }
    
    public void setIdEntradaComplemento(Integer idEntradaComplemento) {
        this.idEntradaComplemento = idEntradaComplemento;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idtipocomplemento", nullable=false)
    public DdTipoComplemento getDdTipoComplemento() {
        return this.ddTipoComplemento;
    }
    
    public void setDdTipoComplemento(DdTipoComplemento ddTipoComplemento) {
        this.ddTipoComplemento = ddTipoComplemento;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="identrada", nullable=false)
    public Entrada getEntrada() {
        return this.entrada;
    }
    
    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }
}


