package controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.Board;
import service.face.BoardService;
import service.impl.BoardServiceImpl;

@WebServlet("/board/recommend")
public class BoardRecommendController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService = BoardServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Board recommendBoard = boardService.getBoardno(req);
		
		recommendBoard.setId((String) req.getSession().getAttribute("userid"));
		
		boardService.recommend(recommendBoard);
		
		int cnt = boardService.cntRecommend(recommendBoard);
		boolean check = boardService.checkRecommend(recommendBoard);
		
		PrintWriter out = resp.getWriter();
		
		Gson gson = new Gson();
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("cnt", cnt);
		result.put("check", check);
		
		out.println(gson.toJson(result));
	}

}
