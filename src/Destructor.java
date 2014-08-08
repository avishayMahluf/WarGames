import java.util.ArrayList;
import java.util.List;

public class Destructor extends Thread {

	private List<Launcher> destructdLauncher;
	private List<Missile> destructdMissile;
	private Object Lock;
	private String id;
	private final static String DEAFULT_ID = "D00";
	public enum Type {
		plane, ship
	};

	private Type dType;

	public Destructor() {
		this.id = DEAFULT_ID; // Default id
		Lock = new Object();
	}

	public Destructor(String dId) {
		super();
		destructdMissile = new ArrayList<Missile>();
		this.id = dId;
	}

	public Destructor(Type type) {
		super();
		destructdLauncher = new ArrayList<Launcher>();
		this.dType = type;
	}

	public void addDestructLauncher(Launcher l) {
		destructdLauncher.add(l);
	}

	public void addDestructMissile(Missile m) {
		destructdMissile.add(m);
	}
	public String getDestructorId(){
		return id;
	}
	public void run() {
		
	}

	public boolean intercept(Missile m) {

		if(m.getMissileState() == Missile.State.Flying){
			m.Intercep();
			if (m.getMissileState() == Missile.State.Intercepted){
				return true;
			}
		}
		return false;
	}

}
