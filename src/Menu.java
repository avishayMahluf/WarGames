import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Kosta Lazarev
 *
 */
public class Menu {

	private War war;
	private Scanner s;

	/**
	 * Menu constructor
	 * 
	 * @param war
	 */
	public Menu(War war) {
		this.war = war;
		s = new Scanner(System.in);
	}

	public void addMissileLauncherDestructor() {

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

	public void addLauncher() {

	}

	/**
	 * Adding launcher destructor by using id input from user
	 */
	public void addLauncherDestructor() {
		System.out.println("Add launcher destructor");
		System.out.printf("Enter type :\n plane or ship \n ");

		try {
			war.addLauncherDestructor(s.next());
			System.out.println("\nLauncher destructor added");
		} catch (Exception e) {
			System.out.println("Failed to add launcher destructor");
			e.printStackTrace();
		}
	}

	/**
	 * Lunch missile, "takes time"
	 */
	public void missileLunch() {

		System.out.println("Launch missile");

		for (Launcher launcher : war.getMissileLaunchers()) {
			System.out.println(launcher.getLauncherId() + " : launcher");

		}
		System.out.println("Enter launcher id: L");

		int lId = s.nextInt();
		Launcher l = war.getMissileLaunchers().get(
				war.getMissileLaunchers().indexOf(new Launcher("L" + lId)));
		System.out.println("New missile id:");
		int mId = s.nextInt();
		System.out.println("fly time:");
		int mFlyTime = s.nextInt();
		System.out.println("Damage:");
		int mDamage = s.nextInt();
		System.out.println("Destenation:");
		String mDest = s.next();
		l.addMissile(new Missile("M" + mId, mDest, 0, mFlyTime, mDamage, l));

	}

	/**
	 * Launcher destroyer, "takes time" destroys only visible launchers
	 */
	public void launcherIntercept() {
		List<Launcher> launchers = war.getMissileLaunchers();
		System.out.println("Vissble missile launchers:");
		for (Launcher launcher : launchers) {
			if (!launcher.isHidden() && !launcher.isDestroyed()) {
				System.out.println("Launcher id: " + launcher.getLauncherId());

			}
			System.out.printf("Enter Launcher id: L");
			try {
				String lId = "L" + s.nextInt();
				if (launchers.get(launchers.indexOf(new Launcher(lId)))
						.destroyLauncher()) {
					System.out.println("Launcher destroyed!!!");
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Launcher was not found!");
			}
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
			int mId = s.nextInt();

			Missile selectedMissile = Missile.getMissileFromList(
					war.getInAirMissiles(), "M" + mId);
			System.out.println("Destractors id that are availble:");
			for (Destructor d : war.getMissileDestructors()) {
				System.out.println("#" + i++ + " - " + d.getDestructorId());
			}
			System.out.printf("Enter : #");
			int dId = s.nextInt();
			Destructor selectedDestractor = war.getMissileDestructors().get(
					dId - 1);
			if (selectedDestractor.intercept(selectedMissile))
				System.out.println("MISSILE " + selectedMissile.getMissileId()
						+ " WAS INTERCEPTED!");
			else
				System.out.println("MISSILE " + selectedMissile.getMissileId()
						+ " WAS MISSED!");
		} else {
			System.out.println("No missiles in the air");
		}
	}

	/**
	 * Show war statistics
	 */
	public void showStatistics() {

	}

	/**
	 * Ends war and shows statistics
	 */
	public void endWarGame() {

		showStatistics();
	}
}
