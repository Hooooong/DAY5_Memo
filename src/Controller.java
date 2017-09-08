import java.util.List;
import java.util.Scanner;

public class Controller {
	
	
	//Model model;
	
	ModelWithDB model;
	
	View view;
	

	/**
	 * ������
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
		// �Է��� �޾� ó���ϴ� ����
		Scanner scanner = new Scanner(System.in);
		/*
		 * ��ɾ �Է¹޾� �ļ� ó��
		 * 
		 * c : CREATE - ������ r : READ - �б��� u : UPDATE - ������� d : DELETE - �������
		 *
		 */
		String command = "";
		Memo memo = null;
		while (!command.equals("exit")) {
			System.out.println("--------------------------- ��ɾ �Է��ϼ��� ---------------------------");
			System.out.println("------------- c : ����, r : �б�, u : ����, d : ���� , l : ���  -----------");
			System.out.println("---------------------------- exit : ���� -----------------------------");
			System.out.println("--------------------------------------------------------------------");

			command = scanner.nextLine(); // Ű������ �Է� �߿� Enter�� �Է� �� �� ���� ����ϰ� �ȴ�.
			// ��ɾ �б�ó��
			switch (command) {
			case "c":
			case "C":
				memo = view.create(scanner);

				// �޸� �������� ������ �ʿ��� ��� ��Ʈ�ѷ����� �۾��Ѵ�.
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

						// ������ model ���� �����ϴ� �۾��� �־�� �Ѵ�.
						// ������ ���� memo ��ü�� �Ѱ��ֱ� ������ ����� ������..
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
		System.out.println("�ý����� ����Ǿ����ϴ�.");
	}
}
