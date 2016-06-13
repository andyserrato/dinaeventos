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

import org.dinamizadores.dinaeventos.model.DdTiposIva;

/**
 * Backing bean for DdTiposIva entities.
 * <p/>
 * This class provides CRUD functionality for all DdTiposIva entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DdTiposIvaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdTiposIva entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private DdTiposIva ddTiposIva;

	public DdTiposIva getDdTiposIva() {
		return this.ddTiposIva;
	}

	public void setDdTiposIva(DdTiposIva ddTiposIva) {
		this.ddTiposIva = ddTiposIva;
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
			this.ddTiposIva = this.example;
		} else {
			this.ddTiposIva = findById(getId());
		}
	}

	public DdTiposIva findById(Integer id) {

		return this.entityManager.find(DdTiposIva.class, id);
	}

	/*
	 * Support updating and deleting DdTiposIva entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.ddTiposIva);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.ddTiposIva);
				return "view?faces-redirect=true&id="
						+ this.ddTiposIva.getIdtipoiva();
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
			DdTiposIva deletableEntity = findById(getId());

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
	 * Support searching DdTiposIva entities with pagination
	 */

	private int page;
	private long count;
	private List<DdTiposIva> pageItems;

	private DdTiposIva example = new DdTiposIva();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public DdTiposIva getExample() {
		return this.example;
	}

	public void setExample(DdTiposIva example) {
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
		Root<DdTiposIva> root = countCriteria.from(DdTiposIva.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<DdTiposIva> criteria = builder
				.createQuery(DdTiposIva.class);
		root = criteria.from(DdTiposIva.class);
		TypedQuery<DdTiposIva> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<DdTiposIva> root) {

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

	public List<DdTiposIva> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back DdTiposIva entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<DdTiposIva> getAll() {

		CriteriaQuery<DdTiposIva> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(DdTiposIva.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(DdTiposIva.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final DdTiposIvaBean ejbProxy = this.sessionContext
				.getBusinessObject(DdTiposIvaBean.class);

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

				return String.valueOf(((DdTiposIva) value).getIdtipoiva());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DdTiposIva add = new DdTiposIva();

	public DdTiposIva getAdd() {
		return this.add;
	}

	public DdTiposIva getAdded() {
		DdTiposIva added = this.add;
		this.add = new DdTiposIva();
		return added;
	}
}
