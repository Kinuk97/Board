package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.face.MemberDao;
import dbutil.DBConn;
import dto.Board;
import dto.Member;

public class MemberDaoImpl implements MemberDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	private MemberDaoImpl() { }
	
	private static class Singleton {
		private static final MemberDao instance = new MemberDaoImpl();
	}
	
	public static MemberDao getInstance() {
		return Singleton.instance;
	}

	@Override
	public int selectCntMemberByUserid(Member member) {
		conn = DBConn.getConnection();

		String sql = "SELECT count(*) FROM member WHERE userid = ? AND userpw = ?";

		int result = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, member.getUserid());
			ps.setString(2, member.getUserpw());

			rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
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

		return result;
	}

	@Override
	public Member selectMemberByUserid(Member member) {
		conn = DBConn.getConnection();

		String sql = "SELECT userid, usernick FROM member WHERE userid = ?";
		
		Member resultMember = null;

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, member.getUserid());

			rs = ps.executeQuery();

			if (rs.next()) {
				resultMember = new Member();
				
				resultMember.setUserid(rs.getString("userid"));
				resultMember.setUsernick(rs.getString("usernick"));
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

		return resultMember;
	}

	@Override
	public void insert(Member member) {
		conn = DBConn.getConnection();

		String sql = "INSERT INTO member VALUES (?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, member.getUserid());
			ps.setString(2, member.getUserpw());
			ps.setString(3, member.getUsernick());

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
