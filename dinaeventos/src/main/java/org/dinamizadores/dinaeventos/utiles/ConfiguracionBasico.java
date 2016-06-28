package org.dinamizadores.dinaeventos.utiles;
 
import java.io.Serializable;
 
public class ConfiguracionBasico implements Serializable {
 
    /** UID. */
    private static final long serialVersionUID = -3325221099164627624L;
 
    /** Dirección de email del servicio SMTP. */
    private String emailSMTP;
 
    /** Dirección de red del servidor SMTP. */
    private String serverSMTP;
 
    /** Nombre del usuario que gestiona el servicio SMTP. */
    private String userSMTP;
 
    /** Contraseña del usuario que gestiona el servicio SMTP. */
    private String passwordSMTP;
 
    /** Lista de emails con copia oculta para cada email enviado. */
    private String emailsCopiaOculta;
 
    /** Número de días para la edición de la sesión. */
    private int diasEdicionSesion;
 
    /** Número de días para la edición del comunicado. */
    private int diasEdicionComunicado;
 
    /**
     * Constructor por defecto.
     */
    public ConfiguracionBasico() {
 
    }
 
    public String getEmailSMTP() {
        return emailSMTP;
    }
 
    public void setEmailSMTP(String emailSMTP) {
        this.emailSMTP = emailSMTP;
    }
 
    public String getServerSMTP() {
        return serverSMTP;
    }
 
    public void setServerSMTP(String serverSMTP) {
        this.serverSMTP = serverSMTP;
    }
 
    public String getUserSMTP() {
        return userSMTP;
    }
 
    public void setUserSMTP(String userSMTP) {
        this.userSMTP = userSMTP;
    }
 
    public String getPasswordSMTP() {
        return passwordSMTP;
    }
 
    public void setPasswordSMTP(String passwordSMTP) {
        this.passwordSMTP = passwordSMTP;
    }
 
    public String getEmailsCopiaOculta() {
        return emailsCopiaOculta;
    }
 
    public void setEmailsCopiaOculta(String emailsCopiaOculta) {
        this.emailsCopiaOculta = emailsCopiaOculta;
    }
 
    public int getDiasEdicionSesion() {
        return diasEdicionSesion;
    }
 
    public void setDiasEdicionSesion(int diasEdicionSesion) {
        this.diasEdicionSesion = diasEdicionSesion;
    }
 
    public int getDiasEdicionComunicado() {
        return diasEdicionComunicado;
    }
 
    public void setDiasEdicionComunicado(int diasEdicionComunicado) {
        this.diasEdicionComunicado = diasEdicionComunicado;
    }
 
}