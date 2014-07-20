import java.util.Scanner;

import java.lang.reflect.Method;

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

	public static void main(String[] args) {
		ReadXMLFile xml = new ReadXMLFile();
		showMenu();

	}
	
	public static void showMenu(){
		Scanner s = new Scanner(System.in);
		int methodIndex=0;
		int selectedFunvtion;
		try{
			Class<?> menuClass = Class.forName("Menu");
			
			Method[] menuFunctions = menuClass.getDeclaredMethods();
			
			for (Method m : menuFunctions){
				System.out.printf("%d)	%s \n",methodIndex++,m.getName());
			}
			selectedFunvtion= s.nextInt();
			Method selected = menuFunctions[selectedFunvtion];
		
			selected.invoke(menuClass.newInstance());
		} catch (ClassNotFoundException e) {
			
		} catch (Exception ee)
		{}
		
		
	}
}
