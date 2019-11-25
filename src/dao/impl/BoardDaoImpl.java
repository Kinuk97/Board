package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.face.BoardDao;
import dbutil.DBConn;
import dto.Board;
import dto.BoardFile;
import dto.Comment;
import util.Paging;

public class BoardDaoImpl implements BoardDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	

	private BoardDaoImpl() {
		conn = DBConn.getConnection();
	}

	private static class Singleton {
		private static final BoardDao instance = new BoardDaoImpl();
	}

	public static BoardDao getInstance() {
		return Singleton.instance;
	}

	@Override
	public List<Board> selectAll() {
		String sql = "SELECT * FROM board ORDER BY boardno DESC";

		List<Board> list = new ArrayList<Board>();

		try {
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				Board board = new Board();

				board.setBoardno(rs.getInt("boardno"));
				board.setTitle(rs.getString("title"));
				board.setId(rs.getString("id"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setWrittendate(rs.getDate("writtendate"));

				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public Board selectBoardByBoardno(Board board) {
		String sql = "SELECT boardno, title, id, content, hit, writtendate, (SELECT COUNT(*) FROM recommend WHERE boardno = board.boardno) recommend FROM board WHERE boardno = ?";

		Board resultBoard = null;

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, board.getBoardno());

			rs = ps.executeQuery();

			while (rs.next()) {
				resultBoard = new Board();

				resultBoard.setBoardno(rs.getInt("boardno"));
				resultBoard.setTitle(rs.getString("title"));
				resultBoard.setId(rs.getString("id"));
				resultBoard.setContent(rs.getString("content"));
				resultBoard.setHit(rs.getInt("hit"));
				resultBoard.setWrittendate(rs.getDate("writtendate"));
				resultBoard.setRecommend(rs.getInt("recommend"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultBoard;
	}

	@Override
	public void updateHit(Board board) {
		String sql = "UPDATE board SET hit = hit + 1 WHERE boardno = ?";
		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, board.getBoardno());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int selectCntAll(String search) {
		String sql = "SELECT count(*) cnt FROM board";
		
		if (search != null) {
			sql += " WHERE title LIKE ? OR content LIKE ?";
		}

		int totalCount = 0;

		try {
			ps = conn.prepareStatement(sql);
			
			if (search != null) {
				ps.setString(1, "%" + search + "%");
				ps.setString(2, "%" + search + "%");
			}

			rs = ps.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return totalCount;
	}

	@Override
	public List<Board> selectAll(Paging paging) {
		String sql = "";
		sql += "select boardno, title, id, content, hit, writtendate, (SELECT COUNT(*) FROM recommend WHERE boardno = board.boardno) recommend from(";
		sql += " select rownum rnum, B.* FROM(";
		sql += " select boardno, title, id, content, hit, writtendate from board";
		
		if (paging.getSearch() != null) {
			sql += " WHERE title LIKE ? OR content LIKE ?";
		}
		
		sql += " order by boardno desc";
		sql += " ) B";
		sql += " ORDER BY rnum";
		sql += " ) BOARD";
		sql += " WHERE rnum BETWEEN ? AND ?";

		List<Board> list = new ArrayList<Board>();

		try {
			ps = conn.prepareStatement(sql);

			if (paging.getSearch() != null) {
				ps.setString(1, "%" + paging.getSearch() + "%");
				ps.setString(2, "%" + paging.getSearch() + "%");
				ps.setInt(3, paging.getStartNo());
				ps.setInt(4, paging.getEndNo());
			} else {
				ps.setInt(1, paging.getStartNo());
				ps.setInt(2, paging.getEndNo());
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				Board board = new Board();

				board.setBoardno(rs.getInt("boardno"));
				board.setTitle(rs.getString("title"));
				board.setId(rs.getString("id"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setWrittendate(rs.getDate("writtendate"));
				board.setRecommend(rs.getInt("recommend"));

				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	@Override
	public int selectBoardno() {
		String sql = "SELECT board_seq.nextval boardno FROM dual";

		int boardno = 0;

		try {
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			if (rs.next()) {
				boardno = rs.getInt("boardno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return boardno;
	}

	@Override
	public void insert(Board board) {
		String sql = "INSERT INTO board VALUES (?, ?, ?, ?, 0, sysdate)";

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());
			ps.setString(2, board.getTitle());
			ps.setString(3, board.getId());
			ps.setString(4, board.getContent());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void insertFile(BoardFile boardFile) {
		String sql = "INSERT INTO boardfile VALUES (boardfile_seq.nextval, ?, ?, ?, ?, sysdate)";

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, boardFile.getBoardno());
			ps.setString(2, boardFile.getOriginname());
			ps.setString(3, boardFile.getStoredname());
			ps.setInt(4, boardFile.getFilesize());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public BoardFile selectFile(Board board) {
		String sql = "SELECT * FROM boardfile WHERE boardno = ?";
		
		BoardFile file = null;

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());

			rs = ps.executeQuery();

			if (rs.next()) {
				file = new BoardFile();
				
				file.setFileno(rs.getInt("fileno"));
				file.setBoardno(rs.getInt("boardno"));
				file.setFilesize(rs.getInt("filesize"));
				file.setOriginname(rs.getString("originname"));
				file.setStoredname(rs.getString("storedname"));
				file.setWritedate(rs.getDate("writedate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return file;
	}

	@Override
	public BoardFile selectFile(BoardFile boardFile) {
		String sql = "SELECT * FROM boardfile WHERE fileno = ?";
		
		BoardFile file = null;

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, boardFile.getFileno());

			rs = ps.executeQuery();

			if (rs.next()) {
				file = new BoardFile();
				
				file.setFileno(rs.getInt("fileno"));
				file.setBoardno(rs.getInt("boardno"));
				file.setFilesize(rs.getInt("filesize"));
				file.setOriginname(rs.getString("originname"));
				file.setStoredname(rs.getString("storedname"));
				file.setWritedate(rs.getDate("writedate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return file;
	}

	@Override
	public void update(Board board) {
		String sql = "UPDATE board SET title = ?, content = ? WHERE boardno = ?";

		try {
			ps = conn.prepareStatement(sql);

			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getBoardno());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(BoardFile boardFile) {
		String sql = "UPDATE boardfile SET originname = ?, storedname = ?, filesize = ? WHERE fileno = ?";

		try {
			ps = conn.prepareStatement(sql);

			ps.setString(1, boardFile.getOriginname());
			ps.setString(2, boardFile.getStoredname());
			ps.setInt(3, boardFile.getFilesize());
			ps.setInt(4, boardFile.getFileno());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void delete(Board board) {
		String sql = "DELETE FROM board WHERE boardno = ?";
		
		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, board.getBoardno());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void delete(BoardFile boardFile) {
		String sql = "DELETE FROM boardFile WHERE boardno = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, boardFile.getBoardno());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int cntRecommend(Board board) {
		String sql = "SELECT count(*) cnt FROM recommend WHERE boardno = ?";

		int totalCount = 0;

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());

			rs = ps.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return totalCount;
	}
	
	@Override
	public int cntMyRecommend(Board board) {
		String sql = "SELECT count(*) cnt FROM recommend WHERE boardno = ? AND userid = ?";

		int totalCount = 0;

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());
			ps.setString(2, board.getId());

			rs = ps.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return totalCount;
	}

	@Override
	public void insertRecommend(Board board) {
		String sql = "INSERT INTO recommend VALUES (?, ?)";

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, board.getId());
			ps.setInt(2, board.getBoardno());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteRecommend(Board board) {
		String sql = "DELETE FROM recommend WHERE boardno = ? AND userid = ?";

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());
			ps.setString(2, board.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
