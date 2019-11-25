package controller.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Comment;
import service.face.BoardService;
import service.impl.BoardServiceImpl;

/**
 * Servlet implementation class CommentDeleteController
 */
@WebServlet("/comment/delete")
public class CommentDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = BoardServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Comment comment = new Comment();
		
		String param = req.getParameter("commentno");
		if (param != null) {
			comment.setCommentno(Integer.parseInt(param));
		}
		
		param = req.getParameter("boardno");
		if (param != null) {
			comment.setBoardno(Integer.parseInt(param));
		}
		
		boardService.commentDelete(comment);
		
		resp.sendRedirect("/board/view?boardno=" + comment.getBoardno());
	}
}
