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
	private final static String DEAFULT_ID = "D00";

	public enum Type {
		plane, ship,iron_dome
	};

	private Type dType;

	public Destructor() {
		this.id = DEAFULT_ID; // Default id
		LockMissile = new Object();
	}

	public Destructor(String dId) {
		super();
		destructdMissile 	= new PriorityQueue<Missile>((Comparator<? super Missile>)(new Missile.MissileDestructComparator()));
		this.id 			= dId;
		dType				= Type.iron_dome;
		LockMissile 		= new Object();
	}

	public Destructor(Type type) {
		super();
		destructdLauncher 	= new PriorityQueue<Launcher>((Comparator<? super Launcher>)(new Launcher.DestructComparator()));
		this.dType 			= type;
		LockLuncher 		= new Object();
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
		if (dType == Type.iron_dome){
			MissileDestructorRun();
		} else {
			LauncherDestructorRun();
		}
	}
	private void MissileDestructorRun(){
		while(isActive){
			try {
				//Lau m = missiles.peek();
//
//				if (m != null) {
//
//					if (m.getLauncTime() == War.WarTimeInSeconds
//							|| m.getLauncTime() == 0) {
//						m.setLock(Lock);
//						m.start();
//						synchronized (this) {
//							try {
//								wait();
//								peek();
//								wait();
//								missiles.poll();
//							} catch (InterruptedException e) {
//
//								e.printStackTrace();
//								System.out.println("missile was interputed");
//							}
//						}
//					} 
//
//				}
//				sleep(100);
		} catch (Exception e) {}
		}
	}
	private void LauncherDestructorRun(){
		while(isActive){
			try {
				Launcher l = destructdLauncher.poll();
				if (l.getDestructTime() == War.WarTimeInSeconds){
					
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	public boolean intercept(Missile m) {

		if (m.getMissileState() == Missile.State.Flying) {
			m.Intercep();
			if (m.getMissileState() == Missile.State.Intercepted) {
				return true;
			}
		}
		return false;
	}

}
