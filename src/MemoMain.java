/**
 *  출력 : System.out.print();
 *  입력 : Scanner(System.in);
 * 
 * @author Android Hong
 *
 */
public class MemoMain {
	
	public static void main(String[] args) {
		
		// 메모장에 저장하는 Model
		//Model model = new Model();
		
		// DB와의 연결을 하는 Model
		ModelWithDB model = new ModelWithDB();
		View view = new View();
		
		Controller controller = new Controller(model, view);
		controller.process();
	}
}

