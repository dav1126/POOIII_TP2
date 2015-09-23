
import java.io.File;
import java.io.IOException;

import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Classe LectureFichierExcel : Transférer un fichier Excel vers une liste
 * 
 * 
 */
public class LectureFichierExcel {
	private static ListedeNoeuds emp = new ListedeNoeuds();

	/**
	 * Transférer un fichier Excel vers une liste
	 * 
	 * @param f
	 *            : fichier à transférer
	 */
	public static void Lire(File f) {
		Workbook workbook = null;
		int i;
		int nbRow;
		try {
			/* Récupération du classeur Excel (en lecture) */
			workbook = Workbook.getWorkbook(f);
			/* Première feuille du fichier */
			Sheet sheet = workbook.getSheet(0);
			nbRow = sheet.getRows();
		
			// Lire les lignes les une apres les autre et construire la liste
			int indexTemp;
			String nomTemp;
			String prenomTemp;
			double salaireTemp;
			Employe empTemp = null;
			
			if (!sheet.getCell(0, 1).getContents().equals(""))
			{
				for (i = 1 ; i < nbRow; i++)
				{	
					LabelCell contenuPrenom = (LabelCell) sheet.getCell(0, i);
					LabelCell contenuNom = (LabelCell)sheet.getCell(1, i);
					NumberCell contenuSalaire = (NumberCell)sheet.getCell(2, i);
					
					nomTemp = contenuNom.getContents();
					prenomTemp = contenuPrenom.getContents();
					salaireTemp = contenuSalaire.getValue();
					
					indexTemp = i;								
					
					empTemp = new Employe(indexTemp, nomTemp, prenomTemp, salaireTemp);				
					
					emp.ajouterElement(empTemp, indexTemp);	
				}
			}
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				/* On ferme le worbook pour libérer la mémoire */
				workbook.close();
			}
		}

	}

	/**
	 * Retourne la liste créee
	 * @return the emp
	 */
	public static ListedeNoeuds getEmp() {
		return emp;
	}

}
