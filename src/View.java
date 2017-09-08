import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * View Ŭ����
 * 
 * @author Android Hong
 *
 */
public class View{
	
	/*
	 * Ű���� �Է¹޴� �޼ҵ� 
	 */
	public Memo create(Scanner scanner) {
		Memo memo = new Memo();

		println("�̸��� �Է��ϼ��� : ");
		memo.setName(scanner.nextLine());
		println("������ �Է��ϼ��� : ");
		memo.setContent(scanner.nextLine());
		memo.setDateTime(System.currentTimeMillis());
		
		// �� �ϳ��� ������ �޸𸮸� ����ҷ� �̵�.
		//model.create(memo);
		return memo;
	}
	
	/*
	 * ���� �д� �޼ҵ�
	 */
	public void read(Memo memo) {
		
		println("-------------------");
		println("�� ��ȣ	: " + memo.getNo());
		println("�ۼ���	: " + memo.getName());
		println("����	: " + memo.getContent());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		println("��¥	: " + format.format(memo.getDateTime()));
		println("-------------------");
			
	}
	
	/*
	 * ���� �����ϴ� �޼ҵ�
	 */
	
	public Memo update(Scanner scanner) {
		Memo memo = new Memo();
		
		println("�̸��� �Է��ϼ��� : ");
		memo.setName(scanner.nextLine());
		println("������ �Է��ϼ��� : ");
		memo.setContent(scanner.nextLine());
		memo.setDateTime(System.currentTimeMillis());
		
		return memo;
	}
	
	/*
	 * �� ����� ����ϴ� �޼ҵ�
	 */
	public void showList(List<Memo> memoList) {
		for(Memo memo : memoList) {
			print("�� ��ȣ : " + memo.getNo());
			print(" | �ۼ��� : " + memo.getName());
			println(" | ���� : " + memo.getContent());
		}
	}
	
	/*
	 * ��ȣ�Է�
	 */
	public int readNo(Scanner scanner) {
		
		println("��ȣ�� �Է��ϼ���");
		String no = scanner.nextLine();
		
		if(!checkNumber(no)) {
			return 0;
		}else {
			return Integer.parseInt(no);
		}
	}
	
	/*
	 * ���� ���� �� ���
	 */
	public void noList() {
		println("���� �����ϴ�.");
	}
	
	/*
	 * ���ڸ� �Է����� �ʾ��� ��
	 */
	public void noInputNumber() {
		println("���ڸ� �Է��� �� �ֽ��ϴ�.");
	}
	
	
	public void print(String text) {
		System.out.print(text);
	}
	
	public void println(String text) {
		System.out.println(text);
	}
	
	/**
	 * ���ڸ� �Է¹��� �� �ְ� �˻��ϴ� �޼ҵ�
	 * 
	 * @param no
	 * @return
	 */
	public boolean checkNumber(String no) {
		boolean flag = Pattern.matches("^[0-9]*$", no); 
		return flag;
	}
}