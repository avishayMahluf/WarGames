package Main;
import XML.ReadXMLFile;

/**
 * @author Kosta Lazarev & Omri Glam
 * Main class for launching war application
 */
public class Program {
	public static final String CONFIG_FILE = "war.xml";
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		
		War warGames = new War();

		try {
			
			ReadXMLFile.getData(CONFIG_FILE, warGames.getMissileLaunchers(),
					warGames.getMissileDestructors(),
					warGames.getMissileLauncherDestructors(),warGames.getStatistics(),warGames.getFileHandler());

			warGames.startWar();
			warGames.showMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
