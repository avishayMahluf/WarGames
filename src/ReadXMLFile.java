import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
 
public class ReadXMLFile {
 
  public ReadXMLFile() {
 
    try {
 
	File fXmlFile = new File("war.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	doc.getDocumentElement().normalize();
 
	System.out.println("Load element :" + doc.getDocumentElement().getNodeName());
 
	NodeList nMissileLaunchers = doc.getElementsByTagName("missileLaunchers");
 
	System.out.println("----------------------------");
	System.out.println("missileLaunchers:");
	NodeList nLaunchers = doc.getElementsByTagName("launcher");
	for (int i = 0; i < nLaunchers.getLength(); i++) {
 
		
		
		Node nLauncher = nLaunchers.item(i);
		
		System.out.println("\nCurrent Element :" + nLauncher.getNodeName());
 
		if (nLauncher.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nLauncher;
			
			
			System.out.println("Launcher id : " + eElement.getAttribute("id"));
			System.out.println("Launcher isHidden : " + eElement.getAttribute("isHidden"));
			
			Launcher launcher = new Launcher(eElement.getAttribute("id"), Boolean.parseBoolean(eElement.getAttribute("isHidden")));
			NodeList nMissiles = eElement.getElementsByTagName("missile");
			for (int j=0;j < nMissiles.getLength() ; j++) {
				launcher.addMissile(new Missile(Id, Destination, LaunchTime, FlyTime, Damage));
				System.out.println("missile : " + ((Element) (nMissiles.item(j))).getAttribute("id"));
				System.out.println("	destination :	 " + ((Element) (nMissiles.item(j))).getAttribute("destination"));
				System.out.println("	launchTime 	:	 " + ((Element) (nMissiles.item(j))).getAttribute("launchTime"));
				System.out.println("	flyTime 	:	 " + ((Element) (nMissiles.item(j))).getAttribute("flyTime"));
				System.out.println("	damage 		:	 " + ((Element) (nMissiles.item(j))).getAttribute("damage"));
			}
//			System.out.println("missile : " + eElement.getElementsByTagName("missile").item(0).getAttributes());
//			System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
//			System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
//			System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
 
		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
 
}