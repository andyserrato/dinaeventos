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

import org.dinamizadores.dinaeventos.model.DdRrppJefeEntrada;

/**
 * Backing bean for DdRrppJefeEntrada entities.
 * <p/>
 * This class provides CRUD functionality for all DdRrppJefeEntrada entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DdRrppJefeEntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdRrppJefeEntrada entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private DdRrppJefeEntrada ddRrppJefeEntrada;

	public DdRrppJefeEntrada getDdRrppJefeEntrada() {
		return this.ddRrppJefeEntrada;
	}

	public void setDdRrppJefeEntrada(DdRrppJefeEntrada ddRrppJefeEntrada) {
		this.ddRrppJefeEntrada = ddRrppJefeEntrada;
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
			this.ddRrppJefeEntrada = this.example;
		} else {
			this.ddRrppJefeEntrada = findById(getId());
		}
	}

	public DdRrppJefeEntrada findById(Long id) {

		return this.entityManager.find(DdRrppJefeEntrada.class, id);
	}

	/*
	 * Support updating and deleting DdRrppJefeEntrada entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.ddRrppJefeEntrada);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.ddRrppJefeEntrada);
				return "view?faces-redirect=true&id="
						+ this.ddRrppJefeEntrada.getIdRrppJefeEntrada();
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
			DdRrppJefeEntrada deletableEntity = findById(getId());

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
	 * Support searching DdRrppJefeEntrada entities with pagination
	 */

	private int page;
	private long count;
	private List<DdRrppJefeEntrada> pageItems;

	private DdRrppJefeEntrada example = new DdRrppJefeEntrada();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public DdRrppJefeEntrada getExample() {
		return this.example;
	}

	public void setExample(DdRrppJefeEntrada example) {
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
		Root<DdRrppJefeEntrada> root = countCriteria
				.from(DdRrppJefeEntrada.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<DdRrppJefeEntrada> criteria = builder
				.createQuery(DdRrppJefeEntrada.class);
		root = criteria.from(DdRrppJefeEntrada.class);
		TypedQuery<DdRrppJefeEntrada> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<DdRrppJefeEntrada> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

//		Integer rrppMinionIdrrppMinion = this.example
//				.getRrppMinionIdrrppMinion();
//		if (rrppMinionIdrrppMinion != null
//				&& rrppMinionIdrrppMinion.intValue() != 0) {
//			predicatesList
//					.add(builder.equal(root.get("rrppMinionIdrrppMinion"),
//							rrppMinionIdrrppMinion));
//		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<DdRrppJefeEntrada> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back DdRrppJefeEntrada entities (e.g. from
	 * inside an HtmlSelectOneMenu)
	 */

	public List<DdRrppJefeEntrada> getAll() {

		CriteriaQuery<DdRrppJefeEntrada> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(DdRrppJefeEntrada.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(DdRrppJefeEntrada.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final DdRrppJefeEntradaBean ejbProxy = this.sessionContext
				.getBusinessObject(DdRrppJefeEntradaBean.class);

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

				return String.valueOf(((DdRrppJefeEntrada) value).getIdRrppJefeEntrada());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DdRrppJefeEntrada add = new DdRrppJefeEntrada();

	public DdRrppJefeEntrada getAdd() {
		return this.add;
	}

	public DdRrppJefeEntrada getAdded() {
		DdRrppJefeEntrada added = this.add;
		this.add = new DdRrppJefeEntrada();
		return added;
	}
}
