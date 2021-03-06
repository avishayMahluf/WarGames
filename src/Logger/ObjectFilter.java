package Logger;
import java.util.Vector;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * ObjectFilter class helps the logger to write in the right object log file
 * <br><br>
 * Functions links:<br>
 * {@link #addFilter(Object)}<br>
 * {@link #isLoggable(LogRecord)}<br>
 * 
 * @author Kosta Lazarev & Omri Glam
 *
 */
public class ObjectFilter implements Filter {

	private Vector<Object> objectVector = new Vector<Object>();

	public ObjectFilter(Object toList) {
		this.objectVector.add(toList);
	}
	
	public void addFilter(Object toFilter) {
		this.objectVector.add(toFilter);
	}

	@Override
	public boolean isLoggable(LogRecord record) {
		if (record.getParameters() != null) {
			for (int i = 0; i < objectVector.size(); i++) {
				for (int j = 0; j < record.getParameters().length; j++) {
					if (objectVector.get(i) == record.getParameters()[j]) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
