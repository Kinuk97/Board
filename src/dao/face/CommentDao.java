package dao.face;

import java.util.List;

import dto.Board;
import dto.Comment;

public interface CommentDao {

	public List<Comment> selectCommentByBoardNo(Board board);
	public void insertComment(Comment comment);
	public void deleteComment(Comment comment);
}
