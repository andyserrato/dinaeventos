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

import org.dinamizadores.dinaeventos.model.Redessociales;

/**
 * Backing bean for Redessociales entities.
 * <p/>
 * This class provides CRUD functionality for all Redessociales entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class RedessocialesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Redessociales entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Redessociales redessociales;

	public Redessociales getRedessociales() {
		return this.redessociales;
	}

	public void setRedessociales(Redessociales redessociales) {
		this.redessociales = redessociales;
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
			this.redessociales = this.example;
		} else {
			this.redessociales = findById(getId());
		}
	}

	public Redessociales findById(Integer id) {

		return this.entityManager.find(Redessociales.class, id);
	}

	/*
	 * Support updating and deleting Redessociales entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.redessociales);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.redessociales);
				return "view?faces-redirect=true&id="
						+ this.redessociales.getIdredessociales();
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
			Redessociales deletableEntity = findById(getId());

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
	 * Support searching Redessociales entities with pagination
	 */

	private int page;
	private long count;
	private List<Redessociales> pageItems;

	private Redessociales example = new Redessociales();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Redessociales getExample() {
		return this.example;
	}

	public void setExample(Redessociales example) {
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
		Root<Redessociales> root = countCriteria.from(Redessociales.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Redessociales> criteria = builder
				.createQuery(Redessociales.class);
		root = criteria.from(Redessociales.class);
		TypedQuery<Redessociales> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Redessociales> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String enlace = this.example.getEnlacePerfil();
		if (enlace != null && !"".equals(enlace)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("enlace")),
					'%' + enlace.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Redessociales> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Redessociales entities (e.g. from inside
	 * an HtmlSelectOneMenu)
	 */

	public List<Redessociales> getAll() {

		CriteriaQuery<Redessociales> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Redessociales.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Redessociales.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final RedessocialesBean ejbProxy = this.sessionContext
				.getBusinessObject(RedessocialesBean.class);

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

				return String.valueOf(((Redessociales) value)
						.getIdredessociales());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Redessociales add = new Redessociales();

	public Redessociales getAdd() {
		return this.add;
	}

	public Redessociales getAdded() {
		Redessociales added = this.add;
		this.add = new Redessociales();
		return added;
	}
}
