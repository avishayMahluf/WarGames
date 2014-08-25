import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import jdk.jfr.events.ExceptionThrownEvent;

public class Destructor extends Thread {

	private PriorityQueue<Launcher> destructdLauncher;
	private PriorityQueue<Missile> destructdMissile;
	private Object LockMissile;
	private Object LockLuncher;
	private String id;
	private boolean isActive;
	private Statistics stats;
	private final static String DEAFULT_ID = "D00";

	public enum Type {
		plane, ship, iron_dome
	};

	private Type dType;

	public Destructor() {
		this.id = DEAFULT_ID; // Default id
		LockMissile = new Object();
	}

	
	public Destructor(String dId) {
		super();
		destructdMissile = new PriorityQueue<Missile>(
				(Comparator<? super Missile>) (new Missile.MissileDestructComparator()));
		this.id = dId;
		dType = Type.iron_dome;
		LockMissile = new Object();
		isActive = true;

	}

	public Destructor(Type type) {
		super();
		destructdLauncher = new PriorityQueue<Launcher>(
				(Comparator<? super Launcher>) (new Launcher.DestructComparator()));
		this.dType = type;
		LockLuncher = new Object();
		isActive = true;
	}

	public Destructor(String dId, Statistics stats) {
		super();
		destructdMissile = new PriorityQueue<Missile>(
				(Comparator<? super Missile>) (new Missile.MissileDestructComparator()));
		this.id = dId;
		dType = Type.iron_dome;
		LockMissile = new Object();
		isActive = true;
		this.stats = stats;
	}

	public Destructor(Type type, Statistics stats) {
		super();
		destructdLauncher = new PriorityQueue<Launcher>(
				(Comparator<? super Launcher>) (new Launcher.DestructComparator()));
		this.dType = type;
		LockLuncher = new Object();
		isActive = true;
		this.stats = stats;
	}

	public void setStatistics(Statistics s) {
		stats = s;
	}

	public void addDestructLauncher(Launcher l) {
		destructdLauncher.add(l);
	}

	public void addDestructMissile(Missile m) {
		destructdMissile.add(m);
	}

	public String getDestructorId() {
		return id;
	}

	public void run() {
		try {
			if (dType == Type.iron_dome) {
				MissileDestructorRun();
			} else {
				LauncherDestructorRun();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void MissileDestructorRun() {
		while (isActive) {
			try {
				Missile m = destructdMissile.peek();

				if (m != null) {

					if (m.getDestructAfterLaunch() == War.WarTimeInSeconds) {
						System.out.println("Trying to intercept "
								+ m.toString());
						intercept(m);
						destructdMissile.poll();
					}

				}
				sleep(100);
			} catch (Exception e) {

			}
		}

	}

	private void LauncherDestructorRun() throws InterruptedException {
		while (isActive) {
			try {
				Launcher l = destructdLauncher.peek();
				if (l.getDestructTime() == War.WarTimeInSeconds) {
					System.out.println("Trieng to distroy " + l.toString());
					if (l.destroyLauncher()) {
						System.out.println(l.toString() + " Distroyed!!");
						if (stats != null) {
							stats.addDeistroidLauncher();
						}
					} else {
						System.out.println("Fail to distroy " + l.toString());
					}
					destructdLauncher.poll();
				}
			} catch (Exception e) {

			}
			sleep(500);
		}
	}

	public boolean intercept(Missile m) {
		if (m.Intercep()) {
			System.out
					.println(this.toString() + " Intercepted " + m.toString());
			if (stats != null) {
				stats.addMissileIntercept();
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Destructor type:" + this.dType;
	}

}
