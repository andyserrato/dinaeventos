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

import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;

/**
 * Backing bean for GlobalCodigopostales entities.
 * <p/>
 * This class provides CRUD functionality for all GlobalCodigopostales entities.
 * It focuses purely on Java EE 6 standards (e.g.
 * <tt>&#64;ConversationScoped</tt> for state management,
 * <tt>PersistenceContext</tt> for persistence, <tt>CriteriaBuilder</tt> for
 * searches) rather than introducing a CRUD framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class GlobalCodigopostalesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving GlobalCodigopostales entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private GlobalCodigospostales globalCodigopostales;

	public GlobalCodigospostales getGlobalCodigopostales() {
		return this.globalCodigopostales;
	}

	public void setGlobalCodigopostales(
			GlobalCodigospostales globalCodigopostales) {
		this.globalCodigopostales = globalCodigopostales;
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
			this.globalCodigopostales = this.example;
		} else {
			this.globalCodigopostales = findById(getId());
		}
	}

	public GlobalCodigospostales findById(Integer id) {

		return this.entityManager.find(GlobalCodigospostales.class, id);
	}

	/*
	 * Support updating and deleting GlobalCodigopostales entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.globalCodigopostales);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.globalCodigopostales);
				return "view?faces-redirect=true&id="
						+ this.globalCodigopostales.getIdcodigopostal();
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
			GlobalCodigospostales deletableEntity = findById(getId());

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
	 * Support searching GlobalCodigopostales entities with pagination
	 */

	private int page;
	private long count;
	private List<GlobalCodigospostales> pageItems;

	private GlobalCodigospostales example = new GlobalCodigospostales();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public GlobalCodigospostales getExample() {
		return this.example;
	}

	public void setExample(GlobalCodigospostales example) {
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
		Root<GlobalCodigospostales> root = countCriteria
				.from(GlobalCodigospostales.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<GlobalCodigospostales> criteria = builder
				.createQuery(GlobalCodigospostales.class);
		root = criteria.from(GlobalCodigospostales.class);
		TypedQuery<GlobalCodigospostales> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<GlobalCodigospostales> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String codigo = this.example.getCodigo();
		if (codigo != null && !"".equals(codigo)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("codigo")),
					'%' + codigo.toLowerCase() + '%'));
		}
		String localidad = this.example.getLocalidad();
		if (localidad != null && !"".equals(localidad)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("localidad")),
					'%' + localidad.toLowerCase() + '%'));
		}
		Integer idprovincia = this.example.getIdprovincia();
		if (idprovincia != null && idprovincia.intValue() != 0) {
			predicatesList.add(builder.equal(root.get("idprovincia"),
					idprovincia));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<GlobalCodigospostales> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back GlobalCodigopostales entities (e.g. from
	 * inside an HtmlSelectOneMenu)
	 */

	public List<GlobalCodigospostales> getAll() {

		CriteriaQuery<GlobalCodigospostales> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(GlobalCodigospostales.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(GlobalCodigospostales.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final GlobalCodigopostalesBean ejbProxy = this.sessionContext
				.getBusinessObject(GlobalCodigopostalesBean.class);

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

				return String.valueOf(((GlobalCodigospostales) value)
						.getIdcodigopostal());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private GlobalCodigospostales add = new GlobalCodigospostales();

	public GlobalCodigospostales getAdd() {
		return this.add;
	}

	public GlobalCodigospostales getAdded() {
		GlobalCodigospostales added = this.add;
		this.add = new GlobalCodigospostales();
		return added;
	}
}
