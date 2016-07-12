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
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

@Named("usuarioCrearBean")
@ViewScoped
@Loggable
public class UsuarioCrearBean implements Serializable {
	private static final long serialVersionUID = -8550280736153895180L;
	@EJB
	private UsuarioDao usuarioDao;
	@EJB
	private DiccionarioDao diccionarioDao;
	private Usuario usuario = new Usuario();
	private List<GlobalCodigospostales> codigosPostales;
	private List<DdSexo> ddSexos;
	private final Logger log = LogManager.getLogger(RrppJefesBean.class);
	
	@PostConstruct
	public void init() {
		usuario.setCodigoPostal(new GlobalCodigospostales());
		codigosPostales = new ArrayList<>();
		ddSexos = diccionarioDao.getDdSexos();
	}
	
	@Loggable
	public String crearUsuarioBasico() {
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		int emailUnique = 0;
		emailUnique = usuarioDao.isEmailUnique(usuario.getEmail());
		String redirection = "#";
		
		if (emailUnique == 0) {
			usuarioDao.create(usuario);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario", usuario.getEmail() + " creado.");
			usuario = new Usuario();
			redirection = "/evento/crearEvento.xhtml?faces-redirect=true"; 
			// TODO poner en rojo el email
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email", usuario.getEmail() + " tiene una cuenta asociada");
		}
		
		facesContext.addMessage(null, message);
		
		return redirection;
	}
	
	public void actualizaLocalidadesByCP(String IdProvincia) {
		codigosPostales = diccionarioDao.actualizaLocalidadesByCP(IdProvincia);
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<GlobalCodigospostales> getCodigosPostales() {
		return codigosPostales;
	}

	public void setCodigosPostales(List<GlobalCodigospostales> codigosPostales) {
		this.codigosPostales = codigosPostales;
	}

	public List<DdSexo> getDdSexos() {
		return ddSexos;
	}

	public void setDdSexos(List<DdSexo> ddSexos) {
		this.ddSexos = ddSexos;
	}
	
	
	
}