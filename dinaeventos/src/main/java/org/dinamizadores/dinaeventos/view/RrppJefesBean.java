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
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DiccionarioDao;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.model.DdSexo;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.RrppJefes;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("rrppJefe")
@ViewScoped
@Loggable
public class RrppJefesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private UsuarioDao usuarioDao;
	@EJB
	private DiccionarioDao diccionarioDao;
	private List<DdSexo> ddSexos;
	private final Logger log = LogManager.getLogger(RrppJefesBean.class);
	private RrppJefes jefeEntity;
	private UploadedFile imageFile;
	private List<GlobalCodigospostales> codigosPostales; 
	
	@PostConstruct
	public void init() {
		jefeEntity = new RrppJefes();
		jefeEntity.setUsuario(new Usuario());
		jefeEntity.getUsuario().setCodigoPostal(new GlobalCodigospostales());
		codigosPostales = new ArrayList<>();
		ddSexos = diccionarioDao.getDdSexos();
		
	}
	
	public void create() {
		if (!"".equalsIgnoreCase(imageFile.getFileName())) {
			try {
				byte[] bytes;
				InputStream is = imageFile.getInputstream();

				if (is != null) {
					bytes = IOUtils.toByteArray(is);
					is.close();
				} else {
					bytes = new byte[0];
				}

				is.close();

				jefeEntity.getUsuario().setFotoperfil(bytes);
				jefeEntity.getUsuario().setFotoNombre(imageFile.getFileName());
			} catch (IOException e) {
				log.error("Error de conversión al guardar el logo.");
			}
		}
		
		jefeEntity = usuarioDao.crearRrppJefe(jefeEntity);
	}
	
	@Loggable
	public void handleFileUploadFotoEvento(FileUploadEvent event) {
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
					jefeEntity.getUsuario().setFotoperfil(bytes);
					jefeEntity.getUsuario().setFotoNombre(file.getFileName());
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
	
	public List<DdSexo> getDdSexos() {
		return ddSexos;
	}

	public void setDdSexos(List<DdSexo> ddSexos) {
		this.ddSexos = ddSexos;
	}

	public RrppJefes getJefeEntity() {
		return jefeEntity;
	}

	public void setJefeEntity(RrppJefes jefeEntity) {
		this.jefeEntity = jefeEntity;
	}
	
	public List<GlobalCodigospostales> getCodigosPostales() {
		return codigosPostales;
	}

	public void setCodigosPostales(List<GlobalCodigospostales> codigosPostales) {
		this.codigosPostales = codigosPostales;
	}
	
	public void actualizaLocalidadesByCP(String IdProvincia) {
		codigosPostales = diccionarioDao.actualizaLocalidadesByCP(IdProvincia);
	}

	public UploadedFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(UploadedFile imageFile) {
		this.imageFile = imageFile;
	}
	
	//TODO [ANDY] en el pcalendar sólo deja 20 años corregir
}
