package Main;

import java.util.ArrayList;
import java.util.List;

import WarWeapons.Launcher;


public class WarUtility {

	public static ArrayList<String> getLauncherId(War war) {
		
		List<Launcher> missileLaunchers  = war.getMissileLaunchers();
		int size_launcher = missileLaunchers.size();
		ArrayList<String> launcherId = new ArrayList<String>();
		for (int i = 0; i < size_launcher; i++) {
			Launcher l = missileLaunchers.get(i);
			launcherId.add(l.getLauncherId());
			}
		return launcherId;
	}
	
}

