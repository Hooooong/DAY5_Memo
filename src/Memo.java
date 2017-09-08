/**
 * Memo Å¬·¡½º
 * 
 * @author Android Hong
 *
 */
public class Memo{
	private int no;
	private String name;
	private String content;
	private long dateTime;
	
	public Memo() {
		
	}
	
	public Memo(int no, String name, String content, long dateTime) {
		super();
		this.no = no;
		this.name = name;
		this.content = content;
		this.dateTime = dateTime;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	
}
