import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.reflect.Method;

import com.sun.jmx.snmp.tasks.Task;

/*
 * WarGames main class
 */


/**
 * 
 * Main program class that lunches the system into activity
 * 
 * @author by Kosta Lazarev
 * @version 16/07/2014
 */
public class War {
	private List<Destructor> missileDestructors;
	private List<Destructor> missileLauncherDestructors;
	private List<Launcher> missileLaunchers ;
	private List<Missile> inAirMissiles;
	private Timer WarTimer;
	private boolean started;
	public static int WarTimeInSeconds;
	
	public War(){
		WarTimeInSeconds=0;
		missileDestructors = new ArrayList<Destructor>();
		missileLauncherDestructors=new ArrayList<Destructor>();
		missileLaunchers = new ArrayList<Launcher>();
		inAirMissiles = new ArrayList<Missile>();
		WarTimer = new Timer();
		started=false;
		
	}
	/**
	 * Start seconds counting
	 */
	protected void startWarTimer(){
		System.out.println("War Clock Started");
		WarTimer.scheduleAtFixedRate(new TimerTask() {
					
					@Override
					public void run() {
						WarTimeInSeconds++;
						//System.out.println(WarTimeInSeconds);
						if (WarTimeInSeconds==Integer.MAX_VALUE){
							endWar();
						}
						
					}
				}, 0, 3000);
		started=true;
	}
	protected void endWar() {
		// TODO Auto-generated method stub
		
	}

	private void startWar(){
		startWarTimer();
		for (Launcher mLauncer : missileLaunchers) {
			mLauncer.start();
		}
		for (Destructor destructor : missileDestructors) {
			destructor.start();
		}
		
	}
	

	public static void main(String[] args) {
		
		War WarGames = new War();
		
		try{
		
			ReadXMLFile.getData("war.xml", WarGames.getMissileLaunchers(),
					WarGames.getMissileDestructors(),
					WarGames.getMissileLauncherDestructors());
			
			WarGames.startWar();
			WarGames.showMenu();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		

	}
	
	public List<Missile> getInAirMissiles() {
		return inAirMissiles;
	}
	public List<Destructor> getMissileDestructors() {
		return missileDestructors;
	}

	public List<Destructor> getMissileLauncherDestructors() {
		return missileLauncherDestructors;
	}

	public List<Launcher> getMissileLaunchers() {
		return missileLaunchers;
	}
	
	public boolean isStarted() {
		return started;
	}
	public void showMenu(){
		String[] menuList = {"Add launcher destructor",
				"Add missile destructor",	
				"Add launcher",
				"Launch missile",
				"Intercept launcher",
				"Intercept missile",
				"Show statistics",
				"End war game"};
				
		Menu function = new Menu(this);
		Scanner s = new Scanner(System.in);
		while(this.isStarted()){
			for(int i=0; i < menuList.length ; i++){
				System.out.println((i+1) +" - " + menuList[i]);
			}
			int action = s.nextInt();
			switch (action) {
			case 1:
				function.addLauncherDestructor();
				break;
			case 2:
				function.addMissileDestructor();
				break;
			case 3:
				function.addLauncher();
				break;
			case 4:
				function.missileLunch();
				break;
			case 5:
				function.launcherIntercept();
				break;
			case 6:
				function.missileIntercept();
				break;
			case 7:
				function.showStatistics();
				break;
			case 8:
				function.endWarGame();
				break;


			default:
				break;
			}
		}
		
	}
	
	public void updateInAirMissiles(){
		for (Launcher l : missileLaunchers) {
			for (Missile missile : l.getMissiles()) {
				if(missile.getMissileState() == Missile.State.Flying){
					inAirMissiles.add(missile);				}
			}
		}
	}
}
