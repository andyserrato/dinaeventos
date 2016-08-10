package org.dinamizadores.dinaeventos.view.valenciaConnect;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.EntradaComplemento;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.ConversorNumeroSerie;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.view.EventoBean;

import com.mangopay.entities.CardRegistration;

@Named
@ViewScoped
@Loggable
public class ComprarEntradaValenciaConnect implements Serializable {

	private static final long serialVersionUID = -1019333060794727516L;

	private final Logger log = LogManager.getLogger(EventoBean.class);

	private Integer id;

	private Usuario usuario;

	private BigDecimal total = new BigDecimal(0);

	private BigDecimal handlingFee = new BigDecimal(0);

	private Integer cantidad = 0;

	private String nombreEntrada = null;

	private List<EntradasCompleta> listadoEntradas = new ArrayList<>();

	private List<Entrada> listadoEntradasEntidad = new ArrayList<>();

	private int cuenta = 0;

	private Boolean envioConjunto = false;

	private String data = null;

	private String accessKeyRef = null;

	private String returnURL = null;

	private CardRegistration tarjetaRegistrada;

	private String cardCvx = null;

	private String cardExpirationDate = null;

	private String cardNumber = null;

	private Evento evento = null;

	private ConversorNumeroSerie conversorNumeroSerie = new ConversorNumeroSerie();

	@EJB
	private EntradaDao entradaDao;

	@EJB
	private UsuarioDao usuarioDao;

	@EJB
	private EventoDao eventoDao;

	@EJB
	private DAOGenerico daoGenerico;

	@PostConstruct
	public void init() {

		listadoEntradas = (List<EntradasCompleta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaEntradas");
		envioConjunto = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("envioConjunto");
		evento = (Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento");

		// tarjetaRegistrada = (CardRegistration) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tarjeta");
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tarjeta", tarjetaRegistrada);
		insertarTotal();

		// data = tarjetaRegistrada.PreregistrationData;
		// accessKeyRef = tarjetaRegistrada.AccessKey;
		// returnURL = "http://localhost:8080/dinaeventos/faces/valenciaConnect/comprar/finalizarPago.xhtml?faces-redirect=true";
		crearEntradasUsuarios();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaEntradas", listadoEntradas);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaEntradasEntidad", listadoEntradasEntidad);
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

	public void crearEntradasUsuarios() {

		for (EntradasCompleta entrada : listadoEntradas) {
			crearEntradaAndUsuario(entrada);
		}

	}

	public void crearUsuario(Usuario usuario) {

		usuario.setActivo(true);
		usuario.setBloqueado(false);
		usuario.setCliente(true);
		usuarioDao.create(usuario);
	}

	// TODO [EQUIPO] normalizar este método que es una guarrada
	public void crearEntradaAndUsuario(EntradasCompleta entradaCompletaDTO) {

		try {
			log.debug("Se procede a crear el usuario con email: " + entradaCompletaDTO.getUsuario().getEmail());
			crearUsuario(entradaCompletaDTO.getUsuario());
			log.debug("Usuario peristido con id: " + entradaCompletaDTO.getUsuario().getIdUsuario());

			Entrada entradaEntidad = new Entrada();
			entradaEntidad.setActiva(true);
			entradaEntidad.setDentrofuera(false);

			entradaEntidad.setValidada(true);
			entradaEntidad.setVendida(false);

			// TODO [EQUIPO] Corregir al normalizar y tener todos los datos claros
			// en.setIdformapago(1);
			// en.setIdtipoiva(1);

			entradaEntidad.setIdevento(evento.getIdevento());
			entradaEntidad.setIdtipoentrada(entradaCompletaDTO.getIdTipoEntrada().intValue());

			Calendar calendario = Calendar.getInstance();

			String numeroserie = entradaCompletaDTO.getUsuario().getDni() + "" + calendario.getTimeInMillis();
			numeroserie = conversorNumeroSerie.convertirNumero(numeroserie);
			entradaEntidad.setNumeroserie(numeroserie);

			BigDecimal total = new BigDecimal(0);

			entradaEntidad.setFechaVendida(calendario.getTime());
			entradaCompletaDTO.setFechaVendida(entradaEntidad.getFechaVendida());
			for (ComplementoEntero complementoDTO : entradaCompletaDTO.getListaComplementos()) {
				if (complementoDTO.getCantidad() > 0) {
					total = total.add(complementoDTO.getComplemento().getPrecio());
				}
			}

			entradaCompletaDTO.setNumeroserie(entradaEntidad.getNumeroserie());
			total = total.add(entradaCompletaDTO.getPrecio());
			entradaEntidad.setPrecio(total);

			entradaEntidad.setUsuario(entradaCompletaDTO.getUsuario());

			entradaDao.create(entradaEntidad);
			entradaCompletaDTO.setIdEntrada(Long.valueOf(entradaEntidad.getIdentrada()));
			algoritmoInsercionEntradasComplementos(entradaCompletaDTO, entradaEntidad);

			listadoEntradasEntidad.add(entradaEntidad);

		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			// TODO [EQUIPO] verificar que log.debug("blah blah", exception) funciona igual que e.printStackTrace
			log.debug("Ha ocurrido un error creando el pdf de entrada", noSuchAlgorithmException);
			noSuchAlgorithmException.printStackTrace();
		}

	}

	public void algoritmoInsercionEntradasComplementos(EntradasCompleta entradaDTO, Entrada entradaEntidad) {

		try {
			EntradaComplemento entradanueva = null;
			List<ComplementoEntero> auxTipoComplemento;
			ComplementoEntero nuevocom;

			auxTipoComplemento = new ArrayList<ComplementoEntero>();
			for (ComplementoEntero c : entradaDTO.getListaComplementos()) {
				for (int i = 0; i < c.getCantidad(); i++) {
					nuevocom = new ComplementoEntero();
					nuevocom.setCantidad(c.getCantidad());
					nuevocom.setComplemento(c.getComplemento());
					auxTipoComplemento.add(nuevocom);
				}
			}
			EntradasCompleta entradaaux = new EntradasCompleta();
			entradaaux.setListaComplementos(auxTipoComplemento);

			for (ComplementoEntero com : entradaaux.getListaComplementos()) {
				if (com.getCantidad() > 0) {
					entradanueva = new EntradaComplemento();
					entradanueva.setDdTipoComplemento(com.getComplemento());
					entradanueva.setEntrada(entradaEntidad);
					entradaEntidad.getEntradaComplementos().add(entradanueva);
					daoGenerico.insertar(entradanueva);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public BigDecimal getHandlingFee() {
		handlingFee = total.multiply(total.multiply(new BigDecimal("0.03"))).add(new BigDecimal("2.35"));
		return handlingFee;
	}

	public void setHandlingFee(BigDecimal handlingFee) {
		this.handlingFee = handlingFee;
	}

	/**
	 * TODO [ANDY] No mostrar complementos según tipo de entrada. Esto debe mejorarse xq es una chapuza
	 */
	public boolean isComplementoRendered(String nombreTipoEntrada) {
		boolean renderComplementos = true;

		if (nombreTipoEntrada != null && !"".equals(nombreTipoEntrada) && nombreTipoEntrada.equals("Completa")) {
			renderComplementos = false;
		}

		return renderComplementos;
	}

	public String getFechaFormateada(Date fecha) {
		return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
	}

	public BigDecimal getTotalAPagarComplementos(BigDecimal precioComplemento, int cantidadComplemento) {
		return precioComplemento.multiply(new BigDecimal(cantidadComplemento));
	}
}
