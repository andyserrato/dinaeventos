package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DiccionarioDao;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dao.OrganizadoresDao;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.DdTipoEvento;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.Organizadores;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
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
	@EJB 
	private OrganizadoresDao organizadorDao;
	@Inject
	private LoginBean loginBean;
	private Evento evento = new Evento();
	private List<GlobalCodigospostales> codigosPostales;
	private Patrocinadores patrocinador = new Patrocinadores();
	private DdTipoEntrada tipoEntrada = new DdTipoEntrada();
	private UploadedFile imageFile;
	private UploadedFile imagePatrocinador;
	private List<DdTipoEvento> ddTipoEvento = new ArrayList<>(); 
	private List<DdTipoComplemento> complementoList = new ArrayList<>();
	private DdTipoComplemento complemento = new DdTipoComplemento();
	
	@PostConstruct
	public void init() {
		codigosPostales = new ArrayList<>();
		evento.setCodigoPostal(new GlobalCodigospostales());
		inicializarOrganizador();
		ddTipoEvento = diccionarioDao.getDdTiposDeEvento();
	}
	
	public void inicializarOrganizador() {
		Organizadores organizador = null;
		
		try {
			organizador = organizadorDao.getOrganizadorByIdUsuario(loginBean.getUsuario().getIdUsuario());
		} catch (Exception e) {
			log.debug("Error obteniendo el organizador", e);
		}
		
		if (organizador != null) {
			evento.setOrganizador(organizador);
			evento.setIdorganizador(organizador.getIdorganizador());
		} else {
			organizador = new Organizadores();
			organizador.setIdUsuario(loginBean.getUsuario().getIdUsuario());
			organizadorDao.create(organizador);
			log.debug("Organizador creado: " + organizador.getIdorganizador() + " asociado al usuario: " + loginBean.getUsuario().getIdUsuario() + " con email: " + loginBean.getUsuario().getIdUsuario());
			evento.setOrganizador(organizador);
			evento.setIdorganizador(organizador.getIdorganizador());
		}
		
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
		
		log.debug((patrocinador == null)?"El patrocinador es nulo, el nombre es: " + patrocinador.getNombre():"El patrocinador no es nulo." + patrocinador.getNombre());
		
		if (patrocinador.getNombre() != null && !"".equals(patrocinador.getNombre())) {
			log.debug("Nombre del patrocinador: " + patrocinador.getNombre());
			log.debug("Nombre de la foto" + patrocinador.getFotoNombre());
			evento.getPatrocinadores().add(patrocinador);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Patrocinador", patrocinador.getNombre() + " creado.");
			patrocinador = new Patrocinadores();
			patrocinadorCreado = true;
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Error");
			log.debug("estoy aqui");
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
	
	@Loggable
	public void handleFileUploadComplementoImagen(FileUploadEvent event) {
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
					complemento.setImagen(bytes);
					complemento.setNombreImagen(file.getFileName());
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Imagen", "agregada");
					facesContext.addMessage(null, message);
				} else {
					bytes = new byte[0];
				}
			} catch (IOException e) {
				log.error("algo ha ocurrido con la foto del evento");
			}
		}
	}
	
	@Loggable
	public String crearEvento() {
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String destinoUrl = Constantes.Rutas.Administracion.MIS_EVENTOS;
		
		if(evento.getNombre() != null && !"".equals(evento.getNombre())){
			if(evento.getDireccion() != null && !"".equals(evento.getDireccion())){
				if(evento.getFechaIni() != null && evento.getFechaFin() != null){
					if(evento.getIdtipoevento() != null){
						if(evento.getOrganizador() != null){
							if(evento.getDdTipoEntradas() != null && !evento.getDdTipoEntradas().isEmpty()){
								if(evento.getAforo() > 0){
									eventoDao.crearEvento(evento);
									message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento", evento.getIdevento() + " creado.");
									facesContext.addMessage(null, message);
								}else{
									message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + "Indica el aforo del evento", "Indica el aforo del evento");
									facesContext.addMessage(null, message);
									destinoUrl = Constantes.Rutas.NULA;
								}
							}else{
								message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + "Indica el tipo de entradas que tendrá el evento", "Indica el tipo de entradas que tendrá el evento");
								facesContext.addMessage(null, message);
								destinoUrl = Constantes.Rutas.NULA;
							}
						}else{
							message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + "Indica el organizador del evento", "Indica el organizador del evento");
							facesContext.addMessage(null, message);
							destinoUrl = Constantes.Rutas.NULA;
						}
					}else{
						message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + "Indica el tipo de evento", "Indica el tipo de evento");
						facesContext.addMessage(null, message);
						destinoUrl = Constantes.Rutas.NULA;
					}
				}else{
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + "Asegurate de indicar las fechas de inicio y fin para el evento", "Asegurate de indicar las fechas de inicio y fin para el evento");
					facesContext.addMessage(null, message);
					destinoUrl = Constantes.Rutas.NULA;
				}
			}else{
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + "Indica una dirección para el evento", "Indica una dirección para el evento");
				facesContext.addMessage(null, message);
				destinoUrl = Constantes.Rutas.NULA;
			}
		}else{
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + "Indica un nombre para el evento", "Indica un nombre para el evento");
			facesContext.addMessage(null, message);
			destinoUrl = Constantes.Rutas.NULA;
		}
		
		log.debug("DestinoUrl: " + destinoUrl);
		return destinoUrl;
	}
	
	@Loggable
	public void crearComplemento() {
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		log.debug("");
		evento.getDdTipoComplementos().add(complemento);
		complementoList.add(complemento);
		complemento = new DdTipoComplemento();
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Complemento", " creado.");
		facesContext.addMessage(null, message);
	}
	
	@Loggable
	public void crearTipoEntrada() {
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// TODO crear comprobaciones para no crearlo vacío del todo
		evento.getDdTipoEntradas().add(tipoEntrada);
		tipoEntrada = new DdTipoEntrada();
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de Entrada",  "Creada.");
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

	public DdTipoEntrada getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(DdTipoEntrada tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public DdTipoComplemento getComplemento() {
		return complemento;
	}

	public void setComplemento(DdTipoComplemento complemento) {
		this.complemento = complemento;
	}

	public List<DdTipoComplemento> getComplementoList() {
		return complementoList;
	}

	public void setComplementoList(List<DdTipoComplemento> complementoList) {
		this.complementoList = complementoList;
	}
	
	
	
}

