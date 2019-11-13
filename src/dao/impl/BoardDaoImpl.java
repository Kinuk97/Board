package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import dao.face.BoardDao;
import dbutil.DBConn;
import dto.Board;

public class BoardDaoImpl implements BoardDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public List<Board> selectAll() {
		conn = DBConn.getConnection();
		
		
		
		return null;
	}

}
