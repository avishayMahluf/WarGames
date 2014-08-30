package Main;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;

import WarWeapons.Destructor;
import WarWeapons.Launcher;
import WarWeapons.Missile;
import WarWeapons.Destructor.Type;

/**
 * 
 * @author Kosta Lazarev & Omri Glam
 *
 */
public class Menu {

	private War war;
	private Scanner s;
	private String[] menuList = { 	"Add launcher destructor",
									"Add missile destructor", 
									"Add launcher",
									"Launch missile",
									"Intercept launcher",
									"Intercept missile",
									"Show statistics",
									"End war game" };
	/**
	 * Menu constructor
	 * Displays console menu for actions
	 * 
	 * @param war
	 */
	public Menu(War war) {
		this.war = war;
		s = new Scanner(System.in);
		
		while (war.isStarted()) {
			System.out.println("\n");
			for (int i = 0; i < menuList.length; i++) {
				System.out.println((i + 1) + " - " + menuList[i]);
			}
			int action = s.nextInt();
			switch (action) {
			case 1:
				addLauncherDestructor();
				break;
			case 2:
				addMissileDestructor();
				break;
			case 3:
				addLauncher();
				break;
			case 4:
				missileLunch();
				break;
			case 5:
				launcherIntercept();
				break;
			case 6:
				missileIntercept();
				break;
			case 7:
				showStatistics();
				break;
			case 8:
				endWarGame();
				break;

			default:
				break;
			}
		}
	}
/**
 * Add Missile launcher destructor to current war
 */
	public void addMissileLauncherDestructor() {
		System.out.println("Add new missile launcher destructor");
		System.out.println("Enter type (ship | plane)");
		String dType = s.nextLine();
		try{
		Destructor.Type.valueOf(dType.trim());
		war.addLauncherDestructor(dType);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.err.println("Wrong type");
		}
	}

	/**
	 * Menu function Adds missile destructor to the war
	 */
	public void addMissileDestructor() {
		System.out.println("Adding missile destructor");
		System.out.printf("Enter id number : ");
		try {
			war.addMissileDestructor(s.nextInt());
			System.out.println("\nMissile launcher destructor added");
		} catch (Exception e) {
			System.out.println("Failed to add missile launcher destructor");
			e.printStackTrace();
		}
	}
	/**
	 * Add Launcher to current war
	 */
	public void addLauncher() {
		System.out.println("Add new missile launcher");
		System.out.print("Enter id: L");
		int lId;
		try{
			lId = s.nextInt();
			war.addLauncher(lId);
			System.out.println("Launcher was created successfully");
		} catch (Exception e) {
			System.err.println("WRONG INPUT: Enter numbers only!");
			s.nextLine();
		}
		
	}

	/**
	 * Adding launcher destructor by using id input from user
	 */
	public void addLauncherDestructor() {
		System.out.println("Add launcher destructor");
		System.out.printf("Enter type :\n plane or ship \n ");
		String lType = s.next();
		try{
			Destructor.Type.valueOf(lType);

			try {
				war.addLauncherDestructor(lType);
				System.out.println("\nLauncher destructor added");
			} catch (Exception e) {
				System.out.println("Failed to add launcher destructor");
				//e.printStackTrace();
			}
		} catch ( IllegalArgumentException ee)  {
			System.out.println("No such type of destructor");
		}
	}

	/**
	 * Lunch missile, "takes time"
	 */
	public void missileLunch() {
		boolean hasLaunchers=false;
		System.out.println("Launch missile");

		for (Launcher launcher : war.getMissileLaunchers()) {
			if (!launcher.isDestroyed()){
				hasLaunchers=true;
				System.out.println(launcher.getLauncherId() + " : launcher");
			}

		}
		if(hasLaunchers){
			System.out.print("Enter launcher id: L");
	
			int lId = s.nextInt();
			Launcher l;
			try{
			l = war.getMissileLaunchers().get(
					war.getMissileLaunchers().indexOf(new Launcher("L" + lId)));
			} catch (IndexOutOfBoundsException e){
				System.err.println("Launcher id was not found");
				return;
			}
			try{
			if (!l.isDestroyed()){
				System.out.print("New missile id: M");
				int mId = s.nextInt();
				System.out.print("Fly time: ");
				int mFlyTime = s.nextInt();
				System.out.print("Damage: ");
				int mDamage = s.nextInt();
				System.out.print("Destenation: ");
				String mDest = s.next();
				l.addMissile(new Missile("M" + mId, mDest, 0, mFlyTime, mDamage, l));
				System.out.println("Missile #M"+ mId + " to " + mDest + " was added to launch queue successfully");
			}else {
				System.err.println("Launcher is destroyed!");
			}
			
		}catch(Exception e){
			System.err.println("WRONG INPUT: Only numbers!");
		}
			
		}else {
			System.err.println("All launchers are destroyed");
		}
	}

	/**
	 * Launcher destroyer, "takes time" destroys only visible launchers
	 */
	public void launcherIntercept() {
		boolean visbleLaunchers=false;
		List<Launcher> launchers = war.getMissileLaunchers();
		System.out.println("Vissble missile launchers:");
		for (Launcher launcher : launchers) {
			if (!launcher.isHidden() && !launcher.isDestroyed()) {
				System.out.println("Launcher id: " + launcher.getLauncherId());
				visbleLaunchers=true;
			}
		}
		if (visbleLaunchers){
			System.out.println("Avileble launcher distractors:");
			for (int i=0; i < war.getMissileLauncherDestructors().size();i++){
				System.out.println((i+1) + "# " + war.getMissileLauncherDestructors().get(i).toString());
			}
			int indexNum = 0;
			System.out.print("Enter the index number to select destractor: #");
			try{
				indexNum = s.nextInt();
				if (!(indexNum > 0 && indexNum <= war.getMissileLauncherDestructors().size())){
					System.err.println("Index was not found");
					return;
				}
			} catch (Exception e) {}
			System.out.printf("Enter Launcher id: L");
			try {
				String lId = "L" + s.nextInt();
				
				war.getMissileLauncherDestructors().get(indexNum-1).addDestructLauncher(launchers.get(launchers.indexOf(new Launcher(lId)))); // a complicated way to do a easy thing...
				System.out.println("Target added to destructor");
				
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Launcher was not found!");
			}
		} else {
			System.out.println("Didn't find any launchers");
		}

	}

	/**
	 * Missile intercept, "takes time"
	 */
	public void missileIntercept() {

		war.updateInAirMissiles();
		int i = 1;
		if (war.getInAirMissiles().size() > 0) {
			for (Missile m : war.getInAirMissiles()) {
				System.out.println("Missile id:" + m.getMissileId()
						+ " in flight");
			}
			System.out.printf("Enter missile id to intercept: M");
			s.nextLine(); // clean buffer
			int mId;
			try{
				mId= s.nextInt();
			}catch (Exception e){
				System.err.println("Id must be a number");
				return;
			}
			

			Missile selectedMissile = Missile.getMissileFromList(
					war.getInAirMissiles(), "M" + mId);
			System.out.println("Destractors id that are availble:");
			for (Destructor d : war.getMissileDestructors()) {
				System.out.println("#" + i++ + " - " + d.getDestructorId());
			}
			System.out.printf("Enter index number: #");
			int dId = s.nextInt();
			try{
				Destructor selectedDestractor = war.getMissileDestructors().get(
					dId - 1);
			
				selectedDestractor.addDestructMissile(selectedMissile);
				System.out.println(selectedMissile.toString() + " was set for interception");

			} catch (Exception e) {
				System.err.println("Id was not found");
			}
		} else {
			System.out.println("No missiles in the air");
		}
	}

	/**
	 * Show war statistics
	 */
	public void showStatistics() {
		System.out.printf(war.getStatistics().toString());
		}

	/**
	 * Ends war and shows statistics
	 */
	public void endWarGame() {

		showStatistics();
		war.getLogger().log(Level.INFO, "War Ended");
		for(Handler h:war.getLogger().getHandlers()){
			h.close();
		}
		s.close();
		System.out.println("ISIS send its best regards...");
		//war.endWar();
		System.exit(0);
		
	}
}
