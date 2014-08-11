import java.util.List;


public class Missile extends Thread {
	public enum State {OnGround,Flying,Intercepted,Hit}
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
	
	public boolean isStarted() {
		return started;
	}
	public Missile(String Id,String Destination, int LaunchTime,int FlyTime,int Damage,Launcher TheLauncher){
		this.id = Id;
		this.launcTime = LaunchTime;
		this.flyTime = FlyTime;
		this.damage = Damage;
		this.destination = Destination;
		this.launcher=TheLauncher;
		missileState=State.OnGround;
	}
	
	public boolean Intercep(){
		if (missileState == State.Flying){
			missileState=State.Intercepted;
			return true;
		}
		return false;
	}
	
	public static Missile getMissile (List<Launcher> lList,String id){
		
		Missile m =  null;
		for (Launcher launcher : lList) {
			m = launcher.getMissile(id);
			if ( m != null)
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
		
		return this.id.equals(((String)id));
	}
	/**
	 * Returns current missile state
	 * 
	 * @return OnGround,Flying,Intercepted,Hit
	 */
	public State getMissileState(){
		return missileState;
	}

	public void setLock(Object lock) {
		this.Lock =lock;
		
	}
	
	public static Missile getMissileFromList(List<Missile> l, String id)
	{
		for (Missile missile : l) {
			if (missile.equals(id))
				return missile;
		}
		return null;
	}
	@Override
	public void run() {
		started=true;
		try {
			
			sleep(getFlyTime()*War.TIMER_TICK);
				
			synchronized (Lock) {
				System.out.printf("%d : Missile id %s Launched\n",War.WarTimeInSeconds, id);
				synchronized (launcher){
					launcher.notify();
				}
				missileState = State.Flying;
				
				sleep(flyTime * 1000);
				if (missileState != State.Intercepted){
					missileState = State.Hit;
					System.out.printf("%d : Missile id %s Hit target %s!! \n",War.WarTimeInSeconds, id,destination);
				} else {
					System.out.printf("%d : Missile id %s didnt hit target %s good job! \n",War.WarTimeInSeconds, id,destination);
				}
				
				
			}
			launcher.removeMissile(this);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}

}
