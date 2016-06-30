package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DiccionarioDao;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.primefaces.model.UploadedFile;


@Named("evento")
@ViewScoped
public class EventoBean implements Serializable {

	private static final long serialVersionUID = 4135525076311614388L;
	private final Logger log = LogManager.getLogger(EventoBean.class);
	@EJB
	private EventoDao eventoDao;
	@EJB
	private DiccionarioDao diccionarioDao;
	private Evento evento = new Evento();
	private List<GlobalCodigospostales> codigosPostales;
	private Patrocinadores patrocinador = new Patrocinadores();
	private List<Patrocinadores> patrocinadores = new ArrayList<>();
	private UploadedFile imageFile;
	private UploadedFile imagePatrocinador;
	
	public void init() {
		codigosPostales = new ArrayList<>();
		evento.setCodigoPostal(new GlobalCodigospostales());
	}
	
	public void actualizaLocalidadesByCP(String IdProvincia) {
		codigosPostales = diccionarioDao.actualizaLocalidadesByCP(IdProvincia);
	}
	
	public void crearPatrocinador() {
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (patrocinador.getNombre() != null && !"".equals(patrocinador.getNombre())) {
			
			if (!"".equalsIgnoreCase(imagePatrocinador.getFileName())) {
				log.info("El patrocinador no es nulo");
				try {
					byte[] bytes;
					InputStream is = imagePatrocinador.getInputstream();
					
					if (is != null) {
						bytes = IOUtils.toByteArray(is);
						is.close();
						patrocinador.setFotoperfil(bytes);
						patrocinador.setFotoNombre(imagePatrocinador.getFileName());
					} else {
						bytes = new byte[0];
					}
				}
				catch (IOException e) {
					log.error("algo ha ocurrido con la foto");
				}
			}
			
			patrocinadores.add(patrocinador);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Patrocinador", patrocinador.getNombre() + " creado.");
			facesContext.addMessage(null, message);
			patrocinador = new Patrocinadores();
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Patrocinador", "No se pudo crear.");
			facesContext.addMessage(null, message);
		}
		
	}
	
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<GlobalCodigospostales> getCodigosPostales() {
		return codigosPostales;
	}

	public void setCodigosPostales(List<GlobalCodigospostales> codigosPostales) {
		this.codigosPostales = codigosPostales;
	}
	
	public UploadedFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(UploadedFile imageFile) {
		this.imageFile = imageFile;
	}

	public Patrocinadores getPatrocinador() {
		return patrocinador;
	}

	public void setPatrocinador(Patrocinadores patrocinador) {
		this.patrocinador = patrocinador;
	}

	public UploadedFile getImagePatrocinador() {
		return imagePatrocinador;
	}

	public void setImagePatrocinador(UploadedFile imagePatrocinador) {
		this.imagePatrocinador = imagePatrocinador;
	}
	
	
		
}

