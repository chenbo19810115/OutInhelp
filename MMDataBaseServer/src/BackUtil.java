
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;


public class BackUtil {

	public void backdataInfo(String filename)
	{
		String backid = UUID.randomUUID().toString();
		BackFileMgn.setProperty(backid, filename);
	}
	
	public List<BackEx> getBackList()
	{
		List<BackEx> ls = new LinkedList<BackEx>();
		Properties ps = BackFileMgn.getProperty();
		Set<Map.Entry<Object, Object>> entrySet = ps.entrySet();
	    for (Map.Entry<Object, Object> entry : entrySet) {  
	        String backid = entry.getKey().toString();
	        String filename = entry.getValue().toString();
	        BackEx backex = new BackEx();
	        backex.setBackfile(filename);
	        backex.setBackid(backid);
	        ls.add(backex);
	    }  
		
		return ls;
	}
}
