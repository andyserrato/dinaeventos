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

import org.dinamizadores.dinaeventos.model.RrppJefes;

/**
 * Backing bean for RrppJefes entities.
 * <p/>
 * This class provides CRUD functionality for all RrppJefes entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class RrppJefesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving RrppJefes entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private RrppJefes rrppJefes;

	public RrppJefes getRrppJefes() {
		return this.rrppJefes;
	}

	public void setRrppJefes(RrppJefes rrppJefes) {
		this.rrppJefes = rrppJefes;
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
			this.rrppJefes = this.example;
		} else {
			this.rrppJefes = findById(getId());
		}
	}

	public RrppJefes findById(Integer id) {

		return this.entityManager.find(RrppJefes.class, id);
	}

	/*
	 * Support updating and deleting RrppJefes entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.rrppJefes);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.rrppJefes);
				return "view?faces-redirect=true&id="
						+ this.rrppJefes.getIdrrppJefe();
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
			RrppJefes deletableEntity = findById(getId());

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
	 * Support searching RrppJefes entities with pagination
	 */

	private int page;
	private long count;
	private List<RrppJefes> pageItems;

	private RrppJefes example = new RrppJefes();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public RrppJefes getExample() {
		return this.example;
	}

	public void setExample(RrppJefes example) {
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
		Root<RrppJefes> root = countCriteria.from(RrppJefes.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<RrppJefes> criteria = builder
				.createQuery(RrppJefes.class);
		root = criteria.from(RrppJefes.class);
		TypedQuery<RrppJefes> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<RrppJefes> root) {

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

	public List<RrppJefes> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back RrppJefes entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<RrppJefes> getAll() {

		CriteriaQuery<RrppJefes> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(RrppJefes.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(RrppJefes.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final RrppJefesBean ejbProxy = this.sessionContext
				.getBusinessObject(RrppJefesBean.class);

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

				return String.valueOf(((RrppJefes) value).getIdrrppJefe());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private RrppJefes add = new RrppJefes();

	public RrppJefes getAdd() {
		return this.add;
	}

	public RrppJefes getAdded() {
		RrppJefes added = this.add;
		this.add = new RrppJefes();
		return added;
	}
}
