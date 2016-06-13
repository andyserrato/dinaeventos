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

import org.dinamizadores.dinaeventos.model.DdTipoEvento;

/**
 * Backing bean for DdTipoEvento entities.
 * <p/>
 * This class provides CRUD functionality for all DdTipoEvento entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DdTipoEventoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdTipoEvento entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private DdTipoEvento ddTipoEvento;

	public DdTipoEvento getDdTipoEvento() {
		return this.ddTipoEvento;
	}

	public void setDdTipoEvento(DdTipoEvento ddTipoEvento) {
		this.ddTipoEvento = ddTipoEvento;
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
			this.ddTipoEvento = this.example;
		} else {
			this.ddTipoEvento = findById(getId());
		}
	}

	public DdTipoEvento findById(Integer id) {

		return this.entityManager.find(DdTipoEvento.class, id);
	}

	/*
	 * Support updating and deleting DdTipoEvento entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.ddTipoEvento);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.ddTipoEvento);
				return "view?faces-redirect=true&id="
						+ this.ddTipoEvento.getIdTipoEvento();
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
			DdTipoEvento deletableEntity = findById(getId());

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
	 * Support searching DdTipoEvento entities with pagination
	 */

	private int page;
	private long count;
	private List<DdTipoEvento> pageItems;

	private DdTipoEvento example = new DdTipoEvento();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public DdTipoEvento getExample() {
		return this.example;
	}

	public void setExample(DdTipoEvento example) {
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
		Root<DdTipoEvento> root = countCriteria.from(DdTipoEvento.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<DdTipoEvento> criteria = builder
				.createQuery(DdTipoEvento.class);
		root = criteria.from(DdTipoEvento.class);
		TypedQuery<DdTipoEvento> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<DdTipoEvento> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String tipoEvento = this.example.getTipoEvento();
		if (tipoEvento != null && !"".equals(tipoEvento)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("tipoEvento")),
					'%' + tipoEvento.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<DdTipoEvento> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back DdTipoEvento entities (e.g. from inside
	 * an HtmlSelectOneMenu)
	 */

	public List<DdTipoEvento> getAll() {

		CriteriaQuery<DdTipoEvento> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(DdTipoEvento.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(DdTipoEvento.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final DdTipoEventoBean ejbProxy = this.sessionContext
				.getBusinessObject(DdTipoEventoBean.class);

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

				return String.valueOf(((DdTipoEvento) value).getIdTipoEvento());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DdTipoEvento add = new DdTipoEvento();

	public DdTipoEvento getAdd() {
		return this.add;
	}

	public DdTipoEvento getAdded() {
		DdTipoEvento added = this.add;
		this.add = new DdTipoEvento();
		return added;
	}
}
