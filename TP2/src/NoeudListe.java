
/**
 * Classe NoeudListe :noeud de la liste des employés
 * 
  * 
 */
public class NoeudListe {

	private Object element;
	private NoeudListe suivant;// Un pointeur sur le noeud suivant

	/**
	 * Constructeur de noeud
	 * 
	 * @param pElement
	 *            :l'élement a la ajouter dans le noeud
	 * @param pSuivant
	 *            :le noeud suivant
	 */
	public NoeudListe(Object pElement, NoeudListe pSuivant) {
		this.setElement(pElement);
		this.setSuivant(pSuivant);
	}

	/**
	 * obtenir l'element du noeud
	 * 
	 * @return l'element du noeud
	 */
	public Object getElement() {
		return element;
	}

	/**
	 * obtenir le noeud suivant
	 * 
	 * @return le noeud suivant
	 */
	public NoeudListe getSuivant() {
		return suivant;
	}

	/**
	 * Mettre à jour l'élément dans le noeud
	 * 
	 * @param pElement
	 *            : l'élément a mettre dans le noeud
	 */
	public void setElement(Object pElement) {
		this.element = pElement;
	}

	/**
	 * Mettre à jour le noeud suivant
	 * 
	 * @param pSuivant
	 *            : le noeud suivant
	 */
	public void setSuivant(NoeudListe pSuivant) {
		suivant = pSuivant;
	}
}
