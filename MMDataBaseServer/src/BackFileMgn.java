
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class BackFileMgn {
	
	private static final String PROPERTIES_DEFAULT = "backfile.properties";
	
	private static String backfile = null;
	
	static {
		
		backfile = System.getProperty("user.dir") + "\\" + PROPERTIES_DEFAULT;
		
	}

	public static Properties getProperty() {

		Properties props = new Properties();
		try {
			InputStream input = new FileInputStream(backfile);
			props.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props;
	}

	public static void setProperty(String backid, String filename) {
		try {
			Properties props = new Properties();
			InputStream input = new FileInputStream(backfile);
			props.load(input);
			input.close();
			
			props.setProperty(backid, filename);
			OutputStream out = new FileOutputStream(backfile);
			props.store(out, null);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
