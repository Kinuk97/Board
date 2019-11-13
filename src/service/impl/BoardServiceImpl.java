package service.impl;

import java.util.List;

import dao.face.BoardDao;
import dao.impl.BoardDaoImpl;
import dto.Board;
import service.face.BoardService;

public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao = new BoardDaoImpl();

	@Override
	public List<Board> getList() {
		return boardDao.selectAll();
	}

}
