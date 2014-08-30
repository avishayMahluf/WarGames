package WarWeapons;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import Logger.ObjectFilter;
import Logger.WarFormatter;
import Main.Statistics;
import Main.War;
import sun.launcher.resources.launcher;

/**
 * Launcher class creates a launcher object (Thread)
 * <br><br>
 * Functions links:<br>
 * {@link #run()}<br>
 * {@link #addMissile(Missile)}<br>
 * {@link #getMissile(String)}<br>
 * {@link #getMissiles()}<br>
 * {@link #removeMissile(Missile)}<br>
 * {@link #Launcher(String, boolean, Statistics)}
 * 
 * @author Kosta Lazarev & Omri Glam
 *
 */
public class Launcher extends Thread {

	private static final int MIN_PEEK = 6000;
	private static final int MAX_PEEK = 15000;

	private static Logger logger;
	private PriorityQueue<Missile> missiles;
	private String id;
	private boolean isHidden;
	private boolean isDestroyed;
	private int destructTime;
	private Statistics stats;
	private FileHandler fileHandler;


	/**
	 * Launcher lock
	 */
	public Object Lock = new Object();

	public int getDestructTime() {
		return destructTime;
	}
	/**
	 * Sets launcher distraction time
	 * this time is used by the launcher distractors
	 * default is 0
	 * @param destructTime time in war time (seconds)
	 */
	public void setDestructTime(int destructTime) {
		this.destructTime = destructTime;
	}
	/**
	 * returns launcher visibility state
	 * @return state
	 */
	public boolean isHidden() {
		return isHidden;
	}
	/**
	 * Set launcher visibility state
	 * @param isHidden
	 */
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getLauncherId() {
		return id;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public boolean destroyLauncher() {
		if (!isHidden) {
			isDestroyed = true;
			return true;
		}

		return false;
	}
	/**
	 * Create simple missile launcher with custom id
	 * @param Id
	 */
	public Launcher(String Id) {

		isDestroyed = false;
		Comparator<Missile> comparator = new Missile.MissileLaunchComparator();
		missiles = new PriorityQueue<Missile>(comparator);

		this.id = Id;
		this.isHidden = true;

	}

	/**
	 * full launcher with pre state settings
	 * @param Id custom id
	 * @param IsHidden set launcher visibility 
	 */
	public Launcher(String Id, boolean IsHidden,Statistics stats) {
		this.stats = stats;
		isDestroyed = false;
		Comparator<Missile> comparator = new Missile.MissileLaunchComparator();
		missiles = new PriorityQueue<Missile>(comparator);

		this.id = Id;
		this.isHidden = IsHidden;

		try {
			fileHandler = new FileHandler("Launcher_"+this.id+".txt",false);
			fileHandler.setFilter(new ObjectFilter(this));
			fileHandler.setFormatter(new WarFormatter());

			logger = Logger.getLogger("War.Logger");
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);

		} catch (Exception e) {
			System.err.println("Launcher #"+this.id +" logger didn't started");
		}


	}

	/**
	 * Adds missile to luncher
	 * 
	 * @param m Missile
	 */
	public void addMissile(Missile m) {
		missiles.add(m);
	}
	/**
	 * getMissiles returns all missiles
	 * @return ArrayList<Missile>(missiles)
	 */
	public List<Missile> getMissiles() {

		return new ArrayList<Missile>(missiles);
	}
	/**
	 * getMissilegets gets a missile id and returns the missile (if doesn't exist returns null)
	 * @param id
	 * @return m - missile/null
	 */
	public Missile getMissile(String id) {
		Missile m = null;
		for (Missile missile : missiles) {
			if (missile.equals(id))
				m = missile;
		}
		return m;
	}

	/**
	 * Get launcher by id from list
	 * 
	 * @param list
	 * @param id
	 * @return launcher
	 */
	public static Launcher getLauncher(List<Launcher> list, String id) {
		Launcher l = null;
		for (Launcher launcher : list) {
			if (launcher.equals(id))
				l = launcher;
		}
		return l;
	}

	/**
	 * Remove the current missile from the launcher list
	 * @param m
	 */
	public void removeMissile(Missile m) {
		missiles.remove(m.getId());

	}
	@Override
	public String toString() {
		return "Launcher #" + this.id ;
	}
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Launcher) {
			return ((Launcher) obj).getLauncherId().equals(id);
		}

		return ((String) obj).equals(id);

	}

	/**
	 * Makes launcher visible for attacking for random period of time
	 */
	protected void peek() {
		logger.log(Level.INFO, this.toString() + " is visible",this);
		isHidden = false;
		int peekTime = MIN_PEEK + (int) (Math.random() * MAX_PEEK);
		Launcher currentLauncher = this;
		Thread launcherHidder = new Thread(){ 
			@Override
			public void run() {
				try {
					sleep(peekTime);
					if(!isDestroyed){
						isHidden = true;
						logger.log(Level.INFO, currentLauncher.toString() + " is hidden",this);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.err.println("Hidder thread failed");
				}
			}

		};
		launcherHidder.start();
	}

	@Override
	/**
	 * Launcher run
	 */
	public void run() {
		logger.log(Level.INFO,this.toString()+" is activated",this);
		while (!isDestroyed) {
			try {
				Missile m = missiles.peek();

				if (m != null) {

					if (m.getLauncTime() == War.WarTimeInSeconds
							|| m.getLauncTime() == 0) {

						m.setLock(Lock);
						m.setStatistics(stats);
						m.start();
						logger.log(Level.INFO,this.toString() 
								+ " launched " + m.toString(),this);
						stats.addMissileLaunch();

						synchronized (this) {
							try {
								peek();
								wait();
								missiles.poll();
								if(m.getMissileState() == Missile.State.Hit){
									logger.log(Level.INFO,m.toString() 
											+" " + m.getMissileState().toString() 
											+ "s and caused " + m.getDamage() 
											+ " damage",this);
								}else{
									logger.log(Level.INFO,m.toString() 
											+ " was " + m.getMissileState().toString(),this);
								}
							} catch (InterruptedException e) {

								e.printStackTrace();
								System.err.println("missile was interputed");
							}
						}
					} 

				}
				sleep(100);

			} catch (Exception e) {
				//	e.printStackTrace();
				System.out.println(this.toString() + " reloads!");
			}

		} // end While

	}
	/**
	 * Destruct comparator used by comparing launcher destruct time
	 */
	static class DestructComparator implements Comparator<Launcher>
	{
		@Override
		public int compare(Launcher l1, Launcher l2) {

			return l2.getDestructTime()-l1.getDestructTime();
		}
	}

}
