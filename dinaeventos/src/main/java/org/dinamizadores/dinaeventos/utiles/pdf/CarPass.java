package org.dinamizadores.dinaeventos.utiles.pdf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koen on 9-06-14.
 */
public class CarPass {

    private String vin;
    private String address;
    private String certificateNumber;
    private String make;
    private String price;
    private String firstUse;
    private String firstRegistrationBe;
    private String validUntil;
    private String odometerType;
    private String message;
    private List<OdometerReading> odometerReadings;
    private String prueba;

    public CarPass() {
        this.vin = "VF154PB5VXA1138BB";
        this.certificateNumber ="0782-7851-9342";
        this.make = "Renault Megane";
        this.price = "6,00 EUR";
        this.firstUse = "22/12/1981";
        this.firstRegistrationBe = "22/12/1981";
        this.validUntil = "01/02/2007";
        this.odometerType = "Km";
        this.prueba = "HOLA";
    }

    public String getVin() {
        return vin;
    }

    public String getAddress() {
        return address;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public String getMake() {
        return make;
    }

    public String getPrice() {
        return price;
    }

    public String getFirstUse() {
        return firstUse;
    }

    public String getFirstRegistrationBe() {
        return firstRegistrationBe;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public String getOdometerType() {
        return odometerType;
    }

    public String getMessage() {
        return message;
    }

    public List<OdometerReading> getOdometerReadings() {
        if ( odometerReadings == null ) {
            odometerReadings = new ArrayList<>();
            odometerReadings.add ( new OdometerReading( "01/12/2006", "185643" ) );
            odometerReadings.add ( new OdometerReading( "16/04/2006", "173459" ) );
            odometerReadings.add ( new OdometerReading( "11/06/2005", "143459" ) );
            odometerReadings.add ( new OdometerReading( "23/01/2005", "134521" ) );
            odometerReadings.add ( new OdometerReading( "05/07/2004", "123451" ) );
            odometerReadings.add ( new OdometerReading( "25/02/2004", "100030" ) );
            odometerReadings.add ( new OdometerReading( "12/11/2003", "96372" ) );
            odometerReadings.add ( new OdometerReading( "26/03/2003", "92091" ) );
            odometerReadings.add ( new OdometerReading( "22/08/2003", "87643" ) );
            odometerReadings.add ( new OdometerReading( "14/01/2002", "76382" ) );
            odometerReadings.add ( new OdometerReading( "24/12/2001", "62782" ) );
            odometerReadings.add ( new OdometerReading( "17/02/2001", "52928" ) );
            odometerReadings.add ( new OdometerReading( "16/09/2000", "54628" ) );
            odometerReadings.add ( new OdometerReading( "03/04/2000", "52928" ) );
            odometerReadings.add ( new OdometerReading( "19/03/1999", "32000" ) );
        }
        return odometerReadings;
    }

    public String getPrueba() {
		return prueba;
	}


	public class OdometerReading  {

        private String date;
        private String value;

        public OdometerReading(String date, String value) {
            this.date = date;
            this.value = value;
        }

        public String getDate() {
            return date;
        }

        public String getValue() {
            return value;
        }
    }


}

