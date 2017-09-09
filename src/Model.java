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
 * Model Ŭ����
 * 
 * @author Android Hong
 *
 */
public class Model {
	
	// Database ���
	private final String DB_DIR = "c:\\workspace\\java\\Memo\\database";
	// File ���
	private final String DB_FILE = "memo.txt";
	// database
	private File database = null;
	// ������
	private final String COLUM_SEP = "::";
	

	public Model() {
		// new �ϴ� ���� �� ������ ����ȴ�.
		File dir = new File(DB_DIR);
		if(!dir.exists()) {
			//���丮�� ������
			
			// dir.mkdirs();
			// dir.mkdir();				
			
			/*
			 * file��ü.mkdirs();
			 * file��ü.mkdir();
			 * 
			 * mkdir() �� ��ΰ� ������ error �� �߻�������
			 * mkdirs() �� ��ΰ� ������ �� ��θ� ������ �ش�. 
			 */
			dir.mkdirs();
		}
		
		/*
		 * File.separator �� ���� ����
		 * 
		 * OS ���� separator �� �ٸ���
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
	 * ���� ����
	 * 
	 * @param memo
	 */
	public void create(Memo memo) {
		// �۹�ȣ
		// �� ��ȣ�� Max�� ã�� �� �ִ� �Լ��� �ʿ�
		memo.setNo(getMaxNum() + 1);
		
		/*
		 * JDK 1.7 ���� try-with-resource ���� ���ܼ�
		 * try (Stream ��ü;) �� ������ �ڵ����� close()�� ���ش�.
		 * ������ ��� Stream �� ���� �� �ִ°� �ƴϰ� AutoCloseable �� implements�� ��ü�� ���� �� �ִ�.
		 *  
		 *  
		 *  FileOutputStream�� Byte���� ����ϴ� Stream
		 *  			|
		 *  	OutputStreamWriter ( byte������ char���� �� ��ȯ���ִ� ��� Stream )
		 *  			|
		 *  BufferedWriter�� Char�� ����ϴ� Stream (���� Stream)
		 *  2 byte�� �а� ���⿡�� �ʹ� ������ ������  �������� byte �� �д� ���� �����ִ� ���� Buffer�̴�.
		 *  
		 *  Stream �� �������־�� ù��°�� ������ ��� ������.
		 *  
		 */
		try(// 1. �ۼ��ϴ� ��Ʈ���� ����.	
			FileOutputStream fos = new FileOutputStream(database, true);
			) {
			// 2. ��Ʈ���� �߰� ó���Ѵ�. (Text �� Encoding �� �����ϴ� �۾�)	
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			// 3. ���� ó��
			BufferedWriter bw = new BufferedWriter(osw);
			
			// 4. ������ ������ �����ڷ� �и��Ͽ� ������ ���ڿ��� �ٲ۴�.
			String saveData = memo.getNo() + COLUM_SEP + memo.getName() + COLUM_SEP + memo.getContent() + COLUM_SEP + memo.getDateTime() + "\n";
			bw.append(saveData);
			bw.flush();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		// ���� ������ Exception ���� ó�����൵ �ȴ�.
		/*catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * ���� �о��
	 * 
	 * @param no
	 * @return
	 */
	public Memo read(int no) {
		// ���ڸ� �Է����� ���
		Memo returnMemo = new Memo();
		
		try( 
			// 1. �д� ��Ʈ���� ����	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. ���� ���� ���ڵ��� �ٲ��ִ� Wrapper Class �� ���
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. ����ó��
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			while((row = br.readLine()) != null) {
				String tempRow[] = row.split(COLUM_SEP);
				// 1::�����::�ȳ��ϼ���::191010293
				// tempRow[0] : 1
				// tempRow[1] : �����
				// tempRow[2] : �ȳ��ϼ���
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
	 * ���� ����
	 * 
	 * @param memo
	 */
	public void update(int no, Memo memo) {
	
		String tempString = "";
		
		try( 
			// 1. �д� ��Ʈ���� ����	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. ���� ���� ���ڵ��� �ٲ��ִ� Wrapper Class �� ���
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. ����ó��
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			
			//�������α��� ���� 
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
		
		try(// 1. �ۼ��ϴ� ��Ʈ���� ����.	
			FileOutputStream fos = new FileOutputStream(database, false);
			) {
			// 2. ��Ʈ���� �߰� ó���Ѵ�. (Text �� Encoding �� �����ϴ� �۾�)	
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			// 3. ���� ó��
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
	 * ���� ����
	 * 
	 * @param no
	 */
	public void delete(int no) {
		String tempString = "";
		
		try( 
			// 1. �д� ��Ʈ���� ����	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. ���� ���� ���ڵ��� �ٲ��ִ� Wrapper Class �� ���
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. ����ó��
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			
			//�������α��� ���� 
			while((row = br.readLine()) != null) {
				String tempRow[] = row.split(COLUM_SEP);
				if(Integer.parseInt(tempRow[0]) != no) {
					tempString += row+"\n";
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		try(// 1. �ۼ��ϴ� ��Ʈ���� ����.	
			FileOutputStream fos = new FileOutputStream(database, false);
			) {
			// 2. ��Ʈ���� �߰� ó���Ѵ�. (Text �� Encoding �� �����ϴ� �۾�)	
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			// 3. ���� ó��
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
	 * ��ü ���� �о��
	 * 
	 * @return
	 */
	public List<Memo> showList() {
		// �� �����
		List<Memo> list = new ArrayList<>();
		
		int count = 0 ;
		
		try( 
			// 1. �д� ��Ʈ���� ����	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. ���� ���� ���ڵ��� �ٲ��ִ� Wrapper Class �� ���
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. ����ó��
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
	 * ���� ������ �������� �޼ҵ�
	 * 
	 * @return
	 */
	public int getListCount() {
		int count = 0;
		try( 
			// 1. �д� ��Ʈ���� ����	
			FileInputStream fis = new FileInputStream(database);) {
			
			// 2. ���� ���� ���ڵ��� �ٲ��ִ� Wrapper Class �� ���
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. ����ó��
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
	 * ���� Max��ȣ�� �������� �޼ҵ�
	 * 
	 * @return
	 */
	public int getMaxNum() {
		// Max ��ȣ
		int maxNum = 0;
		// ���� ��ȣ
		int tempNum = 0;
		try( 
			// 1. �д� ��Ʈ���� ����	
			FileInputStream fis = new FileInputStream(database);
			) {
			
			// 2. ���� ���� ���ڵ��� �ٲ��ִ� Wrapper Class �� ���
			InputStreamReader isr = new InputStreamReader(fis);
			// 3. ����ó��
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			while((row = br.readLine()) != null) {
				String tempRow[] = row.split(COLUM_SEP);
				// ���� ��ȣ�� ��´�.
				tempNum= Integer.parseInt(tempRow[0]);
				
				if(maxNum < tempNum) {
					// Max ��ȣ�� ���� ��ȣ�� ������
					// Max ��ȣ�� ��ü�Ѵ�.
					maxNum = tempNum;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return maxNum;
	}
}

