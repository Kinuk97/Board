package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.face.CommentDao;
import dbutil.DBConn;
import dto.Board;
import dto.Comment;

public class CommentDaoImpl implements CommentDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	

	private CommentDaoImpl() {
		conn = DBConn.getConnection();
	}

	private static class Singleton {
		private static final CommentDao instance = new CommentDaoImpl();
	}

	public static CommentDao getInstance() {
		return Singleton.instance;
	}
	
	@Override
	public List<Comment> selectCommentByBoardNo(Board board) {
		String sql = "SELECT * FROM commentTB WHERE boardno = ? ORDER BY commentno DESC";

		List<Comment> list = new ArrayList<Comment>();

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());

			rs = ps.executeQuery();

			while (rs.next()) {
				Comment comment = new Comment();

				comment.setCommentno(rs.getInt("commentno"));
				comment.setBoardno(rs.getInt("boardno"));
				comment.setContent(rs.getString("content"));
				comment.setUserid(rs.getString("userid"));
				comment.setWrittendate(rs.getDate("writtendate"));

				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public void insertComment(Comment comment) {
		String sql = "INSERT INTO commentTB VALUES (commentTB_seq.nextval, ?, ?, ?, sysdate)";

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, comment.getBoardno());
			ps.setString(2, comment.getUserid());
			ps.setString(3, comment.getContent());
			
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteComment(Comment comment) {
		String sql = "DELETE FROM commentTB WHERE commentno = ?";

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, comment.getCommentno());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
