package org.dinamizadores.dinaeventos.dto;
import java.io.Serializable;
 
/**
 * Clase que gestiona la información de email.
 * 
 * @author LISITT
 */
public class EmailBasico implements Serializable {
 
    /** UID. */
	private static final long serialVersionUID = 3674879582525913121L;

	/** Título del email. */
    protected String titulo;
 
    /** Texto del email. */
    protected String texto;
 
    /** Apellidos y nombre del usuario. */
    protected String nombreUsuario;
 
    /** Apellidos y nombre del profesional. */
    protected String nombreProfesional;
 
    /** Email receptor. */
    protected String mailReceptor;
 
    /** Email segundo receptor. */
    protected String mailSegundoReceptor;
 
    /** Ente del paciente. */
    protected int idEnte;
 
    /** Ente del profesional. */
    protected Integer idProfesional;
 
    /** Password del colectivo para los PDF. */
    protected String passwordpdf;
 
    /**
     * Constructor por defecto.
     */
    public EmailBasico() {}
 
    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }
 
    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
 
    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }
 
    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
 
    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
 
    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
 
    /**
     * @return the nombreProfesional
     */
    public String getNombreProfesional() {
        return nombreProfesional;
    }
 
    /**
     * @param nombreProfesional the nombreProfesional to set
     */
    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
    }
 
    /**
     * @return the mailReceptor
     */
    public String getMailReceptor() {
        return mailReceptor;
    }
 
    /**
     * @param mailReceptor the mailReceptor to set
     */
    public void setMailReceptor(String mailReceptor) {
        this.mailReceptor = mailReceptor;
    }
 
    /**
     * @return the mailSegundoReceptor
     */
    public String getMailSegundoReceptor() {
        return mailSegundoReceptor;
    }
 
    /**
     * @param mailSegundoReceptor the mailSegundoReceptor to set
     */
    public void setMailSegundoReceptor(String mailSegundoReceptor) {
        this.mailSegundoReceptor = mailSegundoReceptor;
    }
 
    /**
     * @return the idEnte
     */
    public int getIdEnte() {
        return idEnte;
    }
 
    /**
     * @param idEnte the idEnte to set
     */
    public void setIdEnte(int idEnte) {
        this.idEnte = idEnte;
    }
 
    /**
     * @return the passwordpdf
     */
    public String getPasswordpdf() {
        return passwordpdf;
    }
 
    /**
     * @param passwordpdf the passwordpdf to set
     */
    public void setPasswordpdf(String passwordpdf) {
        this.passwordpdf = passwordpdf;
    }
 
    /**
     * @return the idProfesional
     */
    public Integer getIdProfesional() {
        return idProfesional;
    }
 
    /**
     * @param idProfesional the idProfesional to set
     */
    public void setIdProfesional(Integer idProfesional) {
        this.idProfesional = idProfesional;
    }
 
}