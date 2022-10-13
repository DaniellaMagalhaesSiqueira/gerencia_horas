package gerencia.modelo;

public class DAO <E>{
//
//	private static EntityManagerFactory emf;
//	private EntityManager em;
//	private Class<E> classe;
//	
//	static {
//		try {
//			emf = Persistence.createEntityManagerFactory("gerencia");
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	public DAO() {
//		this(null);
//	}
//	
//	public DAO(Class<E> classe) {
//		this.classe = classe;
//		em = emf.createEntityManager();
//	}
//	
//	public DAO<E> abrirTransacao(){
//		em.getTransaction().begin();
//		return this;
//	}
//	
//	public DAO<E> fecharTransacao(){
//		em.getTransaction().commit();
//		return this;
//	}
//	
//	public DAO<E> incluir(E entidade){
//		em.persist(entidade);	
//		return this;
//	}
//	public DAO<E> transacaoAtomica(E entidade){
//		return this.abrirTransacao().incluir(entidade).fecharTransacao();
//	}
//	public List<E> obterTodos(int qtd, int deslocamento){
//		if(classe == null) {
//			throw new UnsupportedOperationException();
//		}
//		
//		String jpql = "select e from "+ classe.getName() + " e";
//		TypedQuery<E> query = em.createQuery(jpql, classe);
//		query.setMaxResults(qtd);
//		query.setFirstResult(deslocamento);
//		return query.getResultList();
//	}
//	public List<E> obterTodos(){
//		return this.obterTodos(50,0);
//	}
//	
//	public DAO<E> fecharDAO() {
//		if(em.getTransaction().isActive()) {			
//			em.getTransaction().commit();
//		}
//		return this;
//	}
}
