package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.face.BoardService;
import service.impl.BoardServiceImpl;
import util.Paging;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService = BoardServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청 파라미터에서 curPage를 구하고 Paging 객체 반환
		Paging paging = boardService.getPaging(req);
		
		// Paging 객체를 MODEL 값으로 지정
		req.setAttribute("paging", paging);
		
		req.setAttribute("boardList", boardService.getList(paging));
		
		req.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
