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

@WebServlet("/board/listDelete")
public class BoardListDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService = BoardServiceImpl.getInstance();
       
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] boards = req.getParameterValues("deleteBoard");
		
		Board board = new Board();
		
		if (boards != null && boards.length != 0) {
			for (String deleteBoard : boards) {
				board.setBoardno(Integer.parseInt(deleteBoard));
				
				boardService.delete(board, req);
			}
		}
		
		
		resp.sendRedirect("/board/list");
	}
}
