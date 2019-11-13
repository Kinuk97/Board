package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Board;
import service.face.BoardService;
import service.impl.BoardServiceImpl;

@WebServlet("/board/view")
public class BoardViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService = BoardServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("board", boardService.view(boardService.getBoardno(req)));
		req.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(req, resp);
	}
}
