import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Launcher extends Thread {

	private List<Missile> missiles;
	private String id;
	private boolean isHidden;
	private boolean isDestroyed;
	private int destructTime;
	private Timer peekTimer;
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

	public boolean destroyLauncher(){
		if(!isHidden){
			isDestroyed=true;
			return true;
		}
		
		return false;
	}

	
	public Launcher(String Id){
		
		isDestroyed=false;
		missiles = new ArrayList<Missile>();
		this.id = Id;
		this.isHidden = true;
		
	}
	
	/**
	 * 
	 * @param Id
	 * @param IsHidden
	 */
	public Launcher(String Id,boolean IsHidden){
		
		isDestroyed=false;
		missiles = new ArrayList<Missile>();
		this.id = Id;
		this.isHidden = IsHidden;
		
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

		if (obj instanceof Launcher){
			return ((Launcher)obj).getLauncherId().equals(id);
		}
		
			return ((String)obj).equals(id);
		
	}
	/**
	 * Makes launcher visible for attacking for random
	 * period of time
	 */
	protected void peek(){
		peekTimer = new Timer();
		System.out.println("Launcher "+ id + " is visible");
		isHidden=false;
		int peekTime = 3000 + (int)(Math.random()*9000); 
		peekTimer.scheduleAtFixedRate(new TimerTask() {
					
					@Override
					public void run() {
						isHidden=true;
						System.out.println("Launcher "+ id + " is hidden");
						this.cancel();
						
					}
				}, peekTime, 1000);
		
	}
	
	@Override
	/**
	 * 
	 */
	public void run() {
		
		while (!isDestroyed){
			try{
			for (Missile missile : missiles) {
				if (!missile.isAlive() && !missile.isStarted()){
					missile.setLock(Lock);
					missile.start();
				
					synchronized (this) {
						try {
							wait();
							peek();
						} catch (InterruptedException e) {
						
							e.printStackTrace();
						}
					}
				
				}
					
				}
				
			} catch (Exception e){
				System.err.println("Launcher " + id + " reloads!");
			}
				
		}
		

		
	}
	
	
}