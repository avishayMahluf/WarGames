import java.util.ArrayList;
import java.util.List;


public class Launcher {

	private List<Missile> missiles;
	private String id;
	private boolean isHidden;
	
	public Launcher(String Id,boolean IsHidden){
		this.id = Id;
		this.isHidden = IsHidden;
		missiles = new ArrayList<Missile>();
	}
	
	/**
	 * Adds missile to luncher
	 * @param m Missile
	 */
	public void addMissile(Missile m){
		missiles.add(m);
	}
}
