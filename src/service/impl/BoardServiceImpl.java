package service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.face.BoardDao;
import dao.impl.BoardDaoImpl;
import dto.Board;
import service.face.BoardService;

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
		System.out.println("조회수 올린 후");
		return boardDao.selectBoardByBoardno(board);
	}


}
