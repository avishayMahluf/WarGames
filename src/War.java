import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.reflect.Method;

import com.sun.jmx.snmp.tasks.Task;

/*
 * WarGames main class
 */

/**
 * 
 * Main program class that lunches the system into activity
 * 
 * @author by Kosta Lazarev & Omri Glam
 * @version 16/07/2014
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
	public static int WarTimeInSeconds;

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

	}

	/**
	 * Start seconds counting
	 */
	protected void startWarTimer() {
		System.out.println("War Clock Started");
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
		for (Launcher mLauncer : missileLaunchers) {
			mLauncer.start();
		}
		for (Destructor destructor : missileDestructors) {
			destructor.start();
		}

	}

	/**
	 * Main functions
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		War WarGames = new War();

		try {

			ReadXMLFile.getData(CONFIG_FILE, WarGames.getMissileLaunchers(),
					WarGames.getMissileDestructors(),
					WarGames.getMissileLauncherDestructors());

			WarGames.startWar();
			WarGames.showMenu();
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
}
