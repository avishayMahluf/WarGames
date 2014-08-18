import java.util.Vector;
import java.util.logging.Filter;
import java.util.logging.LogRecord;




public class objectFilter implements Filter{
	
	private Vector<Object> objectVector = new Vector<Object>();
	
	

	public objectFilter(Object toList) {
		this.objectVector.add(toList);
	}



	@Override
	public boolean isLoggable(LogRecord record) {
		if(record.getParameters()!=null){
			for(int i=0; i<objectVector.size(); i++){
				for(int j=0; j<record.getParameters().length; j++){
					if(objectVector.get(i) == record.getParameters()[j]){
						return true;
					}
				}
			}
		}
		return false;
	}
	

}
