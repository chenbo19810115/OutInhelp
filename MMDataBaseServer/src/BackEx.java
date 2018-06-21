
import java.io.Serializable;

public class BackEx implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 761564277963206279L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private String backid;
	private String backfile;
	public String getBackid() {
		return backid;
	}
	public void setBackid(String backid) {
		this.backid = backid;
	}
	public String getBackfile() {
		return backfile;
	}
	public void setBackfile(String backfile) {
		this.backfile = backfile;
	}
}
