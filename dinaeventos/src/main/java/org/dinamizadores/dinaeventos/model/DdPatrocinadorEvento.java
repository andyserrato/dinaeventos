package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DdPatrocinadorEvento generated by hbm2java
 */
@Entity
@Table(name = "dd_patrocinador_evento", catalog = "jbossforge")
public class DdPatrocinadorEvento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1331389470049053921L;
	private DdPatrocinadorEventoId id;

	public DdPatrocinadorEvento() {
	}

	public DdPatrocinadorEvento(DdPatrocinadorEventoId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idpatrocinador", column = @Column(name = "idpatrocinador", nullable = false)),
			@AttributeOverride(name = "idevento", column = @Column(name = "idevento", nullable = false))})
	public DdPatrocinadorEventoId getId() {
		return this.id;
	}

	public void setId(DdPatrocinadorEventoId id) {
		this.id = id;
	}

}
