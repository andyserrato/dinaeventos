package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.ConversorNumeroSerie;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.utiles.pdf.FormarPDF;
import org.dinamizadores.dinaeventos.utiles.plataformapagos.Pagar;
import org.dinamizadores.dinaeventos.dto.complementoEntero;
import org.dinamizadores.dinaeventos.dto.entradasCompleta;

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
	
	private ConversorNumeroSerie con = new ConversorNumeroSerie();

	private Integer id;
	private Usuario usuario;
	
	private BigDecimal total = new BigDecimal(0);
	
	private Integer cantidad = 0;
	
	private String nombreEntrada = null;
	
	private entradasCompleta entrada = new entradasCompleta();
	
	private List<entradasCompleta> listadoEntradas = new ArrayList<entradasCompleta>();
	
	private Evento evento = new Evento();
	
	private int cuenta = 0;
	
	private Boolean envioConjunto = false;
	
	private String idTarjeta = null;
	
	@PostConstruct
	public void init(){
		listadoEntradas = (List<entradasCompleta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaEntradas");
		envioConjunto = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("envioConjunto");
		evento = eventoDao.findById(1);
		//evento = (Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento");

		
		efectuarPago();
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	
	
	public void efectuarPago(){
		
			try {

				crearEntradasUsuarios();
				
				Pagar pa = new Pagar();
				CardRegistration tarjetaRegistrada = (CardRegistration) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tarjeta");
				total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
				
				pa.setTotal(total);
				String[] lista = null;
				lista =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("data");
				
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
	
	public void crearEntradasUsuarios(){
		
		for (entradasCompleta entrada : listadoEntradas){
			
			crearUsuario(entrada.getUsuario());
			crearEntrada(entrada);		
		}
		
	}
	public void crearUsuario(Usuario usuario){
		
		usuario.setActivo(true);
		usuario.setBloqueado(false);
		
		usuarioDao.create(usuario);
		
	}
	
	public void crearEntrada(entradasCompleta entrada){
		
		try {
		Entrada en = new Entrada();
		en.setActiva(true);
		en.setDentrofuera(false);
		en.setValidada(false);
		en.setVendida(true);
		
		
		en.setIdformapago(1);
		en.setIdtipoiva(1);
		en.setIdevento(1);
		Calendar cal = new GregorianCalendar();
		
		String numeroserie = entrada.getUsuario().getDni() + "" + cal.getTimeInMillis();
		numeroserie = con.convertirNumero(numeroserie);
		en.setNumeroserie(numeroserie);
		
		BigDecimal total = new BigDecimal(0);
		en.setFechaVendida(cal.getTime());
		Set<DdTipoComplemento> listacomplemento = new HashSet<DdTipoComplemento>(0);
		for (complementoEntero com : entrada.getListaComplementos()){
			if (com.getCantidad() > 0){
			listacomplemento.add(com.getComplemento());
			total = total.add(com.getComplemento().getPrecio());
			}
			
		}
		entrada.setNumeroserie(en.getNumeroserie());
		total = total.add(entrada.getPrecio());
		en.setPrecio(total);
		//en.setDdTipoComplementos(listacomplemento);
		
		Usuario usu = usuarioDao.getUsuarioDni(entrada.getUsuario().getDni());
		en.setIdusuario(usu.getIdUsuario());
		
		entradaDao.create(en);
		
		entrada.setIdEntrada((long) entradaDao.getEntradaDniEvento(en.getIdusuario(),en.getIdevento()));
		System.out.println("El valor: " + entrada.getIdEntrada());
		String format = String.format("%03d", entrada.getIdEntrada());
		entrada.setIdEntrada(Long.valueOf(format));
		
		} catch (NoSuchAlgorithmException e) {
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

	public List<entradasCompleta> getListadoEntradas() {
		return listadoEntradas;
	}

	public void setListadoEntradas(List<entradasCompleta> listadoEntradas) {
		this.listadoEntradas = listadoEntradas;
	}

	public entradasCompleta getEntrada() {
		return entrada;
	}

	public void setEntrada(entradasCompleta entrada) {
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
