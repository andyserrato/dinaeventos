package org.dinamizadores.dinaeventos.view.valenciaConnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dinamizadores.dinaeventos.dao.EntradaDao;

/**
 * Servlet implementation class PayPalIPN
 */
@WebServlet("/PayPalIPN")
public class PayPalIPN extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private static final String SANDBOX_URL = "https://www.sandbox.paypal.com/cgi-bin/webscr";
	private static final String PRODUCTION_URL = "https://www.paypal.com/cgi-bin/webscr";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayPalIPN() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    @Inject
    EntradaDao entradaDao;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("cmd", "_notify-validate");
		ArrayList<String> numerosSerie = new ArrayList<>();
		
		
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();
			String value = request.getParameter(name);
			params.put(name, value);
			
			if (name.contains("item_number") && value.contains("ns:"))
				numerosSerie.add(value.substring(3, value.length()));
			
			System.out.println(name + ":" + value);
		}
		
		//Recuperamos los datos 
		String transactionID = request.getParameter("txn_id");
		String paymentStatus = request.getParameter("payment_status");
		String clientAccountStatus = request.getParameter("payer_status");
		
		//Verificamos que los 3 campos tengan valor
		if (transactionID != null && paymentStatus != null && clientAccountStatus != null){
			//Verificamos que el estado del pago sea correcto y que el comprado tenga su cuenta verificada
			if (transaccionValida(paymentStatus, clientAccountStatus)){
				//Aquí enviamos los datos a paypal informando que hemos recibido la notificación
				enviarPeticion(params);
				//Enviamos las entradas en el caso de que la entrada no tenga asociada una transacción
				
			}
		}
		
		
		System.out.println();
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	private String enviarPeticion(Map<String, Object> params) throws IOException{
		StringBuilder postData = new StringBuilder();
		URL url = new URL(SANDBOX_URL);
		
		String charset = (String)params.get("charset");
		
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), charset));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), charset));
		}
		
		byte[] postDataBytes = postData.toString().getBytes(charset);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
		String inputLine;
		StringBuffer response = new StringBuffer();

		// TODO [EQUIPO] alerta de chapuza mejorarla
		while ((inputLine = in.readLine()) != null) {
			System.out.println("INPUT LINE:" + inputLine);
		}
		
		return "";
	}
	
	private boolean transaccionValida(String paymentStatus, String clientAccountStatus){
		boolean resultado = false;
		
		if (paymentStatus.equals("Completed") && clientAccountStatus.equals("verified"))
			resultado = true;
		
		return resultado;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
