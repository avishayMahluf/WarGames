import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * WarGames main class
 */

/**
 * 
 * Main program class that lunches the system into activity
 * 
 * @author by Kosta Lazarev & Omri Glam
 * @version 25/08/2014
 */
public class War {

	public static final String CONFIG_FILE = "war.xml";
	/**
	 * The game works on a clock that logs events. the tick set in mili seconds.
	 */
	public static final int TIMER_TICK = 1000;
	private List<Destructor> missileDestructors;
	private List<Destructor> missileLauncherDestructors;
	private List<Launcher> missileLaunchers;
	private List<Missile> inAirMissiles;
	private Timer WarTimer;
	private boolean started;
	private Statistics stats;
	public static int WarTimeInSeconds;
	private static Logger logger;
	private FileHandler fileHandler;
	
	/**
	 * Main war game constructor
	 */
	public War() {
		WarTimeInSeconds = 0;
		missileDestructors = new ArrayList<Destructor>();
		missileLauncherDestructors = new ArrayList<Destructor>();
		missileLaunchers = new ArrayList<Launcher>();
		inAirMissiles = new ArrayList<Missile>();
		WarTimer = new Timer();
		started = false;
		stats=new Statistics();
		addFileHandler();

	}

	private void addFileHandler() {
		try{		
			fileHandler = new FileHandler("WarLog.txt");
			fileHandler.setFormatter(new WarFormatter());
			logger = Logger.getLogger("War.Logger");
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
		}catch(Exception e){
			System.err.println("Logger was not found!");
		}
		
	}

	/**
	 * Start seconds counting
	 */
	protected void startWarTimer() {
		logger.log(Level.INFO,"War Clock Started");
		WarTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				WarTimeInSeconds++;
				// System.out.println(WarTimeInSeconds);
				if (WarTimeInSeconds == Integer.MAX_VALUE) {
					endWar();
				}

			}
		}, 0, TIMER_TICK);
		started = true;
	}

	/**
	 * End war game, stops timer
	 */
	protected void endWar() {
		// TODO Auto-generated method stub

	}

	/**
	 * Starts game By starting all threads from config file
	 */
	private void startWar() {
		startWarTimer();
		logger.log(Level.INFO,"War has started");
		for (Launcher mLauncer : missileLaunchers) {
			mLauncer.start();
		}
		for (Destructor destructor : missileDestructors) {
			destructor.start();
		}
		for (Destructor destructor : missileLauncherDestructors) {
			destructor.start();
		}

	}

	/**
	 * Main functions
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		War warGames = new War();

		try {

			ReadXMLFile.getData(CONFIG_FILE, warGames.getMissileLaunchers(),
					warGames.getMissileDestructors(),
					warGames.getMissileLauncherDestructors(),warGames.stats,warGames.fileHandler);

			warGames.startWar();
			warGames.showMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
/**
 * Missiles that are in the air right now
 * @return
 */
	public List<Missile> getInAirMissiles() {
		return inAirMissiles;
	}

	public List<Destructor> getMissileDestructors() {
		return missileDestructors;
	}

	public List<Destructor> getMissileLauncherDestructors() {
		return missileLauncherDestructors;
	}

	public List<Launcher> getMissileLaunchers() {
		return missileLaunchers;
	}
	public Statistics getStatistics(){
		return stats;
	}
	/**
	 * War started boolean
	 * 
	 * @return TRUE if war is started
	 */
 	public boolean isStarted() {
		return started;
	}

	/**
	 * Prints menu and selections
	 */
	public void showMenu() {
		String[] menuList = { "Add launcher destructor",
				"Add missile destructor", "Add launcher", "Launch missile",
				"Intercept launcher", "Intercept missile", "Show statistics",
				"End war game" };

		Menu function = new Menu(this);
		Scanner s = new Scanner(System.in);
		while (this.isStarted()) {
			for (int i = 0; i < menuList.length; i++) {
				System.out.println((i + 1) + " - " + menuList[i]);
			}
			int action = s.nextInt();
			switch (action) {
			case 1:
				function.addLauncherDestructor();
				break;
			case 2:
				function.addMissileDestructor();
				break;
			case 3:
				function.addLauncher();
				break;
			case 4:
				function.missileLunch();
				break;
			case 5:
				function.launcherIntercept();
				break;
			case 6:
				function.missileIntercept();
				break;
			case 7:
				function.showStatistics();
				break;
			case 8:
				function.endWarGame();
				break;

			default:
				break;
			}
		}

	}

	/**
	 * Updates inAirMissile list method goes through all launcher and checks
	 * missiles in the air and set them in one list.
	 */
	public void updateInAirMissiles() {
		inAirMissiles = new ArrayList<Missile>();
		for (Launcher l : missileLaunchers) {
			for (Missile missile : l.getMissiles()) {
				if (missile.getMissileState() == Missile.State.Flying) {
					inAirMissiles.add(missile);
				}
			}
		}
	}

	/**
	 * Add missile destructor to list
	 */
	public void addMissileDestructor(int id) {

		missileDestructors.add(new Destructor("D" + id));

	}

	/**
	 * Add launcher destructor to list
	 */
	public void addLauncherDestructor(String type) {
		missileLauncherDestructors.add(new Destructor(Destructor.Type
				.valueOf(type)));
	}
	/**
	 * 
	 * @param id
	 */
	public void addLauncher(int id){
		missileLaunchers.add(new Launcher("" + id));
		
	}
	
	public FileHandler getFileHandler(){
		return fileHandler;
	}
	
}
