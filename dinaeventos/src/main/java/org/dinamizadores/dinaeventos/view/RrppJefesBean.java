package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DiccionarioDao;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.model.DdSexo;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.GlobalProvincias;
import org.dinamizadores.dinaeventos.model.RrppJefes;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.primefaces.model.UploadedFile;

/**
 * Backing bean for RrppJefes entities.
 * <p/>
 * This class provides CRUD functionality for all RrppJefes entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

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
	private List<GlobalProvincias> ddProvincias;
	private List<GlobalCodigospostales> codigosPostales; 
	
	@PostConstruct
	public void init() {
		jefeEntity = new RrppJefes();
		jefeEntity.setUsuario(new Usuario());
		ddProvincias = diccionarioDao.getDdGlobalProvincias();
		codigosPostales = new ArrayList<>();
		ddSexos = diccionarioDao.getDdSexos();
		
	}
	
	public void create() {
		jefeEntity = usuarioDao.crearRrppJefe(jefeEntity);
	}
	
	public void storeImage() {
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Foto Subida", imageFile.getFileName() + " Se ha subido");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<DdSexo> getDdSexos() {
		return ddSexos;
	}

	public void setDdSexos(List<DdSexo> ddSexos) {
		this.ddSexos = ddSexos;
	}

	public List<GlobalProvincias> getDdProvincias() {
		return ddProvincias;
	}

	public void setDdProvincias(List<GlobalProvincias> ddProvincias) {
		this.ddProvincias = ddProvincias;
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
	
	public void actualizaLocalidadesByCP(Integer IdProvincia) {
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
