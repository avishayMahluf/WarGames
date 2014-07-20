
public class Missile extends Thread {
	
	private String id;
	private String destination;
	private int launcTime;
	private int flyTime;
	private int damage;
	
	public Missile(String Id,String Destination, int LaunchTime,int FlyTime,int Damage){
		this.id = Id;
		this.launcTime = LaunchTime;
		this.flyTime = FlyTime;
		this.damage = Damage;
		
	}

}
