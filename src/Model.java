import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Model 클래스
 * 
 * @author Android Hong
 *
 */
public class Model {
	
	// Database 경로
	private final String DB_DIR = "c:\\workspace\\java\\Memo\\database";
	// File 경로
	private final String DB_FILE = "memo.txt";
	// database
	private File database = null;
	// 구분자
	private final String COLUM_SEP = "::";
	

	public Model() {
		// new 하는 순간 이 영역이 실행된다.
		File dir = new File(DB_DIR);
		if(!dir.exists()) {
			//디렉토리가 없으면
			
			// dir.mkdirs();
			// dir.mkdir();				
			
			/*
			 * file객체.mkdirs();
			 * file객체.mkdir();
			 * 
			 * mkdir() 는 경로가 없으면 error 가 발생하지만
			 * mkdirs() 는 경로가 없으면 그 경로를 생성해 준다. 
			 */
			dir.mkdirs();
		}
		
		/*
		 * File.separator 를 쓰는 이유
		 * 
		 * OS 마다 separator 가 다르다
		 * windows = \
		 * mac, Linux, Unix = /
		 */
		File file = new File(DB_DIR + File.separator + DB_FILE);
		
		if(!file.exists()) {
			try {
				
				file.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		database = file;
	}
	
	/**
	 * 글을 생성
	 * 
	 * @param memo
	 */
	public void create(Memo memo) {
		// 글번호
		// 글 번호를 Max로 찾을 수 있는 함수가 필요
		memo.setNo(getMaxNum() + 1);
		
		/*
		 * JDK 1.7 부터 try-with-resource 문이 생겨서
		 * try (Stream 객체;) 를 넣으면 자동으로 close()를 해준다.
		 * 하지만 모든 Stream 을 넣을 수 있는게 아니고 AutoCloseable 을 implements한 객체만 넣을 수 있다.
		 *  
		 *  
		 *  FileOutputStream은 Byte단위 출력하는 Stream
		 *  			|
		 *  	OutputStreamWriter ( byte단위를 char단위 로 변환해주는 출력 Stream )
		 *  			|
		 *  BufferedWriter는 Char를 출력하는 Stream (보조 Stream)
		 *  2 byte씩 읽고 쓰기에는 너무 느리기 때문에  복수개의 byte 를 읽는 것을 도와주는 것이 Buffer이다.
		 *  
		 *  Stream 이 연관되있어면 첫번째가 닫히면 모두 닫힌다.
		 *  
		 */
		try(// 1. 작성하는 스트림을 연다.	
			FileOutputStream fos = new FileOutputStream(database, true);
			) {
			// 2. 스트림을 중간 처리한다. (Text 의 Encoding 을 변경하는 작업)	
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			// 3. 버퍼 처리
			BufferedWriter bw = new BufferedWriter(osw);
			
			// 4. 저장할 내용을 구분자로 분리하여 한줄의 문자열로 바꾼다.
			String saveData = memo.getNo() + COLUM_SEP + memo.getName() + COLUM_SEP + memo.getContent() + COLUM_SEP + memo.getDateTime() + "\n";
			bw.append(saveData);
			bw.flush();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		// 상위 예외인 Exception 으로 처리해줘도 된다.
		/*catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 글을 읽어옴
	 * 
	 * @param no
	 * @return
	 */
	public Memo read(int no) {
		// 숫자를 입력했을 경우
		Memo returnMemo = new Memo();
		
		try( 
			// 1. 읽는 스트림을 연다	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. 실제 파일 인코딩을 바꿔주는 Wrapper Class 를 사용
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			while((row = br.readLine()) != null) {
				String tempRow[] = row.split(COLUM_SEP);
				// 1::이흥기::안녕하세요::191010293
				// tempRow[0] : 1
				// tempRow[1] : 이흥기
				// tempRow[2] : 안녕하세요
				// tempRow[3] : 191010293;
				if(Integer.parseInt(tempRow[0]) == no) {
					returnMemo.setNo(Integer.parseInt(tempRow[0]));
					returnMemo.setName(tempRow[1]);
					returnMemo.setContent(tempRow[2]);
					returnMemo.setDateTime(Long.parseLong(tempRow[3]));
					break;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return returnMemo;
	}
	
	/**
	 * 글을 수정
	 * 
	 * @param memo
	 */
	public void update(int no, Memo memo) {
	
		String tempString = "";
		
		try( 
			// 1. 읽는 스트림을 연다	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. 실제 파일 인코딩을 바꿔주는 Wrapper Class 를 사용
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			
			//상위라인까지 읽음 
			while((row = br.readLine()) != null) {
				String tempRow[] = row.split(COLUM_SEP);
				if(Integer.parseInt(tempRow[0]) != no) {
					tempString += row+"\n";
					
				}else{
					tempString += no + COLUM_SEP + memo.getName() + COLUM_SEP + memo.getContent() + COLUM_SEP + memo.getDateTime() + "\n";
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try(// 1. 작성하는 스트림을 연다.	
			FileOutputStream fos = new FileOutputStream(database, false);
			) {
			// 2. 스트림을 중간 처리한다. (Text 의 Encoding 을 변경하는 작업)	
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			// 3. 버퍼 처리
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.append(tempString);
			bw.flush();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 글을 삭제
	 * 
	 * @param no
	 */
	public void delete(int no) {
		String tempString = "";
		
		try( 
			// 1. 읽는 스트림을 연다	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. 실제 파일 인코딩을 바꿔주는 Wrapper Class 를 사용
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			
			//상위라인까지 읽음 
			while((row = br.readLine()) != null) {
				String tempRow[] = row.split(COLUM_SEP);
				if(Integer.parseInt(tempRow[0]) != no) {
					tempString += row+"\n";
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		try(// 1. 작성하는 스트림을 연다.	
			FileOutputStream fos = new FileOutputStream(database, false);
			) {
			// 2. 스트림을 중간 처리한다. (Text 의 Encoding 을 변경하는 작업)	
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			// 3. 버퍼 처리
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.append(tempString);
			bw.flush();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 전체 글을 읽어옴
	 * 
	 * @return
	 */
	public List<Memo> showList() {
		// 글 저장소
		List<Memo> list = new ArrayList<>();
		
		int count = 0 ;
		
		try( 
			// 1. 읽는 스트림을 연다	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. 실제 파일 인코딩을 바꿔주는 Wrapper Class 를 사용
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			while((row = br.readLine()) != null) {
				
				String tempRow[] = row.split(COLUM_SEP);
				
				Memo memo = new Memo(Integer.parseInt(tempRow[0]), tempRow[1], tempRow[2], Long.parseLong(tempRow[3]));
				list.add(memo);
				count++;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(count == 0 ) {
			return null;
		}else {
			return list;
		}
	}
	
	/**
	 * 글의 갯수를 가져오는 메소드
	 * 
	 * @return
	 */
	public int getListCount() {
		int count = 0;
		try( 
			// 1. 읽는 스트림을 연다	
			FileInputStream fis = new FileInputStream(database);) {
			
			// 2. 실제 파일 인코딩을 바꿔주는 Wrapper Class 를 사용
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);
			
			while((br.readLine()) != null) {
				count++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * 글의 Max번호를 가져오는 메소드
	 * 
	 * @return
	 */
	public int getMaxNum() {
		// Max 번호
		int maxNum = 0;
		// 비교할 번호
		int tempNum = 0;
		try( 
			// 1. 읽는 스트림을 연다	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. 실제 파일 인코딩을 바꿔주는 Wrapper Class 를 사용
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			while((row = br.readLine()) != null) {
				String tempRow[] = row.split(COLUM_SEP);
				// 비교할 번호를 담는다.
				tempNum= Integer.parseInt(tempRow[0]);
				
				if(maxNum < tempNum) {
					// Max 번호가 비교할 번호가 작으면
					// Max 번호를 교체한다.
					maxNum = tempNum;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return maxNum;
	}
}

