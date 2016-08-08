package org.dinamizadores.dinaeventos.view.valenciaConnect;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PayPalIPN
 */
@WebServlet("/PayPalIPN")
public class PayPalIPN extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayPalIPN() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();
			String value = request.getParameter(name);
			System.out.println(name + ":" + value);
		}
		
		String transactionID = request.getParameter("txn_id");
		String paymentStatus = request.getParameter("payment_status");
		String clientAccountStatus = request.getParameter("payer_status");
		String idEntradas[] = request.getParameter("item_number1").split(",");
		
		//Los 4 valores campos valor
		if (transactionID != null && paymentStatus != null && clientAccountStatus != null && idEntradas != null){
			//Verificamos que el estado del pago sea correcto y que el comprado tenga su cuenta verificada
			if (transaccionValida(paymentStatus, clientAccountStatus)){
				//Aquí enviamos los datos a paypal informando que hemos recibido la notificación
				
				
			}
		}
		
		
		System.out.println();
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
