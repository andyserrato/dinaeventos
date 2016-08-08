package org.dinamizadores.dinaeventos.view.valenciaConnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
import org.dinamizadores.dinaeventos.utiles.pdf.FormarPDF;

import com.itextpdf.text.DocumentException;

@Named
@ViewScoped
@Loggable
public class FinalizarPagoValenciaConnect implements Serializable {

	private static final long serialVersionUID = -3334270119276327476L;

	private static final Logger log = LogManager.getLogger(UsuarioDao.class);

	@EJB
	private EntradaDao entradaDao;

	@EJB
	private UsuarioDao usuarioDao;

	@EJB
	private EventoDao eventoDao;

	@EJB
	private DAOGenerico daoGenerico;

	private ConversorNumeroSerie conversorNumeroSerie = new ConversorNumeroSerie();

	private BigDecimal total = new BigDecimal(0);

	private Integer cantidad = 0;

	private String nombreEntrada = null;

	private EntradasCompleta entrada = new EntradasCompleta();

	private List<EntradasCompleta> listadoEntradas = new ArrayList<EntradasCompleta>();

	private Evento evento = new Evento();

	private int cuenta = 0;

	private Boolean envioConjunto = false;

	private String idTarjeta = null;

	@PostConstruct
	public void init() {
		listadoEntradas = (List<EntradasCompleta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaEntradas");
		envioConjunto = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("envioConjunto");
		evento = (Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento");

		boolean pagoRealizado = false;

		try {
			pagoRealizado = getRespuestaTransaccionPayPal();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (pagoRealizado) {
			efectuarPago();
		}

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public boolean getRespuestaTransaccionPayPal() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		URL url = new URL(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("posturl"));
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("at", externalContext.getInitParameter("authtoken"));
		params.put("cmd", "_notify-synch");
		log.debug("at " + externalContext.getInitParameter("authtoken"));
		params.put("tx", request.getParameter("tx"));
		log.debug("tx " + request.getParameter("tx"));

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		// Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		// for (int c; (c = in.read()) >= 0;)
		// System.out.print((char) c);

		// TODO [IVAN] parsear la respuesta apropiadamente

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		// TODO [EQUIPO] alerta de chapuza mejorarla
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("SUCCESS")) {
				return true;
			}
			response.append(inputLine);
		}

		return false;
	}

	public void efectuarPago() {

		try {

			crearEntradasUsuarios();

			// Pagar pagar = new Pagar();
			// CardRegistration tarjetaRegistrada = (CardRegistration)
			// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tarjeta");
			// total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
			//
			// pagar.setTotal(total);
			// String[] lista = null;
			// lista = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("data");
			//
			// tarjetaRegistrada.RegistrationData = lista[0];
			//
			// pagar.actualizarTarjeta(tarjetaRegistrada);

			FormarPDF.main(listadoEntradas, evento, envioConjunto);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			entradaEntidad.setVendida(true);

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

	public EntradasCompleta getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradasCompleta entrada) {
		this.entrada = entrada;
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

	public String getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(String idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

}
