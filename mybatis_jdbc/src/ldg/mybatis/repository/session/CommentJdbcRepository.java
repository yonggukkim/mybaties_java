package ldg.mybatis.repository.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ldg.mybatis.model.Comment;

public class CommentJdbcRepository {
	private Connection getConnection() {
		try {
		// DBMS정보를 저장, 드라이버 연결
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// URL정보
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "smrit", "oracle");
		}catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
	public Comment selectCommentByPrimaryKey(Long commentNo) {
		Comment comment = null; // dto
		Connection con = null; // db연결 정보
		PreparedStatement stmt = null; // 
		ResultSet rs = null; // select하기 위한
		try {
			con = this.getConnection(); // 자기 자신의 메소드를 사용 할 때는 this.해서 메소드를 불러 오는 것이 좋다.
			String sql = "select comment_no, user_id, comment_content, reg_date from comment1 where comment_no = ?";
			stmt = con.prepareStatement(sql); // dbms에 sql문을 전달
			stmt.setLong(1, commentNo);
			rs = stmt.executeQuery();
			if(rs.next()) {
				comment = new Comment();
				comment.setCommentNo(rs.getLong("comment_no"));
				comment.setUserId(rs.getString("user_id"));
				comment.setRegDate(rs.getDate("reg_date"));
				comment.setCommentContent(rs.getString("comment_content"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs == null) try { rs.close(); } catch (SQLException e) {}
			if(stmt == null) try { stmt.close(); } catch (SQLException e) {}
			if(con == null) try { con.close(); } catch (SQLException e) {}
		}
		return comment;
	}
	public Integer insertComment(Comment comment) {
		Integer i = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into comment1(comment_no, user_id, comment_content, reg_date) "
					+ "values(?,?,?,?)";
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, comment.getCommentNo());
			pstmt.setString(2, comment.getUserId());
			pstmt.setString(3, comment.getCommentContent());
			pstmt.setDate(4, new java.sql.Date(comment.getRegDate().getTime()));
			i = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt == null) try { pstmt.close(); } catch (SQLException e) {}
			if(con == null) try { con.close(); } catch (SQLException e) {}
		}
		return i;
	}
	public Integer updateComment(Comment comment) {
		Integer i = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = this.getConnection();
			String sql = "update comment1 set comment_content = ? where comment_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, comment.getCommentContent());
			pstmt.setLong(2, comment.getCommentNo());
			i = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally { // 메모리에서 정보를 삭제하기 위해 이걸 하지 않으면 다시 connection이 안된다.
			if(pstmt == null) try { pstmt.close(); } catch (SQLException e) {}
			if(con == null) try { con.close(); } catch (SQLException e) {}
		}
		return i;
	}
	public Integer deleteComment(Long commentNo) {
		Integer i = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = this.getConnection();
			String sql = "delete from comment1 where comment_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, commentNo);
			i = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt == null) try { pstmt.close(); } catch (SQLException e) {}
			if(con == null) try { con.close(); } catch (SQLException e) {}
		}
		return i;
	}
	public List<Comment> SelectCommentByCondition (Map<String, Object> condition) {
		List<Comment> comments = new ArrayList<Comment>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
			String sql = "select comment_no, user_id, comment_content, reg_date from comment1";
			if(condition.get("commentNo") != null) {
				sql += " where comment_no = ?";
			}
			pstmt = con.prepareStatement(sql);
			if(condition.get("commentNo") != null) {
				pstmt.setLong(1, Long.valueOf(condition.get("commentNo")+"")); // ""으로 오브젝트 타입을 스트링 타입으로 변환
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommentNo(rs.getLong("comment_no"));
				comment.setUserId(rs.getString("user_id"));
				comment.setCommentContent(rs.getString("comment_content"));
				comment.setRegDate(rs.getDate("reg_date"));
				comments.add(comment);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs == null) try { rs.close(); } catch (SQLException e) {}
			if(pstmt == null) try { pstmt.close(); } catch (SQLException e) {}
			if(con == null) try { con.close(); } catch (SQLException e) {}
		}
		return comments;
	}
}
