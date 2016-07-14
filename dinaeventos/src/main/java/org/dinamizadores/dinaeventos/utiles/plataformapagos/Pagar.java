package org.dinamizadores.dinaeventos.utiles.plataformapagos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.dinamizadores.dinaeventos.model.Usuario;

import com.mangopay.MangoPayApi;
import com.mangopay.core.Address;
import com.mangopay.core.Money;
import com.mangopay.core.enumerations.CardType;
import com.mangopay.core.enumerations.CountryIso;
import com.mangopay.core.enumerations.CurrencyIso;
import com.mangopay.entities.Card;
import com.mangopay.entities.CardRegistration;
import com.mangopay.entities.PayIn;
import com.mangopay.entities.User;
import com.mangopay.entities.UserNatural;
import com.mangopay.entities.Wallet;
import com.mangopay.entities.subentities.PayInExecutionDetailsDirect;
import com.mangopay.entities.subentities.PayInPaymentDetailsCard;

public class Pagar {
	private MangoPayApi api;
	private CardRegistration tarjetaRegistrada;
	private Wallet cartera;
	private Card tarjeta;
	private PayIn pago;
	private BigDecimal total = new BigDecimal(0);

	public Pagar() {
		api = new MangoPayApi();
		// configuration
		api.Config.ClientId = "dinaevents1";
		api.Config.ClientPassword = "xRusxJ7jhO3ySPAZdwJGiiicfL2dozSNnbOoY5510bHVwt4Gvi";
		// Para cambiar a produccion cambiar esto.
		// api.Config.BaseUrl = "https://api.mangopay.com";

		try {

			// nuevoTarjeta();
			// User sam = api.Users.get("14371680");
			System.out.println("Estoy fuera");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String nuevoUsuario(Usuario miUser) {

		UserNatural sam = new UserNatural();
		User creado = null;

		sam.Tag = "usuario compra";
		sam.FirstName = miUser.getNombre();
		sam.LastName = miUser.getApellidos();
		// sam.Address = new Address();
		// sam.Address.AddressLine1 = miUser.getDireccion();
		// sam.Address.City = miUser.getCodigoPostal().getLocalidad();
		// sam.Address.PostalCode = miUser.getCodigoPostal().getCodigo();
		// sam.Address.Country = CountryIso.ES;
		sam.IncomeRange = 1;
		sam.Email = miUser.getEmail();

		Calendar c = Calendar.getInstance();
		c.setTime(miUser.getFechanac());
		c.set(Calendar.MILLISECOND, 0);

		sam.Birthday = c.getTimeInMillis() / 1000;
		sam.Nationality = CountryIso.ES;
		sam.CountryOfResidence = CountryIso.ES;

		try {
			creado = api.Users.create(sam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creado.Id;
	}

	public void nuevaWallet() {

		cartera = new Wallet();

		cartera.Tag = "custom meta";
		ArrayList<String> usuarios = new ArrayList<String>();
		usuarios.add(tarjeta.UserId);
		cartera.Owners = usuarios;
		cartera.Description = "Cartera de prueba";
		cartera.Currency = CurrencyIso.EUR;

		try {
			cartera = api.Wallets.create(cartera);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PayIn nuevoPago() {
		pago = new PayIn();

		pago.AuthorId = tarjetaRegistrada.UserId;

		pago.CreditedWalletId = "14373138";
		pago.CreditedUserId = "14372969";
		// Tipo de pago
		PayInPaymentDetailsCard s = new PayInPaymentDetailsCard();
		s.CardType = CardType.CB_VISA_MASTERCARD;
		pago.PaymentDetails = s;

		pago.DebitedFunds = new Money();
		pago.DebitedFunds.Currency = CurrencyIso.EUR;

		pago.DebitedFunds.Amount = total.multiply(new BigDecimal(100)).intValue();

		// Para desviar la cantidad de dinero que quiero a otra cuenta
		pago.Fees = new Money();
		pago.Fees.Currency = CurrencyIso.EUR;
		pago.Fees.Amount = 0;

		// Ejecución del pago Directa
		PayInExecutionDetailsDirect p = new PayInExecutionDetailsDirect();
		p.CardId = tarjeta.Id;
		p.SecureModeReturnURL = "http://localhost:8080/dinaeventos/faces/comprar/finalizarPago.xhtml";
		pago.ExecutionDetails = p;

		try {

			api.PayIns.create(pago);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pago;

	}

	public CardRegistration nuevoTarjeta(String idUsuario) {

		tarjetaRegistrada = new CardRegistration();
		CardRegistration nuevaTarjeta = new CardRegistration();

		System.out.println("El user " + idUsuario);
		tarjetaRegistrada.UserId = idUsuario;
		tarjetaRegistrada.Currency = CurrencyIso.EUR;
		tarjetaRegistrada.CardType = CardType.CB_VISA_MASTERCARD;

		try {
			nuevaTarjeta = api.CardRegistrations.create(tarjetaRegistrada);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nuevaTarjeta;
	}

	public PayIn actualizarTarjeta(CardRegistration tarjetaRegi) {
		PayIn resultadoPago = null;
		try {

			tarjetaRegistrada = new CardRegistration();

			tarjetaRegistrada = api.CardRegistrations.get(tarjetaRegi.Id);

			tarjetaRegistrada.RegistrationData = "data=" + tarjetaRegi.RegistrationData;
			System.out.println("Estoy dentro2 " + tarjetaRegistrada.RegistrationData);

			CardRegistration updateTarjeta = api.CardRegistrations.update(tarjetaRegistrada);
			System.out.println("Estoy dentro " + updateTarjeta.CardId);

			// Nos traemos la tarjeta registrada
			tarjeta = api.Cards.get(updateTarjeta.CardId);
			resultadoPago = crearPago();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultadoPago;
	}

	public PayIn crearPago() {
		PayIn resultadoPago = null;

		try {

			// crear monedero virtual temporal
			nuevaWallet();

			// Crear nuevo pago
			resultadoPago = nuevoPago();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultadoPago;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
