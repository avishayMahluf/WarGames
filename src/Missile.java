import java.util.List;
import java.util.Comparator;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.org.glassfish.external.statistics.Statistic;

public class Missile extends Thread {
	public enum State {
		OnGround, Flying, Intercepted, Hit
	}
	private static Logger logger;
	private State missileState;
	private String id;
	private String destination;
	private int launcTime;
	private int flyTime;
	private int damage;
	private int destructAfterLaunch;
	private Object Lock;
	private boolean started;
	private Launcher launcher;
	private FileHandler fileHandler;
	private Statistics statistics;
	
	
	public boolean isStarted() {
		return started;
	}
/**
 * Creating a missile, this missile will have to be set fileHandler to work correctly
 * Use the setFileHandlr method.
 * @param Id
 * @param Destination
 * @param LaunchTime
 * @param FlyTime
 * @param Damage
 * @param TheLauncher
 * @param missileState
 * @param 
 * @param logger
 */
	public Missile(String Id, String Destination, int LaunchTime, int FlyTime,
			int Damage, Launcher TheLauncher,FileHandler fileHandler){
		this.id = Id;
		this.launcTime = LaunchTime;
		this.flyTime = FlyTime;
		this.damage = Damage;
		this.destination = Destination;
		this.launcher = TheLauncher;
		missileState = State.OnGround;
		try{
			this.fileHandler = fileHandler;
			logger = Logger.getLogger("War.Logger");
			logger.addHandler(fileHandler);
			
		}catch(Exception e){
			System.out.println("Missile #"+this.id +" logger didn't started");
		}
	}

/**
 * Intercept (Hit) in air missile
 * @return True if succeeded
 */
	public boolean Intercep() {
		if (missileState == State.Flying) {
			missileState = State.Intercepted;
			interrupt();
			return true;
		}
		return false;
	}

	public void setStatistics(Statistics s){
		statistics = s;
	}
	public static Missile getMissile(List<Launcher> lList, String id) {

		Missile m = null;
		for (Launcher launcher : lList) {
			m = launcher.getMissile(id);
			if (m != null)
				return m;
		}
		return m;
	}

	public int getDestructAfterLaunch() {
		return destructAfterLaunch;
	}

	public void setDestructAfterLaunch(int destructAfterLaunch) {
		this.destructAfterLaunch = destructAfterLaunch;
	}

	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	public String getMissileId() {
		return id;
	}

	public String getDestination() {
		return destination;
	}

	public int getLauncTime() {
		return launcTime;
	}

	public int getFlyTime() {
		return flyTime;
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public boolean equals(Object id) {

		return this.id.equals(((String) id));
	}

	/**
	 * Returns current missile state
	 * 
	 * @return OnGround,Flying,Intercepted,Hit
	 */
	public State getMissileState() {
		return missileState;
	}

	public void setLock(Object lock) {
		this.Lock = lock;

	}
/**
 * Returns missile from list by Id
 * @param l Missile List
 * @param id the id
 * @return The Missile with that id
 */
	public static Missile getMissileFromList(List<Missile> l, String id) {
		for (Missile missile : l) {
			if (missile.equals(id))
				return missile;
		}
		return null;
	}

	@Override
	public void run() {
		started = true;
		try {

			synchronized (Lock) {
				logger.log(Level.INFO,toString() + " Launched",this);
				synchronized (launcher) {
					launcher.notify();
				}
				missileState = State.Flying;

				sleep(flyTime * 1000);
				if (missileState != State.Intercepted) {
					missileState = State.Hit;
					logger.log(Level.INFO,"Missile id #" 
							+ this.id + " Hit target "
							+ destination,this);
					if(statistics!=null){
						statistics.addMissileHit();
						try {
							statistics.addDamage(damage);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
				} else {
					logger.log(Level.INFO,this.toString() + " didnt hit target good job!",this);
				}
				// Notify that was a hit i
				synchronized (launcher) {
					launcher.notify();
				}

			}
			launcher.removeMissile(this);

		} catch (InterruptedException e) {
			// missile was intercepted
			synchronized (launcher) {
				launcher.notify();
			}
			logger.log(Level.INFO,this.toString() + " Was Intercepted!",this);
			// e.printStackTrace();
		}

	}

	@Override
	public String toString() {

		return "Missile: #" + this.id + " To " + getDestination();
	}

	/**
	 * @author Kosta Lazarev
	 * 
	 *         Comparator implementation for using priorty queue for launching
	 *         the missiles by time order. The compare uses the launching time
	 *         of each missile.
	 *
	 */
	static class MissileLaunchComparator implements Comparator<Missile> {

		@Override
		public int compare(Missile o1, Missile o2) {

			return o1.getLauncTime() - o2.getLauncTime();
		}
	}
		/**
		 * 
		 * @author Kosta Lazarev
		 * 
		 *         Comparator implementation for using priorty queue for launching
		 *         the missiles by time order. The compare uses the launching time
		 *         of each missile.
		 *
		 */
	static class MissileDestructComparator implements Comparator<Missile> {

			@Override
			public int compare(Missile o1, Missile o2) {

				return o1.getDestructAfterLaunch() - o2.getDestructAfterLaunch();
			}

	}
}// end Missile
