package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dto.DdGenerico;
import org.dinamizadores.dinaeventos.dto.EntradasTiempoDTO;
import org.dinamizadores.dinaeventos.dto.EventoDTO;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Named("ventasTipoEntradas")
@ViewScoped
public class VentasTipoEntradasBean implements Serializable {

	/** UID. */
	private static final long serialVersionUID = -1875864714871850567L;

	/** LoginBean inyectado para poder recoger datos globales de la aplicación. */
	@Inject
	private LoginBean loginBean;
	
	/** Objeto modelador que define el gráfico de cantidad de entradas vendidas por fecha. */
	private LineChartModel entradasTiempo;
	
	/** Objeto EJB para comunicarse con la BBDD. */
	@EJB
	private EventoDao eventoDAO;
	
	/** Objeto DTO que contiene datos relativos al Evento. */
	private EventoDTO evento;
	
	/** Tipos de entradas y sus ventas totales por evento. */
	private ArrayList<DdGenerico> tiposEntradaVendidas;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {
		
		evento = eventoDAO.getVisionGlobal(1);
		
		if(evento != null) {
			tiposEntradaVendidas = eventoDAO.getTiposDeEntradaVendidas(1);
			crearGraficoEntradasTiempo();
		}
	}
	
	/**
	 * Método para rellenar el gráfico de ventas por tiempo.
	 */
	private void crearGraficoEntradasTiempo(){
		ArrayList<String> listaTipos = eventoDAO.getTiposEntradaByEvento(1);
		ArrayList<EntradasTiempoDTO> listaTiempos = null;
		entradasTiempo = new LineChartModel();
		Calendar c = Calendar.getInstance();
		
		for(String s : listaTipos){
			System.out.println("El tipo vale = " + s);
			
			listaTiempos = eventoDAO.getNumEntradasTiempoPorTipoEntrada(1, s);
			LineChartSeries serieTipoEntrada = new LineChartSeries();
			serieTipoEntrada.setLabel(s);
			
			for(EntradasTiempoDTO e : listaTiempos){
				c.setTime(e.getFecha());
				String f = (c.get(Calendar.YEAR)) + "-";
				
				if((c.get(Calendar.MONTH) + 1) <= 9){
					f += "0";
				}
				
				f+= (c.get(Calendar.MONTH) + 1) + "-";
				
				if(c.get(Calendar.DAY_OF_MONTH) <= 9){
					f+= "0";
				}
				
				f += c.get(Calendar.DAY_OF_MONTH);
				
				serieTipoEntrada.set(f, e.getCantidad());
			}
			
			entradasTiempo.addSeries(serieTipoEntrada);
		}		
		
		//entradasTiempo.getAxis(AxisType.Y).setLabel("Entradas vendidas");
		entradasTiempo.setExtender("skinChart");
		
		DateAxis axis = new DateAxis("Fecha");
        axis.setTickAngle(-45);
        
        //Llegado el momento, aquí habrá que poner la fecha actual, ya que no deberían haber entradas vendidas con fecha futura
        axis.setMax("2016-12-31");
        
        axis.setTickFormat("%b %#d, %y");
        entradasTiempo.getAxes().put(AxisType.X, axis);
		
		entradasTiempo.setTitle("Número de entradas vendidas por tiempo");
        entradasTiempo.setLegendPosition("e");
        Axis yAxis = entradasTiempo.getAxis(AxisType.Y);
        yAxis.setMin(0);
        
        //Llegado el momento, este número tendrá que ser calculado a partir del máximo valor de las series para conservar unas proporciones en el gráfico
        //que permitan verlo sin problemas.
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
	 * @return the evento
	 */
	public EventoDTO getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(EventoDTO evento) {
		this.evento = evento;
	}

	/**
	 * @return the tiposEntradaVendidas
	 */
	public ArrayList<DdGenerico> getTiposEntradaVendidas() {
		return tiposEntradaVendidas;
	}

	/**
	 * @param tiposEntradaVendidas the tiposEntradaVendidas to set
	 */
	public void setTiposEntradaVendidas(ArrayList<DdGenerico> tiposEntradaVendidas) {
		this.tiposEntradaVendidas = tiposEntradaVendidas;
	}
}
