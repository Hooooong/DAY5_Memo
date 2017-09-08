import java.util.List;
import java.util.Scanner;

public class Controller {
	
	
	//Model model;
	
	ModelWithDB model;
	
	View view;
	

	/**
	 * 생성자
	 * 
	 * @param model
	 * @param view
	 */
	/*public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}*/
	public Controller(ModelWithDB model, View view) {
		this.model = model;
		this.view = view;
	}

	public void process() {
		// 입력을 받아 처리하는 도구
		Scanner scanner = new Scanner(System.in);
		/*
		 * 명령어를 입력받아 후속 처리
		 * 
		 * c : CREATE - 쓰기모드 r : READ - 읽기모드 u : UPDATE - 수정모드 d : DELETE - 삭제모드
		 *
		 */
		String command = "";
		Memo memo = null;
		while (!command.equals("exit")) {
			System.out.println("--------------------------- 명령어를 입력하세요 ---------------------------");
			System.out.println("------------- c : 쓰기, r : 읽기, u : 수정, d : 삭제 , l : 목록  -----------");
			System.out.println("---------------------------- exit : 종료 -----------------------------");
			System.out.println("--------------------------------------------------------------------");

			command = scanner.nextLine(); // 키보드의 입력 중에 Enter가 입력 될 때 까지 대기하게 된다.
			// 명령어를 분기처리
			switch (command) {
			case "c":
			case "C":
				memo = view.create(scanner);

				// 메모 데이터의 조작이 필요할 경우 컨트롤러에서 작업한다.
				model.create(memo);
				break;
			case "r":
			case "R":
				if (model.getListCount() == 0) {
					view.noList();
				} else {
					int no = view.readNo(scanner);
					if (no == 0) {
						view.noInputNumber();
					} else {
						memo = model.read(no);
						if (memo != null) {
							view.read(memo);
						} else {
							view.noList();
						}
					}
				}
				break;
			case "u":
			case "U":
				if (model.getListCount() == 0) {
					view.noList();
				} else {
					int no = view.readNo(scanner);
					if (no == 0) {
						view.noInputNumber();
					} else {
						memo = model.read(no);
						if (memo != null) {
							memo =view.update(scanner);
							model.update(no, memo);
						} else {
							view.noList();
						}

						// 원래는 model 에서 변경하는 작업이 있어야 한다.
						// 하지만 지금 memo 자체를 넘겨주기 때문에 상관은 없을듯..
					}
				}
				break;
			case "d":
			case "D":
				if (model.getListCount() == 0) {
					view.noList();
				} else {
					int no = view.readNo(scanner);
					if (no == 0) {
						view.noInputNumber();
					} else {
						model.delete(no);
					}
				}
				break;
			case "l":
			case "L":
				List<Memo> memoList = model.showList();
				if (memoList == null) {
					view.noList();
				} else {
					view.showList(memoList);
				}

				break;
			}
		}
		System.out.println("시스템이 종료되었습니다.");
	}
}
