package service.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Board;

public interface BoardService {
	public List<Board> getList();

	public Board getBoardno(HttpServletRequest req);
	public Board view(Board board);
}
