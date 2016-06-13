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

import org.dinamizadores.dinaeventos.model.DdRrppEvento;

/**
 * Backing bean for DdRrppEvento entities.
 * <p/>
 * This class provides CRUD functionality for all DdRrppEvento entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DdRrppEventoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdRrppEvento entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private DdRrppEvento ddRrppEvento;

	public DdRrppEvento getDdRrppEvento() {
		return this.ddRrppEvento;
	}

	public void setDdRrppEvento(DdRrppEvento ddRrppEvento) {
		this.ddRrppEvento = ddRrppEvento;
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
			this.ddRrppEvento = this.example;
		} else {
			this.ddRrppEvento = findById(getId());
		}
	}

	public DdRrppEvento findById(Long id) {

		return this.entityManager.find(DdRrppEvento.class, id);
	}

	/*
	 * Support updating and deleting DdRrppEvento entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.ddRrppEvento);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.ddRrppEvento);
				return "view?faces-redirect=true&id="
						+ this.ddRrppEvento.getId();
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
			DdRrppEvento deletableEntity = findById(getId());

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
	 * Support searching DdRrppEvento entities with pagination
	 */

	private int page;
	private long count;
	private List<DdRrppEvento> pageItems;

	private DdRrppEvento example = new DdRrppEvento();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public DdRrppEvento getExample() {
		return this.example;
	}

	public void setExample(DdRrppEvento example) {
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
		Root<DdRrppEvento> root = countCriteria.from(DdRrppEvento.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<DdRrppEvento> criteria = builder
				.createQuery(DdRrppEvento.class);
		root = criteria.from(DdRrppEvento.class);
		TypedQuery<DdRrppEvento> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<DdRrppEvento> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<DdRrppEvento> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back DdRrppEvento entities (e.g. from inside
	 * an HtmlSelectOneMenu)
	 */

	public List<DdRrppEvento> getAll() {

		CriteriaQuery<DdRrppEvento> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(DdRrppEvento.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(DdRrppEvento.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final DdRrppEventoBean ejbProxy = this.sessionContext
				.getBusinessObject(DdRrppEventoBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((DdRrppEvento) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DdRrppEvento add = new DdRrppEvento();

	public DdRrppEvento getAdd() {
		return this.add;
	}

	public DdRrppEvento getAdded() {
		DdRrppEvento added = this.add;
		this.add = new DdRrppEvento();
		return added;
	}
}
