public class Employe 
{
	private int index;
	private String nom;
	private String prenom;
	private double salaire;
	
	public Employe(int p_index, String p_nom, String p_prenom, double p_salaire)
	{
		setIndex(p_index);
		setNom(p_nom);
		setPrenom(p_prenom);
		setSalaire(p_salaire);
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int p_index)
	{
		if (validerIndex(p_index))
		index = p_index;
	}
	
	public String getNom()
	{
		return nom;
	}
	
	public void setNom(String p_nom)
	{
		if (validerNom(p_nom))
		{
			nom = p_nom;
		}
	}
	
	public static boolean validerNom(String p_nom)
	{
		return p_nom.length() >= 2; 
	}
	
	public String getPrenom()
	{
		return prenom;
	}
	
	public void setPrenom(String p_prenom)
	{
		if (validerPrenom(p_prenom))
		{
			prenom = p_prenom;
		}
	}
	
	public static boolean validerPrenom(String p_prenom)
	{
		return p_prenom.length() >= 2; 
	}
	
	public double getSalaire()
	{
		return salaire;
	}

	public void setSalaire(double p_salaire)
	{
		if(validerSalaire(p_salaire))
		{
			salaire = p_salaire;
		}
	}
	
	public static boolean validerSalaire(double p_salaire)
	{
		return p_salaire >= 0;
	}
	
	public static boolean validerIndex(int p_index)
	{
		return p_index > 0;
	}
}
