package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Board;
import service.face.BoardService;
import service.impl.BoardServiceImpl;

@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = BoardServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object param = req.getSession().getAttribute("login");
		
		if (param != null && (boolean) param) {
			req.setAttribute("userid", req.getSession().getAttribute("userid"));
			
			req.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("/board/list");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		boardService.write(req);

		resp.sendRedirect("/board/list");
	}
}
