/**
 * 
 */

/**
 * @author Kosta Lazarev & Omri Glam
 * Main class for launching war application
 */
public class Program {
	public static final String CONFIG_FILE = "war.xml";
	/**
	 * @param args 3 years and I still don't know what this is.
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
