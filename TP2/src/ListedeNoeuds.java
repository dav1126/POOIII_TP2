public class ListedeNoeuds 
{
	protected NoeudListe tete = null;
	
	public ListedeNoeuds()
	{
		this.tete = null;
	}
	
	public boolean estVide()
	{
		return this.tete == null;
	}
	
	public void ajouterElement(Object o, int pos)
	{
		NoeudListe courant = null;
		
		if (pos == 1)
		{
			ajouterDebut(o);
		}
		
		else
		{
			courant = getNoeud(pos-1);
			
			if (courant != null)
			{
				courant.setSuivant(new NoeudListe(o, courant.getSuivant()));
			}
			else
			{
				throw new IndexOutOfBoundsException();
			}
		}
	}
	
	public void ajouterDebut(Object o)
	{
		this.tete = new NoeudListe(o, this.tete);
	}
	
	protected NoeudListe getNoeud(int pos)
	{
		NoeudListe retour = null;
		NoeudListe courant =  null;
		int i =1;
		
		if ((!this.estVide()) && (pos > 0))
		{
			courant = this.tete;
		}
		
		while ((courant.getSuivant() != null) && (i < pos))
		{
			courant = courant.getSuivant();
			i++;
		}
		
		if (i == pos)
		{
			retour = courant;
		}
		
		return retour;
	}
	
	public int getTaille()
	{
		int compteur = 0;
		
		if (!this.estVide())
		{
			NoeudListe courant  = this.tete;
			compteur = 1;
			
			while (courant.getSuivant() != null)
			{
				courant = courant.getSuivant();
				compteur++;
			}
		}
		
		return compteur;
	}
	
	public int getPosition(Employe emp)
	{
		int pos = -1;
		int i = 1;
				
		if (!this.estVide())
		{
			NoeudListe courant = this.tete;
			Employe empTemp = (Employe)courant.getElement();
			
			while ((courant.getSuivant() != null) && !(empTemp.getNom().equals(emp.getNom().toUpperCase()) && empTemp.getPrenom().equals(emp.getPrenom().toUpperCase())))
			{	
				courant = courant.getSuivant();
				empTemp = (Employe)courant.getElement();
				i++;
			}
			
			if (empTemp.getNom().equals(emp.getNom().toUpperCase()) && empTemp.getPrenom().equals(emp.getPrenom().toUpperCase()))
			{
				pos = i;
			}
		}
		else
		{
			throw new RuntimeException();
		}
		
		return pos;
	}
	
	public Object enleverDebut()
	{
		Object retour = null;
		
		if (!this.estVide())
		{
			retour = this.tete.getElement();
			this.tete = this.tete.getSuivant();
		}
		else
		{
			throw new RuntimeException();
		}
		
		return retour;
	}
	
	public Object enleverFin()
	{
		Object retour = null;
		NoeudListe courant = null;
		
		if (!this.estVide())
		{	
			if(this.tete.getSuivant() == null)
			{
				retour = this.tete.getElement();
				this.tete = null;
			}
			
			else
			{
				retour = this.getNoeud(getTaille()).getElement();		
				courant = this.getNoeud(getTaille()-1);
				courant.setSuivant(null);
			}
		}
		else
		{
			throw new RuntimeException();
		}
		
		return retour;
	}
	
	public boolean existe(Employe emp)
	{
		boolean empExiste = false;					
		NoeudListe courant = this.tete;
		Employe empTemp = null;
						
			while (courant != null)
			{
				empTemp = (Employe) courant.getElement();
				if(empTemp.getNom().equals(emp.getNom().toUpperCase()) && empTemp.getPrenom().equals(emp.getPrenom().toUpperCase()))
				{
					empExiste = true;
				}
				courant = courant.getSuivant();
			}
		return empExiste;
	}
	
	protected double getMoyenneSal()
	{
		double somme = 0;
		double moyenne = 0;
		NoeudListe courant = this.tete;
		Employe empTemp = null;

		while (courant != null)
		{
			empTemp = (Employe) courant.getElement();
			somme += empTemp.getSalaire();	
			courant = courant.getSuivant();
		}
			
		if (!this.estVide())
		{
			moyenne = somme/this.getTaille();
		}
		
		return moyenne;
	}
	
	protected double getMaxSal()
	{
		double maxSalaire = 0;
		NoeudListe courant = this.tete;
		Employe empTemp = null;
		
			while (courant != null)
			{
				empTemp = (Employe) courant.getElement();
				if(maxSalaire < empTemp.getSalaire())
				{
					maxSalaire = empTemp.getSalaire();
				}
				courant = courant.getSuivant();
			}
			
		return maxSalaire;
	}
	
	protected double getMinSal()
	{
		double minSalaire = 999999999;
		NoeudListe courant = this.tete;
		Employe empTemp = null;
			
		while (courant != null)
		{
			empTemp = (Employe) courant.getElement();
			if(minSalaire > empTemp.getSalaire())
			{
				minSalaire = empTemp.getSalaire();
			}
			courant = courant.getSuivant();
		}
				
			return minSalaire;
	}
	
	protected int getIndexMax()
	{
		int indexMax = 0;
		
		NoeudListe courant = this.tete;
		Employe empTemp = null;
			
		while (courant != null)
		{
			empTemp = (Employe)courant.getElement();
			if(indexMax < empTemp.getIndex())
			{
				indexMax = empTemp.getIndex();
			}
			courant = courant.getSuivant();
		}
				
			return indexMax;
	}
	
	protected void supprimerNoeud(int pos)
	{
		NoeudListe courant = this.tete;
				
		if(pos == 1)
		{
			enleverDebut();
		}
		
		else if (pos == this.getTaille())
		{
			enleverFin();
		}
		
		else
		{
			courant = getNoeud(pos-1);
			courant.setSuivant(courant.getSuivant().getSuivant());
		}	
	}

}
