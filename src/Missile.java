import java.util.List;


public class Missile extends Thread {
	
	private String id;
	private String destination;
	private int launcTime;
	private int flyTime;
	private int damage;
	private int destructAfterLaunch;
	
	public Missile(String Id,String Destination, int LaunchTime,int FlyTime,int Damage){
		this.id = Id;
		this.launcTime = LaunchTime;
		this.flyTime = FlyTime;
		this.damage = Damage;
		
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

}
