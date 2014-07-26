import java.util.ArrayList;
import java.util.List;


public class Launcher extends Thread {

	private List<Missile> missiles;
	private String id;
	private boolean isHidden;
	private boolean isDestroyed;
	private int destructTime;
	
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
	
	
	public Launcher(String Id,boolean IsHidden){
		this.id = Id;
		this.isHidden = IsHidden;
		isDestroyed=false;
		missiles = new ArrayList<Missile>();
	}
	
	/**
	 * Adds missile to luncher
	 * @param m Missile
	 */
	public void addMissile(Missile m){
		missiles.add(m);
	}
	public List<Missile> getMissiles(){
		return missiles;
	}
	public Missile getMissile(String id){
		Missile m = null;
		for (Missile missile : missiles) {
			if (missile.equals(id))
				m = missile;
		}
		return m;
	}
	/**
	 * Get launcher by id from list
	 * @param list
	 * @param id
	 * @return launcher
	 */
	public static Launcher getLauncher(List <Launcher> list, String id){
		Launcher l = null;
		for (Launcher launcher : list) {
			if (launcher.equals(id))
				l=launcher;
		}
		return l;
	}

	public void removeMissile(Missile m)
	{
		missiles.remove(m.getId());
		
	}
	@Override
	public boolean equals(Object obj) {

		
		
		return ((String)obj).equals(id);
	}

	
	@Override
	/**
	 * 
	 */
	public void run() {
		
		while (!isDestroyed){
			for (Missile missile : missiles) {
				if (!missile.isAlive() && !missile.isStarted()){
					missile.setLock(Lock);
					missile.start();
				}
//				synchronized (this) {
//					try {
//						wait();
//					} catch (InterruptedException e) {
//					
//						e.printStackTrace();
//					}
//				}
				
				
			}
		}
		

		
	}
	
	
}
