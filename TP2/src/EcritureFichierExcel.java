import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;

import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
/**
 * Classe EcritureFichierExcel : Transférer la liste vers un fichier Excel
 * 
 * 
 */
public class EcritureFichierExcel {

	/**
	 * Afficher les résultats
	 * 
	 * @throws WriteException
	 *             si le fichier ne peut être créé
	 * 
	 * @throws IOException
	 *             si le fichier ne peut être créé
	 */
	public static void ecrire(ListedeNoeuds empListe) throws WriteException,
			IOException {
		try {
			// création d'un fichier excel
			WritableWorkbook workbook = Workbook.createWorkbook(new File("src\\Employe.xls"));
			// création d'une feuille de calcul
				WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			// création du format des entêtes de colonnes
				WritableCellFormat titleFormat = new WritableCellFormat();
				titleFormat.setWrap(true);
				titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
				
			// création du format pour texte
				WritableCellFormat textFormat = new WritableCellFormat();
				textFormat.setWrap(true);
				textFormat.setAlignment(jxl.format.Alignment.CENTRE);
	
			// création du format pour nombre double
	
			WritableCellFormat fourdps = new WritableCellFormat(
					new NumberFormat("0.00"));
			fourdps.setAlignment(jxl.format.Alignment.CENTRE);
			
	
			// fixer la largeur des colonnes
			for (int i = 0; i <3; i++) {
				sheet.setColumnView(i, 12);
			}
			
				if (!empListe.estVide())
				{
					// si la liste n'est pas vide
					
						// afficher les entêtes de colonnes
						sheet.addCell(new Label(0,  0, "Prenom", titleFormat));
						sheet.addCell(new Label(1,  0, "Nom", titleFormat));
						sheet.addCell(new Label(2, 0, "Salaire", titleFormat));
						
						// afficher la liste
						for (int i = 1; i <= empListe.getTaille(); i++) {
							sheet.addCell(new Label(0,  i, ((Employe) empListe
									.getNoeud(i).getElement()).getPrenom().toUpperCase(), textFormat));
							sheet.addCell(new Label(1,  i, ((Employe) empListe
									.getNoeud(i).getElement()).getNom().toUpperCase(), textFormat));
							sheet.addCell(new Number(2,  i, ((Employe) empListe
									.getNoeud(i).getElement()).getSalaire(), fourdps));
							JOptionPane.showMessageDialog(null, "Enregistrement dans le fichier Excel de l'employé:"+i);
							
							
						}

				} 
				else 
				{// si personne 
					sheet.addCell(new Label(0, 0, "La Liste est vide"));
				}
			// écrire les résultats
				workbook.write();
				workbook.close();

			// confirmer l'écriture des résultats
				JOptionPane.showMessageDialog(null, "Écriture terminée");


		} catch (IOException | WriteException ie) {
			// avertissement si le fichier n'a pu être généré
			JOptionPane.showMessageDialog(null,
					"Le fichier n'a pas pu être créé", "Boîte Message",
					JOptionPane.WARNING_MESSAGE);

		}

	}

}
