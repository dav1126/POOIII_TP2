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
	
	//Variable booleene qui indique si la sauvegarde de la liste a été effectuée. 
	//Utilisée pour faire afficher un avertissement si la liste n'est pas sauvegardée lorsque l'utilisateur quitte.
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
	 * création de l'interface graphique
	 */
	public void menu() {
		//mettre le contenu du fichier dans une liste
		uploaderActuel();
		
		// Création de l'interface graphique		
		labelTitre = new Label("Remplir le formulaire et sélectionner\nl'option désirée");
		labelTitre.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		labelTitre.setTranslateX(55);
		labelTitre.setTranslateY(10);
		labelTitre.setTextAlignment(TextAlignment.CENTER);	
		
		labelNom = new Label("Entrer le nom de l'employé:");
		labelNom.setTranslateX(25);
		labelNom.setTranslateY(60);
		
		labelPrenom = new Label("Entrer le prenom de l'employé:");
		labelPrenom.setTranslateX(25);
		labelPrenom.setTranslateY(120);
		
		labelSalaire = new Label("Entrer le salaire de l'employé:");
		labelSalaire.setTranslateX(25);
		labelSalaire.setTranslateY(180);
		
		labelIndex = new Label("Entrer l'index de l'employé:");
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
		
		bouttonAjouter = new Button("Ajouter l'employé");
		bouttonAjouter.setTranslateX(25);
		bouttonAjouter.setTranslateY(310);
		bouttonAjouter.setPrefSize(350, 30);
		
		bouttonConsulter = new Button("Consulter un employé");
		bouttonConsulter.setTranslateX(25);
		bouttonConsulter.setTranslateY(345);
		bouttonConsulter.setPrefSize(350, 30);
		
		bouttonModifSalaire = new Button("Modifier le salaire d'un employé");
		bouttonModifSalaire.setTranslateX(25);
		bouttonModifSalaire.setTranslateY(380);
		bouttonModifSalaire.setPrefSize(350, 30);
		
		bouttonSupprimer = new Button("Supprimer l'employé");
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
		
		bouttonReinitial = new Button("Réinitialiser les champs");
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
	 * Déclenche l'ajout de l'employé si tout est valide
	 * On s'assure que l'employé n'existe pas déjà et que les champs sont valides.
	 * Si l'utilisateur tente d'insérer un nouvel employé parmis les autres en spécifiant un index, 
	 * on l'averti que cela produira un décalage des index de certains autres employés.
	 */
	public void ajouterMethode() 
	{
		//Création de l'employé si les champs nécessaires sont remplis et valides
		if (validerChamps() && !textFieldNom.getText().trim().isEmpty() && !textFieldPrenom.getText().trim().isEmpty() && !textFieldSalaire.getText().trim().isEmpty())
		{
			//Si l'utilisateur spécifie un index, on l'utilise pour créer le nouvel employé
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
		
		
			//Si l'employé n'existe pas déjà
			if (validerAjouter(emp))
			{
				//Si la liste n'est pas vide et que l'utilisateur a spécifié un index
				if (!empListe.estVide() && !textFieldIndex.getText().equals(""))
				{
					//Si l'index spécifié est supérieur à l'index maximum observé dans la liste
					if (emp.getIndex() > empListe.getIndexMax())
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Ajouter employe");
						alert.setHeaderText(null);
						alert.setContentText("Pour insérer une employé à un index spécifique dans la liste, utiliser un index inférieur ou égal à " + empListe.getIndexMax() + ".\nSi vous voulez simplement ajouter un employé à la suite de la liste, ne spécifiez aucun index." );
					}
					//Si l'index spécifié est inférieur à l'index max observé dans la liste, avertir l'utilisateur qu'une insertion aura lieu parmis la liste
					else
					{
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Ajouter employé");
						alert.setHeaderText(null);
						alert.setContentText("Attention!\n Insérer un employé en spécifiant un index entrainerat un décalage des index d'employés \nexistant qui possède un index égal ou supérieur à celui du nouvel employé!\n Voulez-vous ajouter l'employé tout de même?");
						
						ButtonType yes = new ButtonType("Oui");
						ButtonType no = new ButtonType("Non");
						
						
						alert.getButtonTypes().setAll(yes, no);
						Optional <ButtonType> result = alert.showAndWait();
						
						//Si l'utilisateur accepte d'insérer un employé parmis les autres, on ajoute l'employé
						if (result.get() == yes)
						{
							empListe.ajouterElement(emp, emp.getIndex());
							listeSauvegardee = false;//changer l'indicateur booléan qui indique si les modifs ont été enregistrées
							
							Alert alertOK = new Alert(AlertType.INFORMATION);
							alertOK.setTitle("Ajouter employé");
							alertOK.setHeaderText(null);
							alertOK.setContentText("Ajout effectué. Les index seront remis à jour lors du prochain démarrage du programme");
							alertOK.showAndWait();
							
							resetChamps();
						}		
					}
				}
				
				//Si aucun index n'est spécifié, on ajoute l'employé
				else
				{
					empListe.ajouterElement(emp, emp.getIndex());
					listeSauvegardee = false;//changer l'indicateur booléan qui indique si les modifs ont été enregistrées
					
					resetChamps();
					
					Alert alertOK = new Alert(AlertType.INFORMATION);
					alertOK.setTitle("Ajouter employé");
					alertOK.setHeaderText(null);
					alertOK.setContentText("Ajout effectué");
					alertOK.showAndWait();
				}	
			}
			
			//Si l'employé existe déjà
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Ajouter employé");
				alert.setHeaderText(null);
				alert.setContentText("Échec de l'ajout.\nCet employé existe déjà!");
				alert.showAndWait();
			}
		}
		//Si les champs ne sont pas valides
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Ajouter employé");
			alert.setHeaderText(null);
			alert.setContentText("Échec de l'ajout.\nLes informations nom, prénom salaire et index(optionnel) sont nécessaires, au format suivant:\n"
					+ "Nom et prénom: Texte d'au moins 2 caractères\nSalaire: Nombre >= 0\nIndex: Nombre entier >0 et <=" +(empListe.getIndexMax()+1));
			alert.showAndWait();
		}
	}
	
	//Vérifie si l'employé existe déjà dans la liste
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
	 * déclenche la modification du salaire de l'employé si tout est valide
	 * On trouve l'employé à modifier selon les nom et prénom OU selon l'index spécifié
	 * On affiche ensuite les infos de l'employés et on demande de confirmer la modification du salaire
	 */
	public void modifierMethode() {
		
		String index = textFieldIndex.getText();
		String nom = textFieldNom.getText().toUpperCase();
		String prenom = textFieldPrenom.getText().toUpperCase();
		String salaire = textFieldSalaire.getText();
			
		//Si les champs de nom, prénom et salaire sont remplis et valides
		if(validerChamps() && !nom.equals("") && !prenom.equals("") && !salaire.equals(""))
		{	
			Employe empTemp = new Employe(1, nom, prenom, Double.parseDouble(salaire)); //L'index de 1 est attribué apr défaut pour construire l'employé, il n'est pas utilisé
			
			//Si l'employé existe dans la liste (comparaison basée sur le nom et prenom)
			if (empListe.existe(empTemp))
			{
				//Trouver la position de l'employé recherché(recherche selon nom et prénom)
				int indexTemp = empListe.getPosition(empTemp);
				//Créer un employé possédant les attributs de l'employé trouvé à la position
				Employe empRecherche = (Employe)empListe.getNoeud(indexTemp).getElement();
				
				//Afficher les infos de l'employé et demander de confirmer la modification
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Modification du salaire");
				alert.setHeaderText(null);
				alert.setContentText("Employé: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment modifier le salaire de l'employé pour" +salaire+" ?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empRecherche.setSalaire(Double.parseDouble(salaire));
					listeSauvegardee = false;//changer l'indicateur booléan qui indique si les modifs ont été enregistrées
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Modification du salaire");
					alert2.setHeaderText(null);
					alert2.setContentText("Modification du salaire réussie");
					alert2.showAndWait();
				}	
			}
			
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Modification du salaire");
				alert.setHeaderText(null);
				alert.setContentText("Échec de la consultation.\nL'employé correspondant à ces nom et prénom n'existe pas");
				alert.showAndWait();
			}
		}
		
		//Sinon si les champs index et salaire sont remplis et valides
		else if (validerChamps() && !index.equals("") && !salaire.equals(""))
		{
			//Si l'index spécifié existe
			if(Double.parseDouble(index) <= empListe.getIndexMax())
			{
				//Créer un employé possédant les attributs de l'employé trouvé à la position de l'index
				Employe empRecherche = (Employe)empListe.getNoeud(Integer.parseInt(index)).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Modification du salaire");
				alert.setContentText("Employé: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment modifier le salaire de l'employé pour" +salaire+" ?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empRecherche.setSalaire(Double.parseDouble(salaire));
					listeSauvegardee = false;//changer l'indicateur booléan qui indique si les modifs ont été enregistrées 
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Modification du salaire");
					alert2.setHeaderText(null);
					alert2.setContentText("Modification du salaire réussie");
					alert2.showAndWait();
				}	
			}
			//Si l'index spécifié n'existe pas
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Modification du salaire");
				alert.setHeaderText(null);
				alert.setContentText("Échec de la modification.\nL'index d'employé doit être inférieur à " +empListe.getIndexMax());
				alert.showAndWait();
			}
		}
	
		//Sinon, afficher un message indiquant les champs nécessaires et leur format
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Modification du salaire");
			alert.setHeaderText(null);
			alert.setContentText("Échec de la modification.\nVous devez fournir un nom et prénom OU un index, avec le salaire désiré, au format suivant:\n"
					+ "Nom et prénom: Texte d'au moins 2 caractères\nSalaire: Nombre >0 à deux décimales max\nIndex: Nombre entier >0 et <=" +empListe.getIndexMax());
			alert.showAndWait();
		}
	}
		
	/**
	 * déclenche la suppression de l'employé si tout est valide
	 */
	public void supprimerMethode() 
	{
		String index = textFieldIndex.getText();
		String nom = textFieldNom.getText().toUpperCase();
		String prenom = textFieldPrenom.getText().toUpperCase();
		String salaire = textFieldSalaire.getText();
			
		//Si les champs de nom et prénom sont remplis et valides
		if(validerChamps() && !nom.equals("") && !prenom.equals(""))
		{	
			Employe empTemp = new Employe(1, nom, prenom, 1.0);//Seul le nom et prenom sont utilisés, l'index et le salaire sont des valeurs non utilisée, on leur donne la valeur de 1
			
			//Si l'employé existe dans la liste (comparaison basée sur le nom et prenom)
			if (empListe.existe(empTemp))
			{
				//Trouver la position de l'employé recherché(recherche selon nom et prénom)
				int indexTemp = empListe.getPosition(empTemp);
				//Créer un employé possédant les attributs de l'employé trouvé à la position
				Employe empRecherche = (Employe)empListe.getNoeud(indexTemp).getElement();
				
				//Afficher les infos de l'employé et demander de confirmer la modification
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Suppression de l'employé");
				alert.setHeaderText(null);
				alert.setContentText("Employé: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment supprimer cet employé?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empListe.supprimerNoeud(empRecherche.getIndex());
					listeSauvegardee = false;//changer l'indicateur booléan qui indique si les modifs ont été enregistrées
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Suppression de l'employé.");
					alert2.setHeaderText(null);
					alert2.setContentText("Suppression de l'employé réussie.\nPour remettre les index d'employé à jour, redémarrer le programme.");
					alert2.showAndWait();
					
					resetChamps();
				}	
			}
			
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Suppression de l'employé");
				alert.setHeaderText(null);
				alert.setContentText("Échec de la suppression.\nL'employé correspondant à ces nom et prénom n'existe pas");
				alert.showAndWait();
			}
		}
		
		//Sinon si le champ index est rempli et valide
		else if (validerChamps() && !index.equals(""))
		{
			//Si l'index spécifié existe
			if(Double.parseDouble(index) <= empListe.getIndexMax())
			{
				//Créer un employé possédant les attributs de l'employé trouvé à la position de l'index
				Employe empRecherche = (Employe)empListe.getNoeud(Integer.parseInt(index)).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Suppression de l'employé");
				alert.setContentText("Employé: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
									 +"\nIndex: " +empRecherche.getIndex()
									 +"\nSalaire actuel: " +empRecherche.getSalaire() +"$"
									 +"\n\nVoulez-vous vraiment supprimer cet employé ?");
				
				ButtonType yes = new ButtonType("Oui");
				ButtonType no = new ButtonType("Non");
				
				alert.getButtonTypes().setAll(yes, no);
				Optional <ButtonType> result = alert.showAndWait();
				
				if (result.get() == yes)
				{
					empListe.supprimerNoeud(empRecherche.getIndex());
					listeSauvegardee = false;//changer l'indicateur booléan qui indique si les modifs ont été enregistrées
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Suppression de l'employé");
					alert2.setHeaderText(null);
					alert2.setContentText("Suppression de l'employé réussie.\nPour remettre les index d'employé à jour, redémarrer le programme.");
					alert2.showAndWait();
					
					resetChamps();
				}	
			}
			//Si l'index spécifié n'existe pas
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Suppresion de l'employé");
				alert.setHeaderText(null);
				alert.setContentText("Échec de la suppression.\nL'index d'employé doit être inférieur à " +empListe.getIndexMax());
				alert.showAndWait();
			}
		}
	
		//Sinon, afficher un message indiquant les champs nécessaires et leur format
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Suppression du salaire");
			alert.setHeaderText(null);
			alert.setContentText("Échec de la suppression.\nVous devez fournir un nom et prénom OU un index au format suivant:\n"
					+ "Nom et prénom: Texte d'au moins 2 caractères\nIndex: Nombre entier >0 et <=" +empListe.getIndexMax());
			alert.showAndWait();
		}
	}
		
	/**
	 * déclenche le transfert de la liste si tout est valide
	 */
	public void transferMethode() {
		if (validertrans()) { // valider, avec l'utilisateur, le transfer
									// de la liste
			try {
				EcritureFichierExcel.ecrire(empListe);// transférer la liste
				listeSauvegardee = true;
			} catch (Exception e) {// avertir l'utilisateur de l'échec du
									// transfert<
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Sauvegarde");
				alert.setHeaderText(null);
				alert.setContentText("Échec de la sauvegarde. Le fichier n'a pu être transféré");
				System.exit(0);
			}// avertir l'utilisateur du succes du transfert
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Sauvegarde");
			alert2.setHeaderText(null);
			alert2.setContentText("Le fichier a été transféré");
			
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
		alert.setContentText("Voulez-vous transférer la liste d'employé vers le fichier Excel?");
		
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
	 * déclenche la consultation du salaire de l'employé si tout est valide
	 * La recherche est basée prioritairement sur le nom et le prenom si ces champs sont remplis.
	 * Sinon, on utilise l'index, si le champ est rempli.
	 */
	public void consulterMethode() 
	{
		String index = textFieldIndex.getText();
		String nom = textFieldNom.getText().toUpperCase();
		String prenom = textFieldPrenom.getText().toUpperCase();
		String salaire = textFieldSalaire.getText();
		
		
		//Si les champs de nom et prénom sont remplis et valides
		if(validerChamps() && !nom.equals("") && !prenom.equals(""))
		{	
			Employe empTemp = new Employe(1, nom, prenom, 1);//L'index est le salaire sont attribué a 1 par défaut pour construire l'employé. Ils ne sont pas utilisé.
			
			//Si l'employé existe dans la liste (comparaison basée sur le nom et prenom)
			if (empListe.existe(empTemp))
			{
				//Trouver la position de l'employé recherché(recherche selon nom et prénom)
				int indexTemp = empListe.getPosition(empTemp);
				//Créer un employé possédant les attributs de l'employé trouvé à la position
				Employe empRecherche = (Employe)empListe.getNoeud(indexTemp).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText(null);
				alert.setContentText("Employé: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
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
				alert.setContentText("Échec de la consultation.\nL'employé correspondant à ces nom et prénom n'existe pas");
				alert.showAndWait();
			}
		}
		
		//Sinon si le champ index est rempli et valide, sans le nom et prenom
		else if (validerChamps() && !index.equals(""))
		{
			//Si l'index spécifié existe
			if(Double.parseDouble(index) <= empListe.getIndexMax())
			{
				//Créer un employé possédant les attributs de l'employé trouvé à la position de l'index
				Employe empRecherche = (Employe)empListe.getNoeud(Integer.parseInt(index)).getElement();
				
				//Afficher les infos
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText(null);
				alert.setContentText("Employé: " +empRecherche.getPrenom() + " " + empRecherche.getNom()
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
				alert.setContentText("Échec de la consultation.\nL'index d'employé doit être inférieur ou égal à " +empListe.getIndexMax());
				alert.showAndWait();
			}
		}
	
		//Sinon, afficher un message indiquant les champs nécessaires et leur format
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Consultation");
			alert.setHeaderText(null);
			alert.setContentText("Échec de la consultation.\nLes informations doivent être du format suivant:\n"
					+ "Nom et prénom: Texte d'au moins 2 caractères\nSalaire: Nombre >0 à deux décimales max\nIndex (optionnel): Nombre entier >0 et <=" +empListe.getIndexMax());
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
			alert.setContentText("Aucun employé dans la liste");
			alert.showAndWait();
		}

	}

	/**
	 * Vérifier la validité des champs remplis
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
		//Gérer les erreurs de parses
		catch (NumberFormatException e)
		{
			ok = false;
		}
		
		return ok;
	}

/**
 * Créer la liste de départ
 */
	private void uploaderActuel() 
	{
		File f = new File("src\\Employe.xls");
		if (f.exists()) {//si le fichier Excel exite, télécharger le fichier Excel vers une liste
			LectureFichierExcel.Lire(f);
			empListe = LectureFichierExcel.getEmp();
		} else {//sinon créer une liste
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
			alert.setContentText("La liste a été modifiée sans être sauvegardée. Voulez-vous sauvegarder avant de quitter?");
			
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
