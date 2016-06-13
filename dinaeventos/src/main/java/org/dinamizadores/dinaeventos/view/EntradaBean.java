package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.dinamizadores.dinaeventos.model.Entrada;
import java.lang.Boolean;

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

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Entrada entrada;

	public Entrada getEntrada() {
		return this.entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "dinaeventos-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

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
	 * Support updating and deleting Entrada entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.entrada);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.entrada);
				return "view?faces-redirect=true&id="
						+ this.entrada.getIdentrada();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			Entrada deletableEntity = findById(getId());

			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
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

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Entrada> root = countCriteria.from(Entrada.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Entrada> criteria = builder.createQuery(Entrada.class);
		root = criteria.from(Entrada.class);
		TypedQuery<Entrada> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Entrada> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String numeroserie = this.example.getNumeroserie();
		if (numeroserie != null && !"".equals(numeroserie)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("numeroserie")),
					'%' + numeroserie.toLowerCase() + '%'));
		}
		Boolean validada = this.example.getValidada();
		if (validada != null) {
			predicatesList.add(builder.equal(root.get("validada"), validada));
		}
		Boolean ticketgenerado = this.example.getTicketgenerado();
		if (ticketgenerado != null) {
			predicatesList.add(builder.equal(root.get("ticketgenerado"),
					ticketgenerado));
		}
		Boolean activa = this.example.getActiva();
		if (activa != null) {
			predicatesList.add(builder.equal(root.get("activa"), activa));
		}
		Integer idevento = this.example.getIdevento();
		if (idevento != null && idevento.intValue() != 0) {
			predicatesList.add(builder.equal(root.get("idevento"), idevento));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
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

	public List<Entrada> getAll() {

		CriteriaQuery<Entrada> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Entrada.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Entrada.class))).getResultList();
	}

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
}
