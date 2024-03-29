package service.face;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Board;
import dto.BoardFile;
import dto.Comment;
import util.Paging;

public interface BoardService {
	
	/**
	 * 게시글 목록 조회
	 * 
	 * @return - 게시글 목록
	 */
	public List<Board> getList();

	/**
	 * 페이징 정보를 활용하여 보여질 게시글 목록만 조회
	 * @param paging - 페이징 정보
	 * @return List - 게시글 목록
	 */
	public List<Board> getList(Paging paging);
	
	
	/**
	 * 요청파라미터 curPage를 파싱한다.
	 * Board TB와 curPage 값을 이용한 Paging 객체를 생성하고 반환한다.
	 * @param req - 요청 정보 객체
	 * @return Paging - 페이징 정보
	 */
	public Paging getPaging(HttpServletRequest req);

	public Board getBoardno(HttpServletRequest req);
	public Board view(Board board);
	
	
//	public void write(Board board);
//	public void write(HttpServletRequest req);
	public void write(HttpServletRequest req);
	
	public BoardFile getFile(Board board);
	public BoardFile getFile(BoardFile boardFile);

	public void update(HttpServletRequest req);
	
	public void delete(Board board, HttpServletRequest req);
	
	
	public void recommend(Board recommendBoard);
	public void unRecommend(Board recommendBoard);
	
	public boolean checkRecommend(Board recommendBoard);
	public int cntRecommend(Board recommendBoard);

	public List<Comment> commentList(Board board);
	public void commentInsert(Comment comment);
	public void commentDelete(Comment comment);
}
