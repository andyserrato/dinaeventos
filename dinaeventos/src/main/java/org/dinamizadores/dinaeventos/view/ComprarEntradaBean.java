package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

import com.mangopay.entities.CardRegistration;

@Named
@ViewScoped
@Loggable

public class ComprarEntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdTipoComplementoBean entities
	 */

	private Integer id;

	private Usuario usuario;

	private BigDecimal total = new BigDecimal(0);

	private Integer cantidad = 0;

	private String nombreEntrada = null;

	private List<EntradasCompleta> listadoEntradas = new ArrayList<EntradasCompleta>();

	private int cuenta = 0;

	private Boolean envioConjunto = false;

	private String data = null;

	private String accessKeyRef = null;

	private String returnURL = null;

	private CardRegistration tarjetaRegistrada;

	private String cardCvx = null;

	private String cardExpirationDate = null;

	private String cardNumber = null;

	@PostConstruct
	public void init() {

		listadoEntradas = (List<EntradasCompleta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaEntradas");

		tarjetaRegistrada = (CardRegistration) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tarjeta");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tarjeta", tarjetaRegistrada);
		insertarTotal();
		data = tarjetaRegistrada.PreregistrationData;
		accessKeyRef = tarjetaRegistrada.AccessKey;
		returnURL = "http://localhost:8080/dinaeventos/faces/valenciaConnect/comprar/finalizarPago.xhtml?faces-redirect=true";
	}

	public void insertarTotal() {

		for (EntradasCompleta entrada : listadoEntradas) {
			for (ComplementoEntero c : entrada.getListaComplementos()) {
				total = total.add(c.getComplemento().getPrecio().multiply(BigDecimal.valueOf(c.getCantidad())));
			}
			total = total.add(entrada.getPrecio());
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);

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

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	public Boolean getEnvioConjunto() {
		return envioConjunto;
	}

	public void setEnvioConjunto(Boolean envioConjunto) {
		this.envioConjunto = envioConjunto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAccessKeyRef() {
		return accessKeyRef;
	}

	public void setAccessKeyRef(String accessKeyRef) {
		this.accessKeyRef = accessKeyRef;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public CardRegistration getTarjetaRegistrada() {
		return tarjetaRegistrada;
	}

	public void setTarjetaRegistrada(CardRegistration tarjetaRegistrada) {
		this.tarjetaRegistrada = tarjetaRegistrada;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpirationDate() {
		return cardExpirationDate;
	}

	public void setCardExpirationDate(String cardExpirationDate) {
		this.cardExpirationDate = cardExpirationDate;
	}

	public String getCardCvx() {
		return cardCvx;
	}

	public void setCardCvx(String cardCvx) {
		this.cardCvx = cardCvx;
	}
}
