/**
 * 
 * @author Kosta Lazarev
 *
 */
public abstract class Menu {

	

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
