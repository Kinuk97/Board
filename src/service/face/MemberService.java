package service.face;

import javax.servlet.http.HttpServletRequest;

import dto.Member;

public interface MemberService {
	public Member getMember(HttpServletRequest req);
	
	public Member getLoginMember(HttpServletRequest req);
	
	public boolean login(Member member);
	
	public Member getMemberByUserid(Member member);
	
	public void join(Member member);
}
