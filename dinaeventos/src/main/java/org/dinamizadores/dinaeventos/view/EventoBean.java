package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DiccionarioDao;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.model.DdTipoEvento;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.Organizadores;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
	@Inject
	private LoginBean loginBean;
	private Evento evento = new Evento();
	private List<GlobalCodigospostales> codigosPostales;
	private Patrocinadores patrocinador = new Patrocinadores();
	private UploadedFile imageFile;
	private UploadedFile imagePatrocinador;
	private List<DdTipoEvento> ddTipoEvento = new ArrayList<>(); 
	
	@PostConstruct
	public void init() {
		codigosPostales = new ArrayList<>();
		evento.setCodigoPostal(new GlobalCodigospostales());
		evento.setOrganizador(new Organizadores());
		ddTipoEvento = diccionarioDao.getDdTiposDeEvento();

	}
	
	@Loggable
	public void actualizaLocalidadesByCP(String IdProvincia) {
		codigosPostales = diccionarioDao.actualizaLocalidadesByCP(IdProvincia);
	}
	
	@Loggable
	public void crearPatrocinador() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean patrocinadorCreado = false;
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (patrocinador.getNombre() != null && !"".equals(patrocinador.getNombre())) {
			log.debug("Nombre del patrocinador: " + patrocinador.getNombre());
			log.debug("Nombre de la foto" + patrocinador.getFotoNombre());
			evento.getPatrocinadores().add(patrocinador);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Patrocinador", patrocinador.getNombre() + " creado.");
			patrocinador = new Patrocinadores();
			patrocinadorCreado = true;
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Error");
		}
		
		facesContext.addMessage(null, message);
		context.addCallbackParam("patrocinadorCreado", patrocinadorCreado);
	}
	
	@Loggable
	public void handleFileUploadPatrocinador(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		if (file != null && !"".equalsIgnoreCase(file.getFileName())) {
			log.info("La imagen del patrocinador no es nula");
			try {
				byte[] bytes;
				InputStream is = file.getInputstream();

				if (is != null) {
					bytes = IOUtils.toByteArray(is);
					is.close();
					patrocinador.setFotoperfil(bytes);
					patrocinador.setFotoNombre(file.getFileName());
				} else {
					bytes = new byte[0];
				}
			} catch (IOException e) {
				log.error("algo ha ocurrido con la foto");
			}
		}
	}
	
	@Loggable
	public void cleanPatrocinador() {
		patrocinador = new Patrocinadores();
	}
	
	@Loggable
	public void handleFileUploadLogoEvento(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (file != null && !"".equalsIgnoreCase(file.getFileName())) {
			log.info("La imagen del evento no es nula");
			try {
				byte[] bytes;
				InputStream is = file.getInputstream();

				if (is != null) {
					bytes = IOUtils.toByteArray(is);
					is.close();
					evento.setLogo(bytes);
					evento.setLogoNombre(file.getFileName());
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logo", "agregado");
					facesContext.addMessage(null, message);
				} else {
					bytes = new byte[0];
				}
			} catch (IOException e) {
				log.error("algo ha ocurrido con la foto del evento");
			}
		}
	}
	
	public StreamedContent obtenerImagen(String nombre, byte[] datos) {
		StreamedContent file = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
		if (nombre == null || "".equals(nombre) || datos == null ) {
			file = new DefaultStreamedContent(ec.getResourceAsStream("/resources/images/placeholder.png"), "image/png");
		} else {
			file = new DefaultStreamedContent();
		}
		
		return file;
	}
	
	public void crearEvento() {
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		eventoDao.crearEvento(evento);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento", evento.getIdevento() + " creado.");
		facesContext.addMessage(null, message);
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

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public List<DdTipoEvento> getDdTipoEvento() {
		return ddTipoEvento;
	}

	public void setDdTipoEvento(List<DdTipoEvento> ddTipoEvento) {
		this.ddTipoEvento = ddTipoEvento;
	}

	public List<GlobalCodigospostales> getCodigosPostales() {
		return codigosPostales;
	}

	public void setCodigosPostales(List<GlobalCodigospostales> codigosPostales) {
		this.codigosPostales = codigosPostales;
	}
	
}

