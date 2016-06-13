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

import org.dinamizadores.dinaeventos.model.Patrocinadores;

/**
 * Backing bean for Patrocinadores entities.
 * <p/>
 * This class provides CRUD functionality for all Patrocinadores entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class PatrocinadoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Patrocinadores entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Patrocinadores patrocinadores;

	public Patrocinadores getPatrocinadores() {
		return this.patrocinadores;
	}

	public void setPatrocinadores(Patrocinadores patrocinadores) {
		this.patrocinadores = patrocinadores;
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
			this.patrocinadores = this.example;
		} else {
			this.patrocinadores = findById(getId());
		}
	}

	public Patrocinadores findById(Integer id) {

		return this.entityManager.find(Patrocinadores.class, id);
	}

	/*
	 * Support updating and deleting Patrocinadores entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.patrocinadores);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.patrocinadores);
				return "view?faces-redirect=true&id="
						+ this.patrocinadores.getIdpatrocinador();
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
			Patrocinadores deletableEntity = findById(getId());

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
	 * Support searching Patrocinadores entities with pagination
	 */

	private int page;
	private long count;
	private List<Patrocinadores> pageItems;

	private Patrocinadores example = new Patrocinadores();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Patrocinadores getExample() {
		return this.example;
	}

	public void setExample(Patrocinadores example) {
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
		Root<Patrocinadores> root = countCriteria.from(Patrocinadores.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Patrocinadores> criteria = builder
				.createQuery(Patrocinadores.class);
		root = criteria.from(Patrocinadores.class);
		TypedQuery<Patrocinadores> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Patrocinadores> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String nombre = this.example.getNombre();
		if (nombre != null && !"".equals(nombre)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("nombre")),
					'%' + nombre.toLowerCase() + '%'));
		}
		String direccion = this.example.getDireccion();
		if (direccion != null && !"".equals(direccion)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("direccion")),
					'%' + direccion.toLowerCase() + '%'));
		}
		String telefono = this.example.getTelefono();
		if (telefono != null && !"".equals(telefono)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("telefono")),
					'%' + telefono.toLowerCase() + '%'));
		}
		String email = this.example.getEmail();
		if (email != null && !"".equals(email)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("email")),
					'%' + email.toLowerCase() + '%'));
		}
		String cuentacorriente = this.example.getCuentacorriente();
		if (cuentacorriente != null && !"".equals(cuentacorriente)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("cuentacorriente")),
					'%' + cuentacorriente.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Patrocinadores> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Patrocinadores entities (e.g. from
	 * inside an HtmlSelectOneMenu)
	 */

	public List<Patrocinadores> getAll() {

		CriteriaQuery<Patrocinadores> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Patrocinadores.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Patrocinadores.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final PatrocinadoresBean ejbProxy = this.sessionContext
				.getBusinessObject(PatrocinadoresBean.class);

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

				return String.valueOf(((Patrocinadores) value)
						.getIdpatrocinador());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Patrocinadores add = new Patrocinadores();

	public Patrocinadores getAdd() {
		return this.add;
	}

	public Patrocinadores getAdded() {
		Patrocinadores added = this.add;
		this.add = new Patrocinadores();
		return added;
	}
}
