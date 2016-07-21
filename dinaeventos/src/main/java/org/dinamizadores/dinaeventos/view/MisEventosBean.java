package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.utiles.BBDDFaker;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.dinamizadores.dinaeventos.utiles.UtilDinaEventos;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class MisEventosBean implements Serializable {
	private final Logger log = LogManager.getLogger(MisEventosBean.class);
	private static final long serialVersionUID = -9008347531758410552L;
	@Inject
	private LoginBean loginBean;
	@EJB
	private EventoDao eventoDao;
	private List<Evento> eventos;
	private Evento evento;
	private UtilDinaEventos utilDinaEventos = new UtilDinaEventos();
	@EJB
	private BBDDFaker bbddFaker;
	
	@PostConstruct
	public void init() {
		/*
		 * Si viene de crear evento el usuario tiene en loginbean el usuario y
		 * el evento (y el evento tiene el organizador y el usuario)
		 */

		// Buscar los eventos en los que el usuario es organizador
		eventos = eventoDao.getEventosByOrganizadorIdThroughUsuarioId(loginBean.getUsuario().getIdUsuario());
		ExternalContext econtext = FacesContext.getCurrentInstance().getExternalContext();
		log.debug(econtext.getRequestContextPath());
		log.debug(econtext.getRequestContextPath() + Constantes.Rutas.Administracion.VISION_GLOBAL);
		
		for (Evento evento : eventos) {
			log.debug("idevento: " + evento.getIdevento());
		}

	}

	@Loggable
	public void onRowSelect(SelectEvent event) {
		try {
			loginBean.setEvento(evento);
			ExternalContext econtext = FacesContext.getCurrentInstance().getExternalContext();
			econtext.redirect(econtext.getRequestContextPath() + Constantes.Rutas.Administracion.VISION_GLOBAL_CON_FACES);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Loggable
	public String irAVisionGlobalDelEvento(Evento eventoUnitario) {
		loginBean.setEvento(eventoUnitario);
		return Constantes.Rutas.Administracion.VISION_GLOBAL;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public String obtenerFechaFormateada(Date fecha) {
		log.debug(utilDinaEventos.getMyFormattedDate(fecha));
		return utilDinaEventos.getMyFormattedDate(fecha);
	}

}
