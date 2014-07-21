import java.util.ArrayList;
import java.util.List;


public class Destructor extends Thread {

	private List<Launcher> destructdLauncher;
	private List<Missile> destructdMissile;
	public enum Type {plane,ship};
	
public Destructor(String dId) {
		destructdLauncher = new ArrayList<Launcher>();
	}

public void addDestructLauncher(Launcher l){
	destructdLauncher.add(l);
}
public void addDestructMissile(Missile m){
	destructdMissile.add(m);
}

}
