/**
 *  ��� : System.out.print();
 *  �Է� : Scanner(System.in);
 * 
 * @author Android Hong
 *
 */
public class MemoMain {
	
	public static void main(String[] args) {
		
		// �޸��忡 �����ϴ� Model
		//Model model = new Model();
		
		// DB���� ������ �ϴ� Model
		ModelWithDB model = new ModelWithDB();
		View view = new View();
		
		Controller controller = new Controller(model, view);
		controller.process();
	}
}

