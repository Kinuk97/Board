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
 * Servlet implementation class CommentInsertController
 */
@WebServlet("/comment/insert")
public class CommentInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardSerivce = BoardServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Comment comment = new Comment();
		
		String param = req.getParameter("boardno");
		if (param != null && !"".equals(param)) {
			comment.setBoardno(Integer.parseInt(param));
		}
		
		param = req.getParameter("content");
		if (param != null && !"".equals(param) )
			comment.setContent(param);
		
		Object obj = req.getSession().getAttribute("userid");
		if (obj != null) {
			comment.setUserid((String) obj);
		}
		
		boardSerivce.commentInsert(comment);
		
		resp.sendRedirect("/board/view?boardno=" + comment.getBoardno());
	}

}
