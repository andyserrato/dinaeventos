package org.dinamizadores.dinaeventos.view.valenciaConnect;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.utiles.plataformapagos.Pagar;

import com.mangopay.entities.CardRegistration;

@Named("clientesAndComplementosBean")
@ViewScoped
@Loggable
public class CrearClientesAndComplementos implements Serializable {

	private static final long serialVersionUID = 4214401701169789696L;

	@EJB
	private UsuarioDao usuarioDao;

	@EJB
	private EntradaDao tipoComplementoDao;

	private Integer id;

	private Usuario usuario;

	private BigDecimal total = new BigDecimal(0);

	private Integer cantidad = 0;

	private Boolean envioConjunto = false;

	private Evento evento;

	private String nombreEntrada = null;

	private Map<Long, List<BigDecimal>> listaPrecios = new HashMap<Long, List<BigDecimal>>();

	private List<EntradasCompleta> listadoEntradas = new ArrayList<EntradasCompleta>();

	private List<DdTipoEntrada> tiposEntrada = new ArrayList<DdTipoEntrada>();

	private List<DdTipoComplemento> listadoComplemento = new ArrayList<DdTipoComplemento>();

	@PostConstruct
	public void init() {
		total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		listaPrecios = (Map<Long, List<BigDecimal>>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lista");
		tiposEntrada = (List<DdTipoEntrada>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiposEntrada");
		evento = (Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento");
		calcularInfoUsuarios();
		calcularInfoComplementos();
	}

	private void calcularInfoUsuarios() {
		for (Entry<Long, List<BigDecimal>> e : listaPrecios.entrySet()) {
			for (int i = 0; i < e.getValue().size(); i++) {
				EntradasCompleta entrada = new EntradasCompleta();
				entrada.setIdTipoEntrada(e.getKey());
				entrada.setCantidadEntradas(e.getValue());
				for (DdTipoEntrada d : tiposEntrada)
					if (d.getIdtipoentrada() == e.getKey()) {
						entrada.setNombre(d.getNombre());
						entrada.setIdTipoEntrada(Long.valueOf(d.getIdtipoentrada()));
					}
				entrada.setPrecio(e.getValue().get(0));
				listadoEntradas.add(entrada);
			}
		}
	}

	// public void verificarDNI(AjaxBehaviorEvent event) {
	// try {
	// for (EntradasCompleta e : listadoEntradas) {
	// Usuario us = new Usuario();
	// us = usuarioDao.getUsuarioDni(e.getUsuario().getDni(), evento.getIdevento());
	// if (us != null) {
	// FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El DNI indicado ya existe en el
	// sistema."));
	// }
	//
	// }
	// } catch (Exception e) {
	// FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El DNI indicado ya existe en el
	// sistema."));
	// }
	//
	// }

	public String toPagarEntradas() {

		Pagar pa = new Pagar();
		String idUsuario = pa.nuevoUsuario(listadoEntradas.get(0).getUsuario());
		CardRegistration tarjetaRegistrada = pa.nuevoTarjeta(idUsuario);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaEntradas", listadoEntradas);

		// Enviamos los datos a la nueva pagina
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tarjeta", tarjetaRegistrada);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("envioConjunto", envioConjunto);

		return Constantes.Rutas.Entrada.PAGAR_VALENCIA_CONNECT;

	}

	public void crearUsuarioBasico() {
		// TODO [ANDY] revisar xq esto está en blanco
	}

	private void calcularInfoComplementos() {
		setListadoComplemento(tipoComplementoDao.listTipoComplemento(evento.getIdevento()));

		for (EntradasCompleta entrada : listadoEntradas) {
			entrada.getListaComplementos().clear();

			for (DdTipoComplemento c : listadoComplemento) {
				ComplementoEntero comple = new ComplementoEntero();
				comple.setComplemento(c);
				entrada.getListaComplementos().add(comple);
			}
		}
	}

	public void agregarComplemento() {
		total = new BigDecimal(0);

		for (EntradasCompleta entrada : listadoEntradas) {
			for (ComplementoEntero c : entrada.getListaComplementos()) {
				total = total.add(c.getComplemento().getPrecio().multiply(BigDecimal.valueOf(c.getCantidad())));
			}

			total = total.add(entrada.getPrecio());
		}
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Map<Long, List<BigDecimal>> getListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(Map<Long, List<BigDecimal>> listaPrecios) {
		this.listaPrecios = listaPrecios;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombreEntrada() {
		return nombreEntrada;
	}

	public void setNombreEntrada(String nombreEntrada) {
		this.nombreEntrada = nombreEntrada;
	}

	public List<EntradasCompleta> getListadoEntradas() {
		return listadoEntradas;
	}

	public void setListadoEntradas(List<EntradasCompleta> listadoEntradas) {
		this.listadoEntradas = listadoEntradas;
	}

	public List<DdTipoEntrada> getTiposEntrada() {
		return tiposEntrada;
	}

	public void setTiposEntrada(List<DdTipoEntrada> tiposEntrada) {
		this.tiposEntrada = tiposEntrada;
	}

	public List<DdTipoComplemento> getListadoComplemento() {
		return listadoComplemento;
	}

	public void setListadoComplemento(List<DdTipoComplemento> listadoComplemento) {
		this.listadoComplemento = listadoComplemento;
	}

	public Boolean getEnvioConjunto() {
		return envioConjunto;
	}

	public void setEnvioConjunto(Boolean envioConjunto) {
		this.envioConjunto = envioConjunto;
	}

	/**
	 * TODO [ANDY] No mostrar complementos según tipo de entrada. Esto debe mejorarse xq es una chapuza
	 */
	public boolean isComplementoRendered(String nombreTipoEntrada) {
		boolean renderComplementos = true;

		if (nombreTipoEntrada != null && !"".equals(nombreTipoEntrada) && nombreTipoEntrada.equals("COMPLETA")) {
			renderComplementos = false;
		}

		return renderComplementos;
	}

	public boolean isEnviarCadaAmigoRedered() {
		return (listadoEntradas.size() > 1);
	}

}
