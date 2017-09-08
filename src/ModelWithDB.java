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
 * Model 클래스
 * 
 * @author Android Hong
 *
 */
public class ModelWithDB {
	
	private static String URL = "jdbc:mysql://localhost:3306/memo";
	private static String USER_ID= "root";
	private static String USER_PASSWORD ="mysql";

	
	// 생성자
	public ModelWithDB() {
		/*
		 * 특정 컴퓨터를 찾기 위한 주소체계
		 * IP : 213.
		 * url : naver.com
		 * 
		 * 특정프로그램에 할당되는 세부 번지
		 * port = 1 ~ 6만번대...
		 * 		  2000대 밑은 이미 표준으로 사용되고 잇다.
		 * 
		 * 소켓 : IP + PortNumber
		 * 
		 * 표준 프로토콜
		 * http:// IP(주소) : 80
		 * 
		 * 특정프로그램에 엑세스 하기 위한 주소 체계
		 * 프로토콜이름 ://IP(주소):PortNumber
		 * 
		 */
		
		// 프로토콜이름//IP주소:PortNumber/DB 스키마 이름
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 글을 생성
	 * 
	 * @param memo
	 */
	public void create(Memo memo) {
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			// DB에 연결하기 위한 Connection 객체
			PreparedStatement stmt = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer insertBuffer = new StringBuffer();
			insertBuffer.append("\n	INSERT INTO MEMO(NAME, CONTENT, DATETIME) 	");
			insertBuffer.append("\n	VALUES( ?, ?, ?)							");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(insertBuffer.toString());
			
			// 2.3 물음표에 값을 세팅
			stmt.setString(1, memo.getName());
			stmt.setString(2, memo.getContent());
			stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 글을 읽어옴
	 * 
	 * @param no
	 * @return
	 */
	public Memo read(int no) {
		
		Memo memo = null;
		
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			// DB에 연결하기 위한 Connection 객체
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer selectBuffer = new StringBuffer();
			selectBuffer.append("\n	SELECT NO, NAME, CONTENT, DATETIME  ");
			selectBuffer.append("\n FROM MEMO							");
			selectBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(selectBuffer.toString());
			
			// 2.3 물음표에 값을 세팅
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				memo = new Memo();
				memo.setNo(rs.getInt("NO"));
				memo.setName(rs.getString("NAME"));
				memo.setContent(rs.getString("CONTENT"));
				memo.setDateTime(rs.getLong("DATETIME"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memo;
	}
	
	/**
	 * 글을 수정
	 * 
	 * @param memo
	 */
	public void update(int no, Memo memo) {
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			// DB에 연결하기 위한 Connection 객체
			PreparedStatement stmt = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer updateBuffer = new StringBuffer();
			updateBuffer.append("\n UPDATE MEMO SET NAME= ? 			");
			updateBuffer.append("\n ,CONTENT = ?						");
			updateBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(updateBuffer.toString());
			
			// 2.3 물음표에 값을 세팅
			stmt.setString(1, memo.getName());
			stmt.setString(2, memo.getContent());
			stmt.setInt(3, no);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 글을 삭제
	 * 
	 * @param no
	 */
	public void delete(int no) {
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			// DB에 연결하기 위한 Connection 객체
			PreparedStatement stmt = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer updateBuffer = new StringBuffer();
			updateBuffer.append("\n DELETE FROM MEMO 					");
			updateBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(updateBuffer.toString());
			
			// 2.3 물음표에 값을 세팅
			stmt.setInt(1, no);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 전체 글을 읽어옴
	 * 
	 * @return
	 */
	public List<Memo> showList() {
		ArrayList<Memo> arrayList= new ArrayList<Memo>();
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			// DB에 연결하기 위한 Connection 객체
			Statement stmt = null;
			ResultSet rs = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer selectBuffer = new StringBuffer();
			selectBuffer.append("\n SELECT * FROM MEMO			");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}
	
	/**
	 * 글의 갯수를 가져오는 메소드
	 * 
	 * @return
	 */
	public int getListCount() {
		int count = 0;
		
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			// DB에 연결하기 위한 Connection 객체
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer selectBuffer = new StringBuffer();
			selectBuffer.append("\n	SELECT COUNT(*) as COUNT 			");
			selectBuffer.append("\n FROM MEMO							");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(selectBuffer.toString());
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("COUNT");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
}

