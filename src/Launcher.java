import java.util.ArrayList;
import java.util.List;


public class Launcher extends Thread {

	private List<Missile> missiles;
	private String id;
	private boolean isHidden;
	private int destructTime;
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
		missiles = new ArrayList<Missile>();
	}
	
	/**
	 * Adds missile to luncher
	 * @param m Missile
	 */
	public void addMissile(Missile m){
		missiles.add(m);
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

	@Override
	public boolean equals(Object obj) {
		
		return ((String)obj).equals(id);
	}
	
}
