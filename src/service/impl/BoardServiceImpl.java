package service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.face.BoardDao;
import dao.impl.BoardDaoImpl;
import dto.Board;
import service.face.BoardService;
import util.Paging;

public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao = BoardDaoImpl.getInstance();

	private BoardServiceImpl() {
	}

	private static class Singleton {
		private static BoardService instance = new BoardServiceImpl();
	}

	// 객체 반환
	public static BoardService getInstance() {
		return Singleton.instance;
	}

	@Override
	public List<Board> getList() {
		return boardDao.selectAll();
	}

	@Override
	public Board getBoardno(HttpServletRequest req) {
		Board board = new Board();
		board.setBoardno(Integer.parseInt(req.getParameter("boardno")));
		return board;
	}

	@Override
	public Board view(Board board) {
		boardDao.updateHit(board);
		return boardDao.selectBoardByBoardno(board);
	}

	@Override
	public Paging getPaging(HttpServletRequest req) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if (param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		// Board TB와 curPage 값을 이용한 Paging 객체를 생성하고 반환
		int totalCount = boardDao.selectCntAll();
		
		// Paging 객체 생성
		Paging paging = new Paging(totalCount, curPage);
		
		return paging;
	}

	@Override
	public List<Board> getList(Paging paging) {
		return boardDao.selectAll(paging);
	}


}
