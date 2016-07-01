package org.dinamizadores.dinaeventos.utiles.plataformapagos;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.zugferd.checkers.basic.CountryCode;
import com.mangopay.MangoPayApi;
import com.mangopay.core.Address;
import com.mangopay.core.Money;
import com.mangopay.core.Pagination;
import com.mangopay.core.enumerations.CardType;
import com.mangopay.core.enumerations.CountryIso;
import com.mangopay.core.enumerations.CultureCode;
import com.mangopay.core.enumerations.CurrencyIso;
import com.mangopay.entities.BankAccount;
import com.mangopay.entities.CardRegistration;
import com.mangopay.entities.PayIn;
import com.mangopay.entities.User;
import com.mangopay.entities.UserNatural;
import com.mangopay.entities.Wallet;
import com.mangopay.entities.subentities.PayInExecutionDetailsWeb;
import com.mangopay.entities.subentities.PayInPaymentDetailsCard;

public class pagar {
	private MangoPayApi api;
	
	public pagar(){
		api = new MangoPayApi();
		// configuration
		api.Config.ClientId = "dinaevents1";
		api.Config.ClientPassword = "xRusxJ7jhO3ySPAZdwJGiiicfL2dozSNnbOoY5510bHVwt4Gvi";
		//Para cambiar a produccion cambiar esto.
		//api.Config.BaseUrl = "https://api.mangopay.com";
	
		try {
			//Crear un usuario nuevo
			//nuevoUsuario();
			//Crear una nueva wallet para el usuario
			//nuevaWallet();
			//Crear nuevo pago sin tarjeta de credito
			nuevoPago();
			User sam = api.Users.get("14371680");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void configurarPago (){
	
	

	// call some API methods...
	try {
		List<User> users = api.Users.getAll();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	public void pagoSimple(){

	// get some user by id
	User john;
		try {
			john = api.Users.get("82");
		
	
		// change and update some of his data
		john.Tag += " - CHANGED";
		api.Users.update(john);
	
		// get all users (with pagination)
		Pagination pagination = new Pagination(1, 8); // get 1st page, 8 items per page
		List<User> users = api.Users.getAll(pagination, null);
	
		// get his bank accounts
		pagination = new Pagination(2, 10); // get 2nd page, 10 items per page
		List<BankAccount> accounts = api.Users.getBankAccounts(john.Id, pagination,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void nuevoUsuario (){
		
		UserNatural sam = new UserNatural();
		
		sam.Tag = "custom meta";
		sam.FirstName = "adminWallet";
		sam.LastName = "lozano";
		sam.Address = new Address();
		sam.Address.AddressLine1 = "antonio aparisi";
		sam.Address.City = "valencia";
		sam.Address.PostalCode = "46920";
		sam.Address.Country = CountryIso.ES;
		sam.IncomeRange = 1;
		sam.Email = "saloza@irtic.uv.es";
		sam.Birthday = 24091990;
		sam.Nationality = CountryIso.ES;
		sam.CountryOfResidence = CountryIso.ES;
		sam.Occupation = "ingeniero";

		try {
			api.Users.create(sam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void nuevaWallet (){
		Wallet cartera = new Wallet();
		
		cartera.Tag = "custom meta";
		ArrayList<String> usuarios = new ArrayList<String>();
		usuarios.add("14372969");
		cartera.Owners = usuarios;
		cartera.Description = "Cartera de prueba";
		cartera.Currency = CurrencyIso.EUR;
		
		try {
			api.Wallets.create(cartera);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void nuevoPago(){
		
		PayIn pago = new PayIn();
		
		pago.AuthorId = "14372969";
		
		PayInPaymentDetailsCard s = new PayInPaymentDetailsCard();
		s.CardType = CardType.CB_VISA_MASTERCARD;
		
		pago.PaymentDetails = s;
		
		
		pago.DebitedFunds = new Money();
		pago.DebitedFunds.Currency = CurrencyIso.EUR;
		pago.DebitedFunds.Amount = 1200;
		
		//Para desviar la cantidad de dinero que quiero a otra cuenta
		pago.Fees = new Money();
		pago.Fees.Currency = CurrencyIso.EUR;
		pago.Fees.Amount = 0;
		
		PayInExecutionDetailsWeb p = new PayInExecutionDetailsWeb();
		p.Culture = CultureCode.ES;
		p.ReturnURL ="http://localhost:8080/dinaeventos/faces/comprar/ok.xhtml";
		
		
		pago.ExecutionDetails = p;
		
		pago.CreditedWalletId = "14373138";
		
		try {
			
			api.PayIns.create(pago);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
