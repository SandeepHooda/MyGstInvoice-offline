package misc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class Utils {

	
	public static final SimpleDateFormat dateFormatDDMonYYYYhm = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	
	public static void setTimeZone(){
		dateFormatDDMonYYYYhm.setTimeZone(TimeZone.getTimeZone("IST"));
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}
	public static Calendar getCalender(){
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("IST"));
		return cal;
	}
	public static String getFileContent(String filePath){
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			
			return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getFileContent(String filePath, int lineNoFrom,  int lineNoTo){
		List<String> lines = new ArrayList<String>();
		int currentLineNo = 1;
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (currentLineNo >=lineNoFrom && currentLineNo <=lineNoTo){
					lines.add(line);
				}
				currentLineNo++;
			}
			
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
