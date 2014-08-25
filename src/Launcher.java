import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Launcher extends Thread {

	private static final int MIN_PEEK = 3000;
	private static final int MAX_PEEK = 9000;
	
	private static Logger logger;
	private PriorityQueue<Missile> missiles;
	private String id;
	private boolean isHidden;
	private boolean isDestroyed;
	private int destructTime;
	private Timer peekTimer;
	private Statistics stats;
	private FileHandler fileHandler;
	
	
	/**
	 * Launcher lock
	 */
	public Object Lock = new Object();

	public int getDestructTime() {
		return destructTime;
	}

	public void setDestructTime(int destructTime) {
		this.destructTime = destructTime;
	}

	public boolean isHidden() {
		return isHidden;
	}

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

	public Launcher(String Id) {

		isDestroyed = false;
		Comparator<Missile> comparator = new Missile.MissileLaunchComparator();
		missiles = new PriorityQueue<Missile>(comparator);

		this.id = Id;
		this.isHidden = true;

	}

	/**
	 * 
	 * @param Id
	 * @param IsHidden
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
	 * @param m
	 *            Missile
	 */
	public void addMissile(Missile m) {
		missiles.add(m);
	}

	public List<Missile> getMissiles() {

		return new ArrayList<Missile>(missiles);
	}

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
		peekTimer = new Timer();
		logger.log(Level.INFO, this.toString() + " is visible",this);
		isHidden = false;
		int peekTime = MIN_PEEK + (int) (Math.random() * MAX_PEEK);
		peekTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				isHidden = true;
				logger.log(Level.INFO, this.toString() + " is hidden",this);
				this.cancel();

			}
		}, peekTime, War.TIMER_TICK);

	}

	@Override
	/**
	 * 
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
						logger.log(Level.INFO,toStringMissile(m),this);
						m.start();
						stats.addMissileLaunch();
						synchronized (this) {
							try {
								wait();
								peek();
								wait();
								missiles.poll();
							} catch (InterruptedException e) {

								e.printStackTrace();
								System.err.println("missile was interputed");
							}
						}
					} 

				}
				sleep(100);

			} catch (Exception e) {
				System.err.println("Launcher " + id + " reloads!");
			}

		} // end While

	}
	private String toStringMissile(Missile m) {
		
		return null;
	}
	/**
	 * 
	 * @author Kosta & Omri 
	 * 
	 *
	 */
	static class DestructComparator implements Comparator<Launcher>
	{

	@Override
	public int compare(Launcher l1, Launcher l2) {
	
		return l2.getDestructTime()-l1.getDestructTime();
	}

	
}

}
