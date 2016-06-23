package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.model.DdFormapago;
import org.dinamizadores.dinaeventos.utiles.BBDDFaker;

/**
 * Backing bean for DdFormapago entities.
 * <p/>
 * This class provides CRUD functionality for all DdFormapago entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DdFormapagoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdFormapago entities
	 */

	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private DdFormapago ddFormapago;

	public DdFormapago getDdFormapago() {
		return this.ddFormapago;
	}

	public void setDdFormapago(DdFormapago ddFormapago) {
		this.ddFormapago = ddFormapago;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "dinaeventos-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public void init(){
		 DAOGenerico dao;
		 BBDDFaker bd;
		
		dao = new DAOGenerico();
		bd = new BBDDFaker();
		
		DdFormapago p = bd.crearFormaPago();
		
		if(p == null){
			System.out.println("fuck");
		} else{
		
			try {
				dao.insertar(p);
			} catch (Exception e) {
				System.out.println("Pues YOLO bitches!");
				e.printStackTrace();
			}
		}
	}
	
	public String create() {

		this.conversation.begin();
		this.conversation.setTimeout(1800000L);
		 init();
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
			this.ddFormapago = this.example;
		} else {
			this.ddFormapago = findById(getId());
		}
	}

	public DdFormapago findById(Integer id) {

		return this.entityManager.find(DdFormapago.class, id);
	}

	/*
	 * Support updating and deleting DdFormapago entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.ddFormapago);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.ddFormapago);
				return "view?faces-redirect=true&id="
						+ this.ddFormapago.getIdformapago();
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
			DdFormapago deletableEntity = findById(getId());

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
	 * Support searching DdFormapago entities with pagination
	 */

	private int page;
	private long count;
	private List<DdFormapago> pageItems;

	private DdFormapago example = new DdFormapago();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public DdFormapago getExample() {
		return this.example;
	}

	public void setExample(DdFormapago example) {
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
		Root<DdFormapago> root = countCriteria.from(DdFormapago.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<DdFormapago> criteria = builder
				.createQuery(DdFormapago.class);
		root = criteria.from(DdFormapago.class);
		TypedQuery<DdFormapago> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<DdFormapago> root) {

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

	public List<DdFormapago> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back DdFormapago entities (e.g. from inside
	 * an HtmlSelectOneMenu)
	 */

	public List<DdFormapago> getAll() {

		CriteriaQuery<DdFormapago> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(DdFormapago.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(DdFormapago.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final DdFormapagoBean ejbProxy = this.sessionContext
				.getBusinessObject(DdFormapagoBean.class);

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

				return String.valueOf(((DdFormapago) value).getIdformapago());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DdFormapago add = new DdFormapago();

	public DdFormapago getAdd() {
		return this.add;
	}

	public DdFormapago getAdded() {
		DdFormapago added = this.add;
		this.add = new DdFormapago();
		return added;
	}
}
