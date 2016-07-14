package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dto.EntradasTiempoDTO;
import org.dinamizadores.dinaeventos.dto.TipoVentaBasico;
import org.dinamizadores.dinaeventos.dto.TipoVentaCompleto;
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
@Named("ventasPrecios")
@ViewScoped
public class VentasPreciosBean implements Serializable {

	/** UID. */
	private static final long serialVersionUID = 1399115479895184370L;

	/** LoginBean inyectado para poder recoger datos globales de la aplicación. */
	@Inject
	private LoginBean loginBean;
	
	/** Objeto modelador que define el gráfico de cantidad de entradas vendidas por fecha. */
	private LineChartModel entradasTiempo;
	
	/** Objeto EJB para comunicarse con la BBDD. */
	@EJB
	private EventoDao eventoDAO;
	
	/** Lista desglosada por tipos de ventas con la cantidad ingresada para cada uno. */
	private ArrayList<TipoVentaCompleto> listaVentas;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {
		
		crearGraficoIngresosTiempo();
		crearTablaVentas();
	}
	
	/**
	 * Método para rellenar el gráfico de ventas por tiempo.
	 */
	private void crearGraficoIngresosTiempo(){
		ArrayList<EntradasTiempoDTO> listaTiempos = eventoDAO.getIngresosPorTiempo(1);
		entradasTiempo = new LineChartModel();
		Calendar c = Calendar.getInstance();
		
		LineChartSeries serieTipoEntrada = new LineChartSeries();
		serieTipoEntrada.setLabel("Recaudación (€)");
		serieTipoEntrada.setFill(true);
		
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
			
			serieTipoEntrada.set(f, e.getPrecio());
		}
		
		entradasTiempo.addSeries(serieTipoEntrada);
		//entradasTiempo.getAxis(AxisType.Y).setLabel("€ recaudados");
		entradasTiempo.setExtender("skinChart");
		
		DateAxis axis = new DateAxis("Fecha");
        axis.setTickAngle(-45);
        
        //Llegado el momento, aquí habrá que poner la fecha actual, ya que no deberían haber entradas vendidas con fecha futura
        axis.setMax("2016-09-01");
        
        axis.setTickFormat("%b %#d, %y");
        entradasTiempo.getAxes().put(AxisType.X, axis);
		
		//entradasTiempo.setTitle("Recaudación por días");
        entradasTiempo.setLegendPosition("s");
        Axis yAxis = entradasTiempo.getAxis(AxisType.Y);
        yAxis.setMin(0);
        
        //Llegado el momento, este número tendrá que ser calculado a partir del máximo valor de las series para conservar unas proporciones en el gráfico
        //que permitan verlo sin problemas.
        yAxis.setMax(1000);
		
		return;
	}
	
	private void crearTablaVentas() {
		listaVentas = new ArrayList<TipoVentaCompleto>();
		ArrayList<String> listaTipos = eventoDAO.getTiposEntradaByEvento(1);
		TipoVentaCompleto tipoEntrada = null;
		ArrayList<TipoVentaBasico> listaBasica = null;
		
		for(String s : listaTipos) {
			tipoEntrada = new TipoVentaCompleto();
			listaBasica = eventoDAO.getTiposVentaBasico(1, s);
			
			tipoEntrada.setVentas(listaBasica);
			tipoEntrada.setNombreTipoEntrada(s);
			
			float f = 0;
			for(TipoVentaBasico t : listaBasica) {
				f = f + (t.getPrecio() * (float) t.getCantidad());
			}
			tipoEntrada.setTotal(f);
			
			listaVentas.add(tipoEntrada);
		}
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
	 * @return the listaVentas
	 */
	public ArrayList<TipoVentaCompleto> getListaVentas() {
		return listaVentas;
	}

	/**
	 * @param listaVentas the listaVentas to set
	 */
	public void setListaVentas(ArrayList<TipoVentaCompleto> listaVentas) {
		this.listaVentas = listaVentas;
	}
}
