package ldg.mybatis.repository.session;

import ldg.mybatis.model.Comment;

public class CommentJdbcService {
	private CommentJdbcRepository commentJdbcRepository = new CommentJdbcRepository();
	private Comment comment;
	public void SelectCommentByPrimaryKey(Long commentNo) {
		comment = commentJdbcRepository.selectCommentByPrimaryKey(commentNo);
		System.out.println(comment.getCommentNo());
		System.out.println(comment.getUserId());
		System.out.println(comment.getCommentContent());
		System.out.println(comment.getRegDate());
	}
}
