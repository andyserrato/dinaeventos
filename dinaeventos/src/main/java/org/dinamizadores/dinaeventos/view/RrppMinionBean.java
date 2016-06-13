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

import org.dinamizadores.dinaeventos.model.RrppMinion;

/**
 * Backing bean for RrppMinion entities.
 * <p/>
 * This class provides CRUD functionality for all RrppMinion entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class RrppMinionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving RrppMinion entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private RrppMinion rrppMinion;

	public RrppMinion getRrppMinion() {
		return this.rrppMinion;
	}

	public void setRrppMinion(RrppMinion rrppMinion) {
		this.rrppMinion = rrppMinion;
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
			this.rrppMinion = this.example;
		} else {
			this.rrppMinion = findById(getId());
		}
	}

	public RrppMinion findById(Integer id) {

		return this.entityManager.find(RrppMinion.class, id);
	}

	/*
	 * Support updating and deleting RrppMinion entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.rrppMinion);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.rrppMinion);
				return "view?faces-redirect=true&id="
						+ this.rrppMinion.getIdrrppMinion();
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
			RrppMinion deletableEntity = findById(getId());

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
	 * Support searching RrppMinion entities with pagination
	 */

	private int page;
	private long count;
	private List<RrppMinion> pageItems;

	private RrppMinion example = new RrppMinion();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public RrppMinion getExample() {
		return this.example;
	}

	public void setExample(RrppMinion example) {
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
		Root<RrppMinion> root = countCriteria.from(RrppMinion.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<RrppMinion> criteria = builder
				.createQuery(RrppMinion.class);
		root = criteria.from(RrppMinion.class);
		TypedQuery<RrppMinion> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<RrppMinion> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String nombre = this.example.getNombre();
		if (nombre != null && !"".equals(nombre)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("nombre")),
					'%' + nombre.toLowerCase() + '%'));
		}
		String apellido1 = this.example.getApellido1();
		if (apellido1 != null && !"".equals(apellido1)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("apellido1")),
					'%' + apellido1.toLowerCase() + '%'));
		}
		String apellido2 = this.example.getApellido2();
		if (apellido2 != null && !"".equals(apellido2)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("apellido2")),
					'%' + apellido2.toLowerCase() + '%'));
		}
		String email = this.example.getEmail();
		if (email != null && !"".equals(email)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("email")),
					'%' + email.toLowerCase() + '%'));
		}
		String telefono = this.example.getTelefono();
		if (telefono != null && !"".equals(telefono)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("telefono")),
					'%' + telefono.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<RrppMinion> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back RrppMinion entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<RrppMinion> getAll() {

		CriteriaQuery<RrppMinion> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(RrppMinion.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(RrppMinion.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final RrppMinionBean ejbProxy = this.sessionContext
				.getBusinessObject(RrppMinionBean.class);

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

				return String.valueOf(((RrppMinion) value).getIdrrppMinion());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private RrppMinion add = new RrppMinion();

	public RrppMinion getAdd() {
		return this.add;
	}

	public RrppMinion getAdded() {
		RrppMinion added = this.add;
		this.add = new RrppMinion();
		return added;
	}
}
