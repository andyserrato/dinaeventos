package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

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
import org.dinamizadores.dinaeventos.utiles.plataformapagos.Pagar;

import com.itextpdf.text.DocumentException;
import com.mangopay.entities.CardRegistration;

@Named
@ViewScoped
@Loggable

public class FinalizarPagoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdTipoComplementoBean entities
	 */

	@EJB
	private EntradaDao entradaDao;

	@EJB
	private UsuarioDao usuarioDao;

	@EJB
	private EventoDao eventoDao;

	/** Acceso a la capa DAO para persistir los datos. */
	@EJB
	private DAOGenerico dao;

	private ConversorNumeroSerie con = new ConversorNumeroSerie();

	private Integer id;

	private Usuario usuario;

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
		evento = eventoDao.findById(1);
		// evento = (Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento");

		efectuarPago();

		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public void efectuarPago() {

		try {

			crearEntradasUsuarios();

			Pagar pa = new Pagar();
			CardRegistration tarjetaRegistrada = (CardRegistration) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tarjeta");
			total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");

			pa.setTotal(total);
			String[] lista = null;
			lista = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("data");

			tarjetaRegistrada.RegistrationData = lista[0];

			pa.actualizarTarjeta(tarjetaRegistrada);

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

			crearUsuario(entrada.getUsuario());
			crearEntrada(entrada);
		}

	}

	public void crearUsuario(Usuario usuario) {

		usuario.setActivo(true);
		usuario.setBloqueado(false);

		usuarioDao.create(usuario);

	}

	public void crearEntrada(EntradasCompleta entrada) {

		try {
			Entrada en = new Entrada();
			en.setActiva(true);
			en.setDentrofuera(false);
			en.setValidada(false);
			en.setVendida(true);

			en.setIdformapago(1);
			en.setIdtipoiva(1);
			en.setIdevento(1);
			en.setIdtipoentrada(entrada.getIdTipoEntrada().intValue());
			Calendar cal = new GregorianCalendar();

			String numeroserie = entrada.getUsuario().getDni() + "" + cal.getTimeInMillis();
			numeroserie = con.convertirNumero(numeroserie);
			en.setNumeroserie(numeroserie);

			BigDecimal total = new BigDecimal(0);

			en.setFechaVendida(cal.getTime());
			for (ComplementoEntero com : entrada.getListaComplementos()) {
				if (com.getCantidad() > 0) {
					total = total.add(com.getComplemento().getPrecio());
				}

			}
			entrada.setNumeroserie(en.getNumeroserie());
			total = total.add(entrada.getPrecio());
			en.setPrecio(total);

			Usuario usu = usuarioDao.getUsuarioDni(entrada.getUsuario().getDni(), en.getIdevento());
			if (usu != null)
				en.setIdusuario(usu.getIdUsuario());

			entradaDao.create(en);
			algoritmoInsercionEntradasComplementos(entrada, en);

			entrada.setIdEntrada((long) entradaDao.getEntradaDniEvento(en.getIdusuario(), en.getIdevento()));
			String format = String.format("%03d", entrada.getIdEntrada());
			entrada.setIdEntrada(Long.valueOf(format));

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void algoritmoInsercionEntradasComplementos(EntradasCompleta entrada, Entrada en) {

		try {
			EntradaComplemento entradanueva = null;
			List<ComplementoEntero> auxTipoComplemento;
			ComplementoEntero nuevocom;

			auxTipoComplemento = new ArrayList<ComplementoEntero>();
			for (ComplementoEntero c : entrada.getListaComplementos()) {
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
					entradanueva.setEntrada(en);
					en.getEntradaComplementos().add(entradanueva);
					dao.insertar(entradanueva);
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
