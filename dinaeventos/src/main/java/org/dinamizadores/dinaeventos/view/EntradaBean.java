package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Entrada;

/**
 * Backing bean for Entrada entities.
 * <p/>
 * This class provides CRUD functionality for all Entrada entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class EntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Entrada entities
	 */

	private Integer id;
	
	private Integer cantidad;

	private Entrada entrada;
	
	@EJB
	private EntradaDao tipoEntradaDao; 
	
	private List<DdTipoEntrada> tiposEntrada;

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "dinaeventos-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	
	public void init() {
		
		this.tiposEntrada = tipoEntradaDao.listTipoEntrada();
	}
	
	
	public String create() {

		this.conversation.begin();
		this.conversation.setTimeout(1800000L);
		return "create?faces-redirect=true";
	}

	public void retrieve() {

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}

		if (this.id == null) {
			this.entrada = this.example;
		} else {
			this.entrada = findById(getId());
		}
	}

	public Entrada findById(Integer id) {

		return this.entityManager.find(Entrada.class, id);
	}


	/*
	 * Support searching Entrada entities with pagination
	 */

	private int page;
	private long count;
	private List<Entrada> pageItems;
	

	private Entrada example = new Entrada();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Entrada getExample() {
		return this.example;
	}

	public void setExample(Entrada example) {
		this.example = example;
	}

	public String search() {
		this.page = 0;
		return null;
	}


	public List<Entrada> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Entrada entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */


	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final EntradaBean ejbProxy = this.sessionContext
				.getBusinessObject(EntradaBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findById(Integer.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((Entrada) value).getIdentrada());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Entrada add = new Entrada();

	public Entrada getAdd() {
		return this.add;
	}

	public Entrada getAdded() {
		Entrada added = this.add;
		this.add = new Entrada();
		return added;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Entrada getEntrada() {
		return this.entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public EntradaDao getTipoEntrada() {
		return tipoEntradaDao;
	}

	public void setTipoEntrada(EntradaDao tipoEntrada) {
		this.tipoEntradaDao = tipoEntrada;
	}

	public List<DdTipoEntrada> getTiposEntrada() {
		return tiposEntrada;
	}

	public void setTiposEntrada(List<DdTipoEntrada> tiposEntrada) {
		this.tiposEntrada = tiposEntrada;
	}
}
