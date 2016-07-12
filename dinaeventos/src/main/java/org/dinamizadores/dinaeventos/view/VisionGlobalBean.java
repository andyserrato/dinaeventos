package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dto.EntradasTiempoDTO;
import org.dinamizadores.dinaeventos.dto.EventoDTO;
import org.dinamizadores.dinaeventos.utiles.BBDDFaker;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Named("visionGlobal")
@ViewScoped
public class VisionGlobalBean implements Serializable{
	
	/** UID. */
	private static final long serialVersionUID = 3554407724413902217L;
	
	/** Comentario inútil. */
	private final Logger log = LogManager.getLogger(EventoBean.class);

	/** LoginBean inyectado para poder recoger datos globales de la aplicación. */
	@Inject
	private LoginBean loginBean;

	/** Objeto DTO que contiene datos relativos al Evento. */
	private EventoDTO eventoDTO;
	
	/** Objeto modelador que define el gráfico de ventas por sexo. */
	private PieChartModel quesoSexual;
	
	/** Objeto modelador que define el gráfico de ventas por rango de edad. */
	private PieChartModel quesoEdad;
	
	/** Objeto modelador que define el gráfico de cantidad de entradas vendidas por fecha. */
	private LineChartModel entradasTiempo;
	
	/** Atributo temporal. */
	private int entradasSMS;
	
	/** Atributo temporal. */
	private int cambiosNombre;
	
	/** Objeto EJB para comunicarse con la BBDD. */
	@EJB
	private EventoDao eventoDAO;
	
	@EJB
	private BBDDFaker bbddFaker;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {
		entradasSMS = 6988;
		cambiosNombre = 529;
		
		//TODO Posteriormente ese 1 'hardcodeado' se sacará del loginBean
		eventoDTO = eventoDAO.getVisionGlobal(1);
		
		if (eventoDTO !=  null) {
			crearModeloSexual();
			crearGraficoEntradasTiempo();
			crearModeloEdades();
		}
		
		return;
	}
	
	/**
	 * Método para rellenar el gráfico de venta por sexos.
	 */
	private void crearModeloSexual(){
		quesoSexual = new PieChartModel();
		
		//quesoSexual.setTitle("Distribución por Sexo");
		quesoSexual.set("Hombre", eventoDTO.getNumHombres());
		quesoSexual.set("Mujer", eventoDTO.getNumMujeres());
		quesoSexual.setLegendPosition("w");
		quesoSexual.setExtender("skinPie");

		return;
	}
	
	private void crearModeloEdades(){
		quesoEdad = new PieChartModel();
		
		//quesoEdad.setTitle("Distribución por edades");
		quesoEdad.set("18 y 19", eventoDAO.getNumeroVentasPorRangoEdad(1, 18, 19));
		quesoEdad.set("20 y 21", eventoDAO.getNumeroVentasPorRangoEdad(1, 20, 21));
		quesoEdad.set("22 - 24", eventoDAO.getNumeroVentasPorRangoEdad(1, 22, 24));
		quesoEdad.set("> 25", eventoDAO.getNumeroVentasPorRangoEdad(1, 25, 100));
		quesoEdad.setLegendPosition("w");
		quesoEdad.setExtender("skinPie");
		
		return;
	}
	
	/**
	 * Método para rellenar el gráfico de ventas por tiempo.
	 */
	private void crearGraficoEntradasTiempo(){
		ArrayList<EntradasTiempoDTO> lista = eventoDAO.getNumEntradasTiempo(1);
		entradasTiempo = new LineChartModel();
		LineChartSeries serieGrafico = new LineChartSeries();
		Calendar calendar = Calendar.getInstance();
		
		serieGrafico.setLabel("Cantidad de entradas vendidas.");
		
		String fecha = null;
		for(EntradasTiempoDTO entrada : lista){
			
			if (entrada != null && entrada.getFecha() != null && entrada.getCantidad() != 0) {
				calendar.setTime(entrada.getFecha());
				fecha = (calendar.get(Calendar.YEAR)) + "-";
				
				if((calendar.get(Calendar.MONTH) + 1) <= 9){
					fecha += "0";
				}
				
				fecha+= (calendar.get(Calendar.MONTH) + 1) + "-";
				
				if(calendar.get(Calendar.DAY_OF_MONTH) <= 9){
					fecha+= "0";
				}
				
				fecha += calendar.get(Calendar.DAY_OF_MONTH);
				
				log.debug("La fecha vale: " + fecha + " la cantidad: " + entrada.getCantidad());
				
				serieGrafico.set(fecha, entrada.getCantidad());
			}
		}
		
		entradasTiempo.addSeries(serieGrafico);
		//entradasTiempo.getAxis(AxisType.Y).setLabel("Abonos Vendidos");
		entradasTiempo.setExtender("skinChart");
		
		DateAxis axis = new DateAxis("Fecha");
        axis.setTickAngle(-45);
        axis.setMax("2016-07-01");
        axis.setTickFormat("%b %#d, %y");
        entradasTiempo.getAxes().put(AxisType.X, axis);
		
		//entradasTiempo.setTitle("Número de entradas vendidas por tiempo");
        entradasTiempo.setLegendPosition("e");
        Axis yAxis = entradasTiempo.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
		
		return;
	}
	
	/**
	 * @param loginBean the loginBean to set
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	/**
	 * @return the evento
	 */
	public EventoDTO getEvento() {
		return eventoDTO;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(EventoDTO evento) {
		this.eventoDTO = evento;
	}

	/**
	 * @return the quesoSexual
	 */
	public PieChartModel getQuesoSexual() {
		return quesoSexual;
	}

	/**
	 * @param quesoSexual the quesoSexual to set
	 */
	public void setQuesoSexual(PieChartModel quesoSexual) {
		this.quesoSexual = quesoSexual;
	}

	/**
	 * @return the quesoEdad
	 */
	public PieChartModel getQuesoEdad() {
		return quesoEdad;
	}

	/**
	 * @param quesoEdad the quesoEdad to set
	 */
	public void setQuesoEdad(PieChartModel quesoEdad) {
		this.quesoEdad = quesoEdad;
	}

	/**
	 * @return the entradasTiempo
	 */
	public LineChartModel getEntradasTiempo() {
		return entradasTiempo;
	}

	/**
	 * @param entradasTiempo the entradasTiempo to set
	 */
	public void setEntradasTiempo(LineChartModel entradasTiempo) {
		this.entradasTiempo = entradasTiempo;
	}

	/**
	 * @return the entradasSMS
	 */
	public int getEntradasSMS() {
		return entradasSMS;
	}

	/**
	 * @param entradasSMS the entradasSMS to set
	 */
	public void setEntradasSMS(int entradasSMS) {
		this.entradasSMS = entradasSMS;
	}

	/**
	 * @return the cambiosNombre
	 */
	public int getCambiosNombre() {
		return cambiosNombre;
	}

	/**
	 * @param cambiosNombre the cambiosNombre to set
	 */
	public void setCambiosNombre(int cambiosNombre) {
		this.cambiosNombre = cambiosNombre;
	}
}
