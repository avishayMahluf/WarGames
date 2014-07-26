import java.util.Scanner;

/**
 * 
 * @author Kosta Lazarev
 *
 */
public class Menu {

	private War war;

	public Menu(War war) {
		this.war = war;
	}
	public void addMissileLauncherDestructor(){

	}
	/**
	 * Menu function
	 * Adds missile destructor to the war
	 */
	public void addMissileDestructor(){
		System.out.println("Adding missile destructor");
	}

	public void addLauncher()
	{

	}
	public void addLauncherDestructor()
	{

	}

	public void missileLunch()
	{

	}
	/**
	 * Launcher destroyer, "takes time"
	 * destroys only visible launchers
	 */
	public void launcherIntercept(){
		
	}
	/**
	 * Missile intercept, "takes time"
	 */
	public void missileIntercept(){
		Scanner s = new Scanner(System.in);
		war.updateInAirMissiles();
		int i=1;
		for (Missile m : war.getInAirMissiles()) {
			System.out.println("Missile id:" + m.getMissileId() + " in flight" );
		}
		System.out.printf("Enter missile id to intercept: M");
		int mId = s.nextInt();
		
		Missile selectedMissile =Missile.getMissileFromList(war.getInAirMissiles(),"M"+mId);
		System.out.println("Destractors id that are availble:");
		for (Destructor d :	war.getMissileDestructors()) {
			System.out.println("#" + i++ +" - " + d.getDestructorId());
		}
		System.out.printf("Enter : #");
		int dId = s.nextInt();
		Destructor selectedDestractor = war.getMissileDestructors().get(dId-1);
		if (selectedDestractor.intercept(selectedMissile))
			System.out.println("MISSILE " + selectedMissile.getMissileId() + " INTERCEPTED!");
		else
			System.out.println("MISSILE " + selectedMissile.getMissileId() + " MISSED!");
		
	}
	/**
	 * Show war statistics
	 */
	public void showStatistics(){
		
	}
	/**
	 * Ends war and shows statistics
	 */
	public void endWarGame(){
		
		showStatistics();
	}
}
