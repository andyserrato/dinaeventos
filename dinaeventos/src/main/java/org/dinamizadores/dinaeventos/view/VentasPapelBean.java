package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dto.EntradasTiempoDTO;
import org.dinamizadores.dinaeventos.dto.EventoDTO;
import org.dinamizadores.dinaeventos.dto.TipoVentaBasico;
import org.dinamizadores.dinaeventos.dto.TipoVentaCompleto;
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
@Named("ventasPapel")
@ViewScoped
public class VentasPapelBean implements Serializable {

	/** UID. */
	private static final long serialVersionUID = -2744835737744313163L;

	/** LoginBean inyectado para poder recoger datos globales de la aplicación. */
	@Inject
	private LoginBean loginBean;
	
	/** Objeto modelador que define el gráfico de ventas por sexo. */
	private PieChartModel quesoSexual;
	
	/** Objeto modelador que define el gráfico de ventas por rango de edad. */
	private PieChartModel quesoEdad;
	
	/** Objeto DTO que contiene datos relativos al Evento. */
	private EventoDTO evento;
	
	/** Objeto EJB para comunicarse con la BBDD. */
	@EJB
	private EventoDao eventoDAO;
	
	/** Objeto modelador que define el gráfico de cantidad de entradas vendidas por fecha. */
	private LineChartModel entradasTiempo;
	
	/** Lista desglosada por tipos de ventas con la cantidad ingresada para cada uno. */
	private ArrayList<TipoVentaCompleto> listaVentas;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {
		//Posteriormente ese 1 'hardcodeado' se sacará del loginBean
		evento = eventoDAO.getVentasPapel(1);
		
		if(evento != null) {
		crearModeloSexual();
		crearModeloEdades();
		crearGraficoIngresosTiempo();
		crearTablaVentas();
		}
	}
	
	/**
	 * Método para rellenar el gráfico de venta por sexos.
	 */
	private void crearModeloSexual(){
		quesoSexual = new PieChartModel();
		
		//quesoSexual.setTitle("Distribución por Sexo");
		quesoSexual.set("Hombre", evento.getNumHombres());
		quesoSexual.set("Mujer", evento.getNumMujeres());
		quesoSexual.setLegendPosition("w");
		quesoSexual.setExtender("skinPie");

		return;
	}
	
	private void crearModeloEdades(){
		quesoEdad = new PieChartModel();
		
		//quesoEdad.setTitle("Distribución por edades");
		quesoEdad.set("18 y 19", eventoDAO.getNumeroVentasPapelPorRangoEdad(1, 18, 19));
		quesoEdad.set("20 y 21", eventoDAO.getNumeroVentasPapelPorRangoEdad(1, 20, 21));
		quesoEdad.set("22 - 24", eventoDAO.getNumeroVentasPapelPorRangoEdad(1, 22, 24));
		quesoEdad.set("> 25", eventoDAO.getNumeroVentasPapelPorRangoEdad(1, 25, 100));
		quesoEdad.setLegendPosition("w");
		quesoEdad.setExtender("skinPie");
	}
	
	private void crearTablaVentas() {
		listaVentas = new ArrayList<TipoVentaCompleto>();
		ArrayList<String> listaTipos = eventoDAO.getTiposEntradaByEvento(1);
		TipoVentaCompleto tipoEntrada = null;
		ArrayList<TipoVentaBasico> listaBasica = null;
		
		for(String s : listaTipos) {
			tipoEntrada = new TipoVentaCompleto();
			listaBasica = eventoDAO.getTiposVentaPapelBasico(1, s);
			
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
	 * Método para rellenar el gráfico de ventas por tiempo.
	 */
	private void crearGraficoIngresosTiempo(){
		ArrayList<EntradasTiempoDTO> listaTiempos = eventoDAO.getIngresosPapelPorTiempo(1);
		entradasTiempo = new LineChartModel();
		Calendar c = Calendar.getInstance();
		
		LineChartSeries serieTipoEntrada = new LineChartSeries();
		serieTipoEntrada.setLabel("Recaudación");
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
		
		entradasTiempo.getAxis(AxisType.Y).setLabel("€ recaudados");
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
	
	/**
	 * @param loginBean the loginBean to set
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
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
