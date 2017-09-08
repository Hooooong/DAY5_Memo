import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * View 클래스
 * 
 * @author Android Hong
 *
 */
public class View{
	
	/*
	 * 키보드 입력받는 메소드 
	 */
	public Memo create(Scanner scanner) {
		Memo memo = new Memo();

		println("이름을 입력하세요 : ");
		memo.setName(scanner.nextLine());
		println("내용을 입력하세요 : ");
		memo.setContent(scanner.nextLine());
		memo.setDateTime(System.currentTimeMillis());
		
		// 글 하나를 저장한 메모리를 저장소로 이동.
		//model.create(memo);
		return memo;
	}
	
	/*
	 * 글을 읽는 메소드
	 */
	public void read(Memo memo) {
		
		println("-------------------");
		println("글 번호	: " + memo.getNo());
		println("작성자	: " + memo.getName());
		println("내용	: " + memo.getContent());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		println("날짜	: " + format.format(memo.getDateTime()));
		println("-------------------");
			
	}
	
	/*
	 * 글을 수정하는 메소드
	 */
	
	public Memo update(Scanner scanner) {
		Memo memo = new Memo();
		
		println("이름을 입력하세요 : ");
		memo.setName(scanner.nextLine());
		println("내용을 입력하세요 : ");
		memo.setContent(scanner.nextLine());
		memo.setDateTime(System.currentTimeMillis());
		
		return memo;
	}
	
	/*
	 * 글 목록을 출력하는 메소드
	 */
	public void showList(List<Memo> memoList) {
		for(Memo memo : memoList) {
			print("글 번호 : " + memo.getNo());
			print(" | 작성자 : " + memo.getName());
			println(" | 내용 : " + memo.getContent());
		}
	}
	
	/*
	 * 번호입력
	 */
	public int readNo(Scanner scanner) {
		
		println("번호를 입력하세요");
		String no = scanner.nextLine();
		
		if(!checkNumber(no)) {
			return 0;
		}else {
			return Integer.parseInt(no);
		}
	}
	
	/*
	 * 글이 없을 때 출력
	 */
	public void noList() {
		println("글이 없습니다.");
	}
	
	/*
	 * 숫자를 입력하지 않았을 때
	 */
	public void noInputNumber() {
		println("숫자만 입력할 수 있습니다.");
	}
	
	
	public void print(String text) {
		System.out.print(text);
	}
	
	public void println(String text) {
		System.out.println(text);
	}
	
	/**
	 * 숫자만 입력받을 수 있게 검사하는 메소드
	 * 
	 * @param no
	 * @return
	 */
	public boolean checkNumber(String no) {
		boolean flag = Pattern.matches("^[0-9]*$", no); 
		return flag;
	}
}