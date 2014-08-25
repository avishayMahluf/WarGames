import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class WarFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {

		StringBuffer buffer = new StringBuffer(1000);
		LocalDateTime ldate = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter
				.ofPattern("dd/MM/yy HH:mm:ss");
		buffer.append(ldate.format(dateFormat));
		buffer.append(" ");
		buffer.append(formatMessage(record));
		buffer.append("\n");

		return buffer.toString();
	}

}
