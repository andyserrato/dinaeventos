package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.utiles.BBDDFaker;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.dinamizadores.dinaeventos.utiles.UtilDinaEventos;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

@Named("eventoIndividualBean")
@ViewScoped
@Loggable
public class MostrarEventoIndividualBean implements Serializable {

	private static final long serialVersionUID = 3753234087010910190L;

	private final Logger log = LogManager.getLogger(EventoBean.class);

	private String nombreEvento;

	@EJB
	private EventoDao eventoDao;

	private Evento evento;

	private UtilDinaEventos utilDinaEventos = new UtilDinaEventos();

	@EJB
	private BBDDFaker bbddfaker;

	public String cargarEventoPorNombre() {
		String ruta = null;

		// try {
		// bbddfaker.llenarBBDD();
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		if (nombreEvento != null && !"".equals(nombreEvento)) {
			try {
				log.debug("Buscamos el evento");
				evento = eventoDao.getEventoByName(nombreEvento);
			} catch (Exception e) {
				// TODO [ANDY] Arreglar
				log.debug("Un Error: " + e.getMessage());
			}
		}

		if (evento == null) {
			log.debug("Ruta nula");
			ruta = Constantes.Rutas.PAGINA_404;
		}

		return ruta;
	}

	public String getMyFormattedDate(Date fecha) {
		String formattedDate = "";

		if (fecha != null) {
			formattedDate = utilDinaEventos.getMyFormattedDate(fecha);
		}

		return formattedDate;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}
