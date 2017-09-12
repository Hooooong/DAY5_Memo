import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Model Ŭ����
 * 
 * @author Android Hong
 *
 */
public class ModelWithDB {
	
	private static String URL = "jdbc:mysql://localhost:3306/memo";
	private static String USER_ID= "root";
	private static String USER_PASSWORD ="mysql";

	
	// ������
	public ModelWithDB() {
		/*
		 * Ư�� ��ǻ�͸� ã�� ���� �ּ�ü��
		 * IP : 213.
		 * url : naver.com
		 * 
		 * Ư�����α׷��� �Ҵ�Ǵ� ���� ����
		 * port = 1 ~ 6������...
		 * 		  2000�� ���� �̹� ǥ������ ���ǰ� �մ�.
		 * 
		 * ���� : IP + PortNumber
		 * 
		 * ǥ�� ��������
		 * http:// IP(�ּ�) : 80
		 * 
		 * Ư�����α׷��� ������ �ϱ� ���� �ּ� ü��
		 * ���������̸� ://IP(�ּ�):PortNumber
		 * 
		 */
		
		// ���������̸�//IP�ּ�:PortNumber/DB ��Ű�� �̸�
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���� ����
	 * 
	 * @param memo
	 */
	public void create(Memo memo) {
		// 1. �����ͺ��̽� ����
		// DB�� �����ϱ� ���� Connection ��ü
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			
			// 2. ������ ����
			// 2.1  ������ ����
			StringBuffer insertBuffer = new StringBuffer();
			insertBuffer.append("\n	INSERT INTO MEMO(NAME, CONTENT, DATETIME)		");
			insertBuffer.append("\n	VALUES( ?, ?, ?)								");
		
			// 2.2 ������ ���డ���� ���·� ������ش�.
			stmt = con.prepareStatement(insertBuffer.toString());
			
			// 2.3 ����ǥ�� ���� ����
			// ������ ���� ���� ��ȣ�� 0�� �ƴ� 1�� ������ �ȴ�.
			stmt.setString(1, memo.getName());
			stmt.setString(2, memo.getContent());
			stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			
			/**
			 * 
			 * INSERT, UPDATE, DELETE �� ��쿡 ����ϴ� �޼ҵ�
			 * PreparedStatement��ü.executeUpdate();
			 * RETURN : int
			 * 
			 */
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���� �о��
	 * 
	 * @param no
	 * @return
	 */
	public Memo read(int no) {
		
		Memo memo = null;
		
		// 1. �����ͺ��̽� ����
		// DB�� �����ϱ� ���� Connection ��ü
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			// 2. ������ ����
			// 2.1  ������ ����
			StringBuffer selectBuffer = new StringBuffer();
			selectBuffer.append("\n	SELECT NO, NAME, CONTENT, DATETIME	");
			selectBuffer.append("\n FROM MEMO							");
			selectBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 ������ ���డ���� ���·� ������ش�.
			stmt = con.prepareStatement(selectBuffer.toString());
			
			// 2.3 ����ǥ�� ���� ����
			stmt.setInt(1, no);
			
			/*
			 * SELECT �� ��쿡 ����ϴ� �޼ҵ�
			 * PreparedStatement��ü.executeQuery();
			 * RETURN : ResultSet
			 */
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				memo = new Memo();
				memo.setNo(rs.getInt("NO"));
				memo.setName(rs.getString("NAME"));
				memo.setContent(rs.getString("CONTENT"));
				memo.setDateTime(rs.getLong("DATETIME"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memo;
	}
	
	/**
	 * ���� ����
	 * 
	 * @param memo
	 */
	public void update(int no, Memo memo) {
		// 1. �����ͺ��̽� ����
		// DB�� �����ϱ� ���� Connection ��ü
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			
			// 2. ������ ����
			// 2.1  ������ ����
			StringBuffer updateBuffer = new StringBuffer();
			updateBuffer.append("\n UPDATE MEMO SET NAME= ?				");
			updateBuffer.append("\n ,CONTENT = ?						");
			updateBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 ������ ���డ���� ���·� ������ش�.
			stmt = con.prepareStatement(updateBuffer.toString());
			
			// 2.3 ����ǥ�� ���� ����
			stmt.setString(1, memo.getName());
			stmt.setString(2, memo.getContent());
			stmt.setInt(3, no);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ���� ����
	 * 
	 * @param no
	 */
	public void delete(int no) {
		// 1. �����ͺ��̽� ����
		// DB�� �����ϱ� ���� Connection ��ü
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			
			// 2. ������ ����
			// 2.1  ������ ����
			StringBuffer updateBuffer = new StringBuffer();
			updateBuffer.append("\n DELETE FROM MEMO					");
			updateBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 ������ ���డ���� ���·� ������ش�.
			stmt = con.prepareStatement(updateBuffer.toString());
			
			// 2.3 ����ǥ�� ���� ����
			stmt.setInt(1, no);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ü ���� �о��
	 * 
	 * @return
	 */
	public List<Memo> showList() {
		ArrayList<Memo> arrayList= new ArrayList<Memo>();
		// 1. �����ͺ��̽� ����
		// DB�� �����ϱ� ���� Connection ��ü
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			/*
			 * Statement �� 
			 * executeQuery() �� executeUpdate() �� �����ϴ� ������ �Ķ���ͷ� SQL���� �����ϴµ�, 
			 * �� �� ���޵Ǵ� SQL ���� �ϼ��� ���·� �Ѵ��� ���� SQL ������ �ľ��ϱ� ����. ������, SQL���� �����ϴ� �������� �Ź� �������� �ϱ� ������ ���ɻ� �̽��� �ִ�. 
			 * 
			 * PreparedStatement �� 
			 * reparedStatement �� �̸��������� �� �� �ֵ��� �غ�� Statement �̴�.
			 * �� �غ�� ������(Parsing) �� �̾߱��ϸ�, �������� �̸� �Ǿ��ֱ� ������ Statement�� ���� ���ɻ� ������ �ִ�. 
			 * ���� �������� �Բ� ���Ǹ� ������ �Ǵµ�, ?�κп��� ��ȭ�� �־� ���������� SQL�� �����ϱ� ������ �Ѵ��� ���� SQL ������ �ľ��ϱ�� ��ƴ�.
			 * 
			 * �ܼ� SELECT �� ��쿡�� Statement �� ����ϴ� ���� ������,
			 * ��뷮 ������ ó�� �� PreparedStatement�� �� ����. �� ������ Cache�� ��� ������ �� �ֱ� ������
			 * ��뷮 ó���� �ݺ������� ������ ��� DB�� ���ϸ� ���� �� �ֱ� �����̴�.
			 * 
			 */
			Statement stmt = null;
			ResultSet rs = null;
			
			// 2. ������ ����
			// 2.1  ������ ����
			StringBuffer selectBuffer = new StringBuffer();
			selectBuffer.append("\n SELECT * FROM MEMO			");
		
			// 2.2 ������ ���డ���� ���·� ������ش�.
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(selectBuffer.toString());
			
			while(rs.next()) {
				Memo memo = new Memo();
				memo.setNo(rs.getInt("NO"));
				memo.setName(rs.getString("NAME"));
				memo.setContent(rs.getString("CONTENT"));
				memo.setDateTime(rs.getLong("DATETIME"));
				
				arrayList.add(memo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}
	
	/**
	 * ���� ������ �������� �޼ҵ�
	 * 
	 * @return
	 */
	public int getListCount() {
		int count = 0;
		
		// 1. �����ͺ��̽� ����
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			// DB�� �����ϱ� ���� Connection ��ü
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			// 2. ������ ����
			// 2.1  ������ ����
			StringBuffer selectBuffer = new StringBuffer();
			selectBuffer.append("\n	SELECT COUNT(*) as COUNT 			");
			selectBuffer.append("\n FROM MEMO							");
		
			// 2.2 ������ ���డ���� ���·� ������ش�.
			stmt = con.prepareStatement(selectBuffer.toString());
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("COUNT");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
}

