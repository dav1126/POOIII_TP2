import java.awt.Dimension;
import java.io.File;
import java.text.ParseException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Principale extends Application
{
	ListedeNoeuds empListe;
	Scene scene;
	Pane root;
	
	Label labelTitre;
	Label labelNom;
	Label labelPrenom;
	Label labelSalaire;
	Label labelIndex;
	
	TextField textFieldNom;
	TextField textFieldPrenom;
	TextField textFieldSalaire;
	TextField textFieldIndex;
	
	Button bouttonAjouter;
	Button bouttonConsulter;
	Button bouttonModifSalaire;
	Button bouttonSupprimer;
	Button bouttonInfo;
	Button bouttonEnregistrer;
	Button bouttonReinitial;
	Button bouttonQuitter;
	
	//Variable booleene qui indique si la sauvegarde de la liste a �t� effectu�e. 
	//Utilis�e pour faire afficher un avertissement si la liste n'est pas sauvegard�e lorsque l'utilisateur quitte.
	boolean listeSauvegardee = true;
	
	public void iniChamps()
	{
		
	}
	
	
	/**
	 * Vider les champs
	 */
	public void resetChamps() 
	{
		textFieldNom.setText("");
		textFieldPrenom.setText("");
		textFieldSalaire.setText("");
		textFieldIndex.setText("");
	}

	/**
	 * cr�ation de l'interface graphique
	 */
	public void menu() {
		//mettre le contenu du fichier dans une liste
		uploaderActuel();
		
		// Cr�ation de l'interface graphique		
		labelTitre = new Label("Remplir le formulaire et s�lectionner\nl'option d�sir�e");
		labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		labelTitre.setTranslateX(55);
		labelTitre.setTranslateY(10);
		labelTitre.setTextAlignment(TextAlignment.CENTER);	
		
		labelNom = new Label("Entrer le nom de l'employ�:");
		labelNom.setTranslateX(25);
		labelNom.setTranslateY(60);
		
		labelPrenom = new Label("Entrer le prenom de l'employ�:");
		labelPrenom.setTranslateX(25);
		labelPrenom.setTranslateY(120);
		
		labelSalaire = new Label("Entrer le salaire de l'employ�:");
		labelSalaire.setTranslateX(25);
		labelSalaire.setTranslateY(180);
		
		labelIndex = new Label("Entrer l'index de l'employ�:");
		labelIndex.setTranslateX(25);
		labelIndex.setTranslateY(240);
		
		textFieldNom = new TextField();
		textFieldNom.setPrefWidth(260);
		textFieldNom.setTranslateX(70);
		textFieldNom.setTranslateY(85);
		
		textFieldPrenom = new TextField();
		textFieldPrenom.setPrefWidth(260);
		textFieldPrenom.setTranslateX(70);
		textFieldPrenom.setTranslateY(145);
		
		textFieldSalaire = new TextField();
		textFieldSalaire.setPrefWidth(260);
		textFieldSalaire.setTranslateX(70);
		textFieldSalaire.setTranslateY(205);
		
		textFieldIndex = new TextField();
		textFieldIndex.setPrefWidth(260);
		textFieldIndex.setTranslateX(70);
		textFieldIndex.setTranslateY(265);
		
		bouttonAjouter = new Button("Ajouter l'employ�");
		bouttonAjouter.setTranslateX(25);
		bouttonAjouter.setTranslateY(310);
		bouttonAjouter.setPrefSize(350, 30);
		
		bouttonConsulter = new Button("Consulter un employ�");
		bouttonConsulter.setTranslateX(25);
		bouttonConsulter.setTranslateY(345);
		bouttonConsulter.setPrefSize(350, 30);
		
		bouttonModifSalaire = new Button("Modifier le salaire d'un employ�");
		bouttonModifSalaire.setTranslateX(25);
		bouttonModifSalaire.setTranslateY(380);
		bouttonModifSalaire.setPrefSize(350, 30);
		
		bouttonSupprimer = new Button("Supprimer l'employ�");
		bouttonSupprimer.setTranslateX(25);
		bouttonSupprimer.setTranslateY(415);
		bouttonSupprimer.setPrefSize(350, 30);
		
		bouttonInfo = new Button("Obtenir de l'information sur les salaires");
		bouttonInfo.setTranslateX(25);
		bouttonInfo.setTranslateY(450);
		bouttonInfo.setPrefSize(350, 30);
		
		bouttonEnregistrer = new Button("Enregistrer la liste dans un fichier Excel");
		bouttonEnregistrer.setTranslateX(25);
		bouttonEnregistrer.setTranslateY(485);
		bouttonEnregistrer.setPrefSize(350, 30);
		
		bouttonReinitial = new Button("R�initialiser les champs");
		bouttonReinitial.setTranslateX(25);
		bouttonReinitial.setTranslateY(520);
		bouttonReinitial.setPrefSize(350, 30);
		
		bouttonQuitter = new Button("Quitter");
		bouttonQuitter.setTranslateX(25);
		bouttonQuitter.setTranslateY(555);
		bouttonQuitter.setPrefSize(350, 30);
		
		root.getChildren().addAll(labelTitre, labelNom, textFieldNom, labelPrenom, textFieldPrenom, 
				labelSalaire, textFieldSalaire, labelIndex, textFieldIndex, bouttonAjouter, bouttonConsulter, 
				bouttonModifSalaire, bouttonSupprimer, bouttonInfo, bouttonEnregistrer, bouttonReinitial, bouttonQuitter);
	}

	
	/**
	 * D�clenche l'ajout de l'employ� si tout est valide
	 * On s'assure que l'employ� n'existe pas d�j� et que les champs sont valides.
	 * Si l'utilisateur tente d'ins�rer un nouvel employ� parmis les autres en sp�cifiant un index, 
	 * on l'averti que cela produira un d�calage des index de certains autres employ�s.
	 */
	public void ajouterMethode() 
	{
		//Cr�ation de l'employ� si les champs n�cessaires sont remplis et valides
		if (validerChamps() && !textFieldNom.getText().trim().isEmpty() && !textFieldPrenom.getText().trim().isEmpty() && !textFieldSalaire.getText().trim().isEmpty())
		{
			//Si l'utilisateur sp�cifie un index, on l'utilise pour cr�er le nouvel employ�
			int index = 0;
			if (!textFieldIndex.getText().equals(""))
			{
				index = Integer.parseInt(textFieldIndex.getText());
			}
			//Sinon, on prend l'index qui suit dans la liste
			else
			{
				index = empListe.getIndexMax()+1;
			}
			
			String nom = textFieldNom.getText().toUpperCase();
			String prenom = textFieldPrenom.getText().toUpperCase();
			Double salaire = Double.parseDouble(textFieldSalaire.getText());
							
			Employe emp = new Employe(index, nom, prenom, salaire);
		
		
			//Si l'employ� n'existe pas d�j�
			if (validerAjouter(emp))
			{
				//Si la liste n'est pas vide et que l'utilisateur a sp�cifi� un index
				if (!empListe.estVide() && !textFieldIndex.getText().equals(""))
				{
					//Si l'index sp�cifi� est sup�rieur � l'index maximum observ� dans la liste
					if (emp.getIndex() > empListe.getIndexMax())
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Ajouter employe");
						alert.setHeaderText(null);
						alert.setContentText("Pour ins�rer une employ� � un index sp�cifique dans la liste, utiliser un index inf�rieur ou �gal � " + empListe.getIndexMax() + ".\nSi vous voulez simplement ajouter un employ� � la suite de la liste, ne sp�cifiez aucun index." );
					}
					//Si l'index sp�cifi� est inf�rieur � l'index max observ� dans la liste, avertir l'utilisateur qu'une insertion aura lieu parmis la liste
					else
					{
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Ajouter employ�");
						alert.setHeaderText(null);
						alert.setContentText("Attention!\n Ins�rer un employ� en sp�cifiant un index entrainerat un d�calage des index d'employ�s \nexistant qui poss�de un index �gal ou sup�rieur � celui du nouvel employ�!\n Voulez-vous ajouter l'employ� tout de m�me?");
						
						ButtonType yes = new ButtonType("Oui");
						ButtonType no = new ButtonType("Non");
						
						
						alert.getButtonTypes().setAll(yes, no);
						Optional <ButtonType> result = alert.showAndWait();
						
						//Si l'utilisateur accepte d'ins�rer un employ� parmis les autres, on ajoute l'employ�
						if (result.get() == yes)
						{
							empListe.ajouterElement(emp, emp.getIndex());
							listeSauvegardee = false;//changer l'indicateur bool�an qui indique si les modifs ont �t� enregistr�es
							
							Alert alertOK = new Alert(AlertType.INFORMATION);
							alertOK.setTitle("Ajouter employ�");
							alertOK.setHeaderText(null);
							alertOK.setContentText("Ajout effectu�. Les index seront remis � jour lors du prochain d�marrage du programme");
							alertOK.showAndWait();
							
							resetChamps();
						}		
					}
				}
				
				//Si aucun index n'est sp�cifi�, on ajoute l'employ�
				else
				{
					empListe.ajouterElement(emp, emp.getIndex());
					listeSauvegardee = false;//changer l'indicateur bool�an qui indique si les modifs ont �t� enregistr�es
					
					resetChamps();
					
					Alert alertOK = new Alert(AlertType.INFORMATION);
					alertOK.setTitle("Ajouter employ�");
					alertOK.setHeaderText(null);
					alertOK.setContentText("Ajout effectu�");
					alertOK.showAndWait();
				}	
			}
			
			//Si l'employ� existe d�j�
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Ajouter employ�");
				alert.setHeaderText(null);
				alert.setContentText("�chec de l'ajout.\nCet employ� existe d�j�!");
				alert.showAndWait();
			}
		}
		//Si les champs ne sont pas valides
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Ajouter employ�");
			alert.setHeaderText(null);
			alert.setContentText("�chec de l'ajout.\nLes informations nom, pr�nom salaire et index(optionnel) sont n�cessaires, au format suivant:\n"
					+ "Nom et pr�nom: Texte d'au moins 2 caract�res\nSalaire: Nombre >= 0\nIndex: Nombre entier >0 et <=" +(empListe.getIndexMax()+1));
			alert.showAndWait();
		}
	}
	
	//V�rifie si l'employ� existe d�j� dans la liste
	private boolean validerAjouter(Employe empTemp)
	{
		boolean ok = true;
		
		if (!empListe.estVide() && empListe.existe(empTemp))
		{
			ok = false;
		}
		
		return ok;
	}

	/**
	 * d�clenche la modification du salaire de l'employ� si tout est valide
	 * On trouve l'employ� � modifier selon les nom et pr�nom OU selon l'index sp�cifi�
	 * On affiche ensuite les infos de l'employ�s et on demande de confirmer la modification du salaire
	 */
	public void modifierMethode() {
		
		String index = textFieldIndex.getText();
		String nom = textFieldNom.getText().toUpperCase();
		String prenom = textFieldPrenom.getText().toUpperCase();
		String salaire = textFieldSalaire.getText();
			
		//Si les champs de nom, pr�nom et salaire sont remplis et valides
		if(validerChamps() && !nom.equals("") && !prenom.equals("") && !salaire.equals(""))
		{	
			Employe empTemp = new Employe(1, nom, prenom, Double.parseDouble(salaire)); //L'index de 1 est attribu� apr d�faut pour construire l'employ�, il n'est pas utilis�
			
			//Si l'employ� existe dans la liste (comparaison bas�e sur le nom et prenom)
			if (empListe.existe(empTemp))
			{
				//Trouver la position de l'employ� recherch�(recherche selon nom et pr�nom)
				int indexTemp = empListe.getPosition(empTemp);
				//Cr�er un employ� poss�dant les attributs de l'employ� trouv� � la position
				Employe empRecherche = (Employe)empListe.getNoeud(indexTemp).getElement();
				
				//Afficher les infos de l'employ� et demander de confirmer la modification
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Modification du salaire");
				alert.setHeaderText(null);
				alert.setContentText("Employ�: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment modifier le salaire de l'employ� pour" +salaire+" ?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empRecherche.setSalaire(Double.parseDouble(salaire));
					listeSauvegardee = false;//changer l'indicateur bool�an qui indique si les modifs ont �t� enregistr�es
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Modification du salaire");
					alert2.setHeaderText(null);
					alert2.setContentText("Modification du salaire r�ussie");
					alert2.showAndWait();
				}	
			}
			
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Modification du salaire");
				alert.setHeaderText(null);
				alert.setContentText("�chec de la consultation.\nL'employ� correspondant � ces nom et pr�nom n'existe pas");
				alert.showAndWait();
			}
		}
		
		//Sinon si les champs index et salaire sont remplis et valides
		else if (validerChamps() && !index.equals("") && !salaire.equals(""))
		{
			//Si l'index sp�cifi� existe
			if(Double.parseDouble(index) <= empListe.getIndexMax())
			{
				//Cr�er un employ� poss�dant les attributs de l'employ� trouv� � la position de l'index
				Employe empRecherche = (Employe)empListe.getNoeud(Integer.parseInt(index)).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Modification du salaire");
				alert.setContentText("Employ�: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment modifier le salaire de l'employ� pour" +salaire+" ?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empRecherche.setSalaire(Double.parseDouble(salaire));
					listeSauvegardee = false;//changer l'indicateur bool�an qui indique si les modifs ont �t� enregistr�es 
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Modification du salaire");
					alert2.setHeaderText(null);
					alert2.setContentText("Modification du salaire r�ussie");
					alert2.showAndWait();
				}	
			}
			//Si l'index sp�cifi� n'existe pas
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Modification du salaire");
				alert.setHeaderText(null);
				alert.setContentText("�chec de la modification.\nL'index d'employ� doit �tre inf�rieur � " +empListe.getIndexMax());
				alert.showAndWait();
			}
		}
	
		//Sinon, afficher un message indiquant les champs n�cessaires et leur format
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Modification du salaire");
			alert.setHeaderText(null);
			alert.setContentText("�chec de la modification.\nVous devez fournir un nom et pr�nom OU un index, avec le salaire d�sir�, au format suivant:\n"
					+ "Nom et pr�nom: Texte d'au moins 2 caract�res\nSalaire: Nombre >0 � deux d�cimales max\nIndex: Nombre entier >0 et <=" +empListe.getIndexMax());
			alert.showAndWait();
		}
	}
		
	/**
	 * d�clenche la suppression de l'employ� si tout est valide
	 */
	public void supprimerMethode() 
	{
		String index = textFieldIndex.getText();
		String nom = textFieldNom.getText().toUpperCase();
		String prenom = textFieldPrenom.getText().toUpperCase();
		String salaire = textFieldSalaire.getText();
			
		//Si les champs de nom et pr�nom sont remplis et valides
		if(validerChamps() && !nom.equals("") && !prenom.equals(""))
		{	
			Employe empTemp = new Employe(1, nom, prenom, 1.0);//Seul le nom et prenom sont utilis�s, l'index et le salaire sont des valeurs non utilis�e, on leur donne la valeur de 1
			
			//Si l'employ� existe dans la liste (comparaison bas�e sur le nom et prenom)
			if (empListe.existe(empTemp))
			{
				//Trouver la position de l'employ� recherch�(recherche selon nom et pr�nom)
				int indexTemp = empListe.getPosition(empTemp);
				//Cr�er un employ� poss�dant les attributs de l'employ� trouv� � la position
				Employe empRecherche = (Employe)empListe.getNoeud(indexTemp).getElement();
				
				//Afficher les infos de l'employ� et demander de confirmer la modification
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Suppression de l'employ�");
				alert.setHeaderText(null);
				alert.setContentText("Employ�: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment supprimer cet employ�?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empListe.supprimerNoeud(empRecherche.getIndex());
					listeSauvegardee = false;//changer l'indicateur bool�an qui indique si les modifs ont �t� enregistr�es
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Suppression de l'employ�.");
					alert2.setHeaderText(null);
					alert2.setContentText("Suppression de l'employ� r�ussie.\nPour remettre les index d'employ� � jour, red�marrer le programme.");
					alert2.showAndWait();
					
					resetChamps();
				}	
			}
			
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Suppression de l'employ�");
				alert.setHeaderText(null);
				alert.setContentText("�chec de la suppression.\nL'employ� correspondant � ces nom et pr�nom n'existe pas");
				alert.showAndWait();
			}
		}
		
		//Sinon si le champ index est rempli et valide
		else if (validerChamps() && !index.equals(""))
		{
			//Si l'index sp�cifi� existe
			if(Double.parseDouble(index) <= empListe.getIndexMax())
			{
				//Cr�er un employ� poss�dant les attributs de l'employ� trouv� � la position de l'index
				Employe empRecherche = (Employe)empListe.getNoeud(Integer.parseInt(index)).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Suppression de l'employ�");
				alert.setContentText("Employ�: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment supprimer cet employ� ?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empListe.supprimerNoeud(empRecherche.getIndex());
					listeSauvegardee = false;//changer l'indicateur bool�an qui indique si les modifs ont �t� enregistr�es
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Suppression de l'employ�");
					alert2.setHeaderText(null);
					alert2.setContentText("Suppression de l'employ� r�ussie.\nPour remettre les index d'employ� � jour, red�marrer le programme.");
					alert2.showAndWait();
					
					resetChamps();
				}	
			}
			//Si l'index sp�cifi� n'existe pas
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Suppresion de l'employ�");
				alert.setHeaderText(null);
				alert.setContentText("�chec de la suppression.\nL'index d'employ� doit �tre inf�rieur � " +empListe.getIndexMax());
				alert.showAndWait();
			}
		}
	
		//Sinon, afficher un message indiquant les champs n�cessaires et leur format
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Suppression du salaire");
			alert.setHeaderText(null);
			alert.setContentText("�chec de la suppression.\nVous devez fournir un nom et pr�nom OU un index au format suivant:\n"
					+ "Nom et pr�nom: Texte d'au moins 2 caract�res\nIndex: Nombre entier >0 et <=" +empListe.getIndexMax());
			alert.showAndWait();
		}
	}
		
	/**
	 * d�clenche le transfert de la liste si tout est valide
	 */
	public void transferMethode() {
		if (validertrans()) { // valider, avec l'utilisateur, le transfer
									// de la liste
			try {
				EcritureFichierExcel.ecrire(empListe);// transf�rer la liste
				listeSauvegardee = true;
			} catch (Exception e) {// avertir l'utilisateur de l'�chec du
									// transfert<
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Sauvegarde");
				alert.setHeaderText(null);
				alert.setContentText("�chec de la sauvegarde. Le fichier n'a pu �tre transf�r�");
				System.exit(0);
			}// avertir l'utilisateur du succes du transfert
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Sauvegarde");
			alert2.setHeaderText(null);
			alert2.setContentText("Le fichier a �t� transf�r�");
			
			resetChamps();
		}

	}

	/**
	 * confirme le tranfert avec l'utilisateur
	 * 
	 * @return le choix de l'utilisateur
	 */
	private boolean validertrans() 
	{
		boolean retour = false;
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sauvegarde");//**********************************************************
		alert.setHeaderText(null);
		alert.setContentText("Voulez-vous transf�rer la liste d'employ� vers le fichier Excel?");
		
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		
		
		alert.getButtonTypes().setAll(yes, no);
		Optional <ButtonType> result = alert.showAndWait();
		
		if (result.get() == yes)
		{
			retour = true;
		}
		
		return retour;
	}

	/**
	 * d�clenche la consultation du salaire de l'employ� si tout est valide
	 * La recherche est bas�e prioritairement sur le nom et le prenom si ces champs sont remplis.
	 * Sinon, on utilise l'index, si le champ est rempli.
	 */
	public void consulterMethode() 
	{
		String index = textFieldIndex.getText();
		String nom = textFieldNom.getText().toUpperCase();
		String prenom = textFieldPrenom.getText().toUpperCase();
		String salaire = textFieldSalaire.getText();
		
		
		//Si les champs de nom et pr�nom sont remplis et valides
		if(validerChamps() && !nom.equals("") && !prenom.equals(""))
		{	
			Employe empTemp = new Employe(1, nom, prenom, 1);//L'index est le salaire sont attribu� a 1 par d�faut pour construire l'employ�. Ils ne sont pas utilis�.
			
			//Si l'employ� existe dans la liste (comparaison bas�e sur le nom et prenom)
			if (empListe.existe(empTemp))
			{
				//Trouver la position de l'employ� recherch�(recherche selon nom et pr�nom)
				int indexTemp = empListe.getPosition(empTemp);
				//Cr�er un employ� poss�dant les attributs de l'employ� trouv� � la position
				Employe empRecherche = (Employe)empListe.getNoeud(indexTemp).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText(null);
				alert.setContentText("Employ�: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire: " +empRecherche.getSalaire() +"$");
				alert.showAndWait();
				
				textFieldIndex.setText(String.valueOf(empRecherche.getIndex()));
				textFieldNom.setText(empRecherche.getNom());
				textFieldPrenom.setText(empRecherche.getPrenom());
				textFieldSalaire.setText(String.valueOf(empRecherche.getSalaire()));
			}
			
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Consultation");
				alert.setHeaderText(null);
				alert.setContentText("�chec de la consultation.\nL'employ� correspondant � ces nom et pr�nom n'existe pas");
				alert.showAndWait();
			}
		}
		
		//Sinon si le champ index est rempli et valide, sans le nom et prenom
		else if (validerChamps() && !index.equals(""))
		{
			//Si l'index sp�cifi� existe
			if(Double.parseDouble(index) <= empListe.getIndexMax())
			{
				//Cr�er un employ� poss�dant les attributs de l'employ� trouv� � la position de l'index
				Employe empRecherche = (Employe)empListe.getNoeud(Integer.parseInt(index)).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText(null);
				alert.setContentText("Employ�: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire: " +empRecherche.getSalaire() +"$");
				alert.showAndWait();
				
				textFieldIndex.setText(String.valueOf(empRecherche.getIndex()));
				textFieldNom.setText(empRecherche.getNom());
				textFieldPrenom.setText(empRecherche.getPrenom());
				textFieldSalaire.setText(String.valueOf(empRecherche.getSalaire()));
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Consultation");
				alert.setHeaderText(null);
				alert.setContentText("�chec de la consultation.\nL'index d'employ� doit �tre inf�rieur ou �gal � " +empListe.getIndexMax());
				alert.showAndWait();
			}
		}
	
		//Sinon, afficher un message indiquant les champs n�cessaires et leur format
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Consultation");
			alert.setHeaderText(null);
			alert.setContentText("�chec de la consultation.\nLes informations doivent �tre du format suivant:\n"
					+ "Nom et pr�nom: Texte d'au moins 2 caract�res\nSalaire: Nombre >0 � deux d�cimales max\nIndex (optionnel): Nombre entier >0 et <=" +empListe.getIndexMax());
			alert.showAndWait();
		}
	}

	/**
	 * retourne l'information sur les salaires
	 */
	public void informationMethode() 
	{
		if (!empListe.estVide())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informations sur les salaires");
			alert.setHeaderText(null);
			alert.setContentText("Le salaire maximum est de\n" +empListe.getMaxSal() + "$\n" +
								 "Le salaire minimum est de\n" +empListe.getMinSal() + "$\n" +
								 "Le salaire moyen est de\n" +empListe.getMoyenneSal() + "$\n");
			alert.showAndWait();
		}
		
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Informations sur les salaires");
			alert.setHeaderText(null);
			alert.setContentText("Aucun employ� dans la liste");
			alert.showAndWait();
		}

	}

	/**
	 * V�rifier la validit� des champs remplis
	 * @return boolean qui indique si tout est valide
	 */
	private boolean validerChamps() {
		
		boolean ok = true;
		
		String index = textFieldIndex.getText();
		String nom = textFieldNom.getText().toUpperCase();
		String prenom = textFieldPrenom.getText().toUpperCase();
		String salaire = textFieldSalaire.getText();
		
		try
		{
			if (!salaire.equals(""))
			{
				if (!(Employe.validerSalaire(Double.parseDouble(salaire))))
					ok = false;
			}
			
			if (!index.equals(""))
			{
				if(!(Employe.validerIndex(Integer.parseInt(index))))
					ok = false;
			}
			
			if (!nom.equals(""))
			{
				if (!(Employe.validerNom(nom)))
					ok = false;
			}
			
			if (!prenom.equals(""))
			{
				if (!(Employe.validerPrenom(prenom)))
					ok = false;
			}
		}
		//G�rer les erreurs de parses
		catch (NumberFormatException e)
		{
			ok = false;
		}
		
		return ok;
	}

/**
 * Cr�er la liste de d�part
 */
	private void uploaderActuel() 
	{
		File f = new File("src\\Employe.xls");
		if (f.exists()) {//si le fichier Excel exite, t�l�charger le fichier Excel vers une liste
			LectureFichierExcel.Lire(f);
			empListe = LectureFichierExcel.getEmp();
		} else {//sinon cr�er une liste
			empListe = new ListedeNoeuds();
			System.out.println("test2");
		}
	}
	
	public void quitterMethode()
	{
		if (!listeSauvegardee)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Quitter");
			alert.setHeaderText(null);
			alert.setContentText("La liste a �t� modifi�e sans �tre sauvegard�e. Voulez-vous sauvegarder avant de quitter?");
			
			ButtonType yes = new ButtonType("Yes");
			ButtonType no = new ButtonType("No");
			ButtonType cancel = new ButtonType("Cancel");
			
			alert.getButtonTypes().setAll(yes, no, cancel);
			Optional <ButtonType> result = alert.showAndWait();
			
			if (result.get() == yes)
			{
				transferMethode();
				System.exit(0);
			}
			
			else if (result.get() == no)
			{
				System.exit(0);
			}
		}
		System.exit(0);
	}
	
	public class ListenerButton implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			if (e.getSource() == bouttonAjouter)
			{
				ajouterMethode();
			}
			
			else if (e.getSource() == bouttonConsulter)
			{
				consulterMethode();
			}
			
			else if (e.getSource() == bouttonModifSalaire)
			{
				modifierMethode();
			}
			
			else if (e.getSource() == bouttonSupprimer)
			{
				supprimerMethode();
			}
			
			else if (e.getSource() == bouttonInfo)
			{
				informationMethode();
			}
			
			else if (e.getSource() == bouttonEnregistrer)
			{
				transferMethode();
			}
			
			else if (e.getSource() == bouttonReinitial)
			{
				resetChamps();
			}
			else if (e.getSource() == bouttonQuitter)
			{
				quitterMethode();				
			}
		}
	}

	public void start(Stage pStage)
	{
		root = new Pane();
		scene = new Scene(root, 400, 610);
		scene.setRoot(root);
		
		menu();
		
		bouttonAjouter.setOnAction(new ListenerButton());
		bouttonConsulter.setOnAction(new ListenerButton());
		bouttonEnregistrer.setOnAction(new ListenerButton());
		bouttonInfo.setOnAction(new ListenerButton());
		bouttonModifSalaire.setOnAction(new ListenerButton());
		bouttonQuitter.setOnAction(new ListenerButton());
		bouttonReinitial.setOnAction(new ListenerButton());
		bouttonSupprimer.setOnAction(new ListenerButton());	
		
		pStage.setScene(scene);
		pStage.show();
	}

	public static void main(String args[]) {
		Application.launch(args);

	}
}
