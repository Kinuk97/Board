package service.impl;

import javax.servlet.http.HttpServletRequest;

import dao.face.MemberDao;
import dao.impl.MemberDaoImpl;
import dto.Member;
import service.face.MemberService;

public class MemberServiceImpl implements MemberService {
	private MemberDao memberDao = MemberDaoImpl.getInstance();

	private MemberServiceImpl() {
	}

	private static class Singleton {
		private static MemberService instance = new MemberServiceImpl();
	}

	// 객체 반환
	public static MemberService getInstance() {
		return Singleton.instance;
	}

	@Override
	public Member getMember(HttpServletRequest req) {
		Member member = null;

		String param1 = req.getParameter("userid");
		String param2 = req.getParameter("userpw");
		String param3 = req.getParameter("usernick");

		if (param1 != null && !"".equals(param1)) {
			if (param2 != null && !"".equals(param2)) {
				if (param3 != null && !"".equals(param2)) {
					member = new Member();

					member.setUserid(param1);
					member.setUserpw(param2);
					member.setUsernick(param3);
				}
			}
		}

		return member;
	}

	@Override
	public Member getLoginMember(HttpServletRequest req) {
		Member member = null;

		String param1 = req.getParameter("userid");
		String param2 = req.getParameter("userpw");

		if (param1 != null && !"".equals(param1)) {
			if (param2 != null && !"".equals(param2)) {
				member = new Member();

				member.setUserid(param1);
				member.setUserpw(param2);
			}
		}

		return member;
	}

	@Override
	public boolean login(Member member) {
		boolean result = false;

		if (memberDao.selectCntMemberByUserid(member) == 1) {
			result = true;
		}

		return result;
	}

	@Override
	public Member getMemberByUserid(Member member) {
		return memberDao.selectMemberByUserid(member);
	}

	@Override
	public void join(Member member) {
		memberDao.insert(member);
	}

}
