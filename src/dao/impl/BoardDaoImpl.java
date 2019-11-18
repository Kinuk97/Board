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
import util.Paging;

public class BoardDaoImpl implements BoardDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private BoardDaoImpl() {
	}

	private static class Singleton {
		private static final BoardDao instance = new BoardDaoImpl();
	}

	public static BoardDao getInstance() {
		return Singleton.instance;
	}

	@Override
	public List<Board> selectAll() {
		conn = DBConn.getConnection();

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
		conn = DBConn.getConnection();

		String sql = "SELECT * FROM board WHERE boardno = ?";

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
		conn = DBConn.getConnection();

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
	public int selectCntAll() {
		conn = DBConn.getConnection();

		String sql = "SELECT count(*) cnt FROM board";

		int totalCount = 0;

		try {
			ps = conn.prepareStatement(sql);

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
		conn = DBConn.getConnection();

		String sql = "";
		sql += "select * from(";
		sql += " select rownum rnum, B.* FROM(";
		sql += " select boardno, title, id, content, hit, writtendate from board";
		sql += " order by boardno desc";
		sql += " )B";
		sql += " ORDER BY rnum";
		sql += " )BOARD";
		sql += " WHERE rnum BETWEEN ? AND ?";

		List<Board> list = new ArrayList<Board>();

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());

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
	public int selectBoardno() {
		conn = DBConn.getConnection();

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
		conn = DBConn.getConnection();

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
		conn = DBConn.getConnection();

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
		conn = DBConn.getConnection();

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
		conn = DBConn.getConnection();

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


}
