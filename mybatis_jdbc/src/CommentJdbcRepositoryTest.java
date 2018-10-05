import java.util.Scanner;

import ldg.mybatis.repository.session.CommentJdbcService;

public class CommentJdbcRepositoryTest {
	public static void main(String[] args) {
		CommentJdbcService commentJdbcService = new CommentJdbcService();
		System.out.println("번호를 입력하세요.");
		System.out.println("1.입력 2.검색 3.수정 4.조건 검색 5.삭제 6.종료");
		Scanner sc = new Scanner(System.in);
		int num = Integer.parseInt(sc.nextLine());
		switch(num) {
			case 1: 
				Long commentNo = Long.parseLong(sc.nextLine());
				commentJdbcService.SelectCommentByPrimaryKey(commentNo);
				break;
			case 2: 
				break;
			case 3: 
				break;
			case 4: 
				break;
			case 5: 
				break;
			case 6: System.exit(0);
		}
	}
}
