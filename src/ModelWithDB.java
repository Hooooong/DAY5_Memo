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
		// DB에 연결하기 위한 Connection 객체
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer insertBuffer = new StringBuffer();
			insertBuffer.append("\n	INSERT INTO MEMO(NAME, CONTENT, DATETIME)		");
			insertBuffer.append("\n	VALUES( ?, ?, ?)								");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(insertBuffer.toString());
			
			// 2.3 물음표에 값을 세팅
			// 주의할 점은 시작 번호가 0이 아닌 1로 시작이 된다.
			stmt.setString(1, memo.getName());
			stmt.setString(2, memo.getContent());
			stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			
			/**
			 * 
			 * INSERT, UPDATE, DELETE 의 경우에 사용하는 메소드
			 * PreparedStatement객체.executeUpdate();
			 * RETURN : int
			 * 
			 */
			stmt.executeUpdate();
			
		} catch (Exception e) {
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
		// DB에 연결하기 위한 Connection 객체
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer selectBuffer = new StringBuffer();
			selectBuffer.append("\n	SELECT NO, NAME, CONTENT, DATETIME	");
			selectBuffer.append("\n FROM MEMO							");
			selectBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(selectBuffer.toString());
			
			// 2.3 물음표에 값을 세팅
			stmt.setInt(1, no);
			
			/*
			 * SELECT 인 경우에 사용하는 메소드
			 * PreparedStatement객체.executeQuery();
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
	 * 글을 수정
	 * 
	 * @param memo
	 */
	public void update(int no, Memo memo) {
		// 1. 데이터베이스 연결
		// DB에 연결하기 위한 Connection 객체
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer updateBuffer = new StringBuffer();
			updateBuffer.append("\n UPDATE MEMO SET NAME= ?				");
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
		// DB에 연결하기 위한 Connection 객체
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			PreparedStatement stmt = null;
			
			// 2. 쿼리를 실행
			// 2.1  쿼리를 생성
			StringBuffer updateBuffer = new StringBuffer();
			updateBuffer.append("\n DELETE FROM MEMO					");
			updateBuffer.append("\n WHERE NO = ?						");
		
			// 2.2 쿼리를 실행가능한 상태로 만들어준다.
			stmt = con.prepareStatement(updateBuffer.toString());
			
			// 2.3 물음표에 값을 세팅
			stmt.setInt(1, no);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
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
		// DB에 연결하기 위한 Connection 객체
		try(Connection con = DriverManager.getConnection(URL, USER_ID, USER_PASSWORD)) {
			
			/*
			 * Statement 는 
			 * executeQuery() 나 executeUpdate() 를 실행하는 시점에 파라미터로 SQL문을 전달하는데, 
			 * 이 때 전달되는 SQL 문은 완성된 형태로 한눈에 무슨 SQL 문인지 파악하기 쉽다. 하지만, SQL문을 수행하는 과정에서 매번 컴파일을 하기 때문에 성능상 이슈가 있다. 
			 * 
			 * PreparedStatement 는 
			 * reparedStatement 은 이름에서부터 알 수 있듯이 준비된 Statement 이다.
			 * 이 준비는 컴파일(Parsing) 을 이야기하며, 컴파일이 미리 되어있기 때문에 Statement에 비해 성능상 이점이 있다. 
			 * 보통 조건절과 함께 사용되며 재사용이 되는데, ?부분에만 변화를 주어 지속적으로 SQL을 수행하기 때문에 한눈에 무슨 SQL 문인지 파악하기는 어렵다.
			 * 
			 * 단순 SELECT 인 경우에는 Statement 를 사용하는 것이 좋지만,
			 * 대용량 데이터 처리 시 PreparedStatement가 더 좋다. 그 이유는 Cache에 담아 재사용할 수 있기 때문에
			 * 대용량 처리를 반복적으로 실행할 경우 DB에 부하를 줄일 수 있기 때문이다.
			 * 
			 */
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
			e.printStackTrace();
		}
		
		return count;
	}
}

