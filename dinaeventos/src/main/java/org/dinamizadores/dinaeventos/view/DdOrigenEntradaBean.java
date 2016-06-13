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

import org.dinamizadores.dinaeventos.model.DdOrigenEntrada;

/**
 * Backing bean for DdOrigenEntrada entities.
 * <p/>
 * This class provides CRUD functionality for all DdOrigenEntrada entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DdOrigenEntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdOrigenEntrada entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private DdOrigenEntrada ddOrigenEntrada;

	public DdOrigenEntrada getDdOrigenEntrada() {
		return this.ddOrigenEntrada;
	}

	public void setDdOrigenEntrada(DdOrigenEntrada ddOrigenEntrada) {
		this.ddOrigenEntrada = ddOrigenEntrada;
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
			this.ddOrigenEntrada = this.example;
		} else {
			this.ddOrigenEntrada = findById(getId());
		}
	}

	public DdOrigenEntrada findById(Integer id) {

		return this.entityManager.find(DdOrigenEntrada.class, id);
	}

	/*
	 * Support updating and deleting DdOrigenEntrada entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.ddOrigenEntrada);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.ddOrigenEntrada);
				return "view?faces-redirect=true&id="
						+ this.ddOrigenEntrada.getIdorigenEntrada();
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
			DdOrigenEntrada deletableEntity = findById(getId());

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
	 * Support searching DdOrigenEntrada entities with pagination
	 */

	private int page;
	private long count;
	private List<DdOrigenEntrada> pageItems;

	private DdOrigenEntrada example = new DdOrigenEntrada();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public DdOrigenEntrada getExample() {
		return this.example;
	}

	public void setExample(DdOrigenEntrada example) {
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
		Root<DdOrigenEntrada> root = countCriteria.from(DdOrigenEntrada.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<DdOrigenEntrada> criteria = builder
				.createQuery(DdOrigenEntrada.class);
		root = criteria.from(DdOrigenEntrada.class);
		TypedQuery<DdOrigenEntrada> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<DdOrigenEntrada> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String nombre = this.example.getNombre();
		if (nombre != null && !"".equals(nombre)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("nombre")),
					'%' + nombre.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<DdOrigenEntrada> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back DdOrigenEntrada entities (e.g. from
	 * inside an HtmlSelectOneMenu)
	 */

	public List<DdOrigenEntrada> getAll() {

		CriteriaQuery<DdOrigenEntrada> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(DdOrigenEntrada.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(DdOrigenEntrada.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final DdOrigenEntradaBean ejbProxy = this.sessionContext
				.getBusinessObject(DdOrigenEntradaBean.class);

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

				return String.valueOf(((DdOrigenEntrada) value)
						.getIdorigenEntrada());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DdOrigenEntrada add = new DdOrigenEntrada();

	public DdOrigenEntrada getAdd() {
		return this.add;
	}

	public DdOrigenEntrada getAdded() {
		DdOrigenEntrada added = this.add;
		this.add = new DdOrigenEntrada();
		return added;
	}
}
