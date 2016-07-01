package org.dinamizadores.dinaeventos.dto;

import java.io.Serializable;
import java.util.List;

import org.dinamizadores.dinaeventos.model.DdRrppJefeEntrada;
import org.dinamizadores.dinaeventos.model.RrppJefes;
import org.dinamizadores.dinaeventos.model.RrppMinion;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

@Loggable
public class RrppJefeDTO implements Serializable {

	private static final long serialVersionUID = 2475806733497550973L;
	private RrppJefes rrppJefe;
//	private List<DdRrppEventoBean> eventosDelRrppJefe;
	private List<DdRrppJefeEntrada> entradasAsignadas;
	private List<RrppMinion> minionsDelJefe;

	public RrppJefeDTO() {
		super();
	}

	public RrppJefeDTO(RrppJefes rrppJefe,
			List<DdRrppJefeEntrada> entradasAsignadas, List<RrppMinion> minionsDelJefe) {
		super();
		this.rrppJefe = rrppJefe;
//		this.eventosDelRrppJefe = eventosDelRrppJefe;
		this.entradasAsignadas = entradasAsignadas;
		this.minionsDelJefe = minionsDelJefe;
	}
	
	public RrppJefes getRrppJefe() {
		return rrppJefe;
	}

	public void setRrppJefe(RrppJefes rrppJefe) {
		this.rrppJefe = rrppJefe;
	}

//	public List<DdRrppEventoBean> getEventosDelRrppJefe() {
//		return eventosDelRrppJefe;
//	}
//
//	public void setEventosDelRrppJefe(List<DdRrppEventoBean> eventosDelRrppJefe) {
//		this.eventosDelRrppJefe = eventosDelRrppJefe;
//	}

	public List<DdRrppJefeEntrada> getEntradasAsignadas() {
		return entradasAsignadas;
	}

	public void setEntradasAsignadas(List<DdRrppJefeEntrada> entradasAsignadas) {
		this.entradasAsignadas = entradasAsignadas;
	}

	public List<RrppMinion> getMinionsDelJefe() {
		return minionsDelJefe;
	}

	public void setMinionsDelJefe(List<RrppMinion> minionsDelJefe) {
		this.minionsDelJefe = minionsDelJefe;
	}
}
