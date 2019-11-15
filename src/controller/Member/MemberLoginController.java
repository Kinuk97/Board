package controller.Member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Member;
import service.face.MemberService;
import service.impl.MemberServiceImpl;

@WebServlet("/member/login")
public class MemberLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService = MemberServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Member member = memberService.getLoginMember(req);
		
		boolean result = memberService.login(member);
		
		if (result) {
			Member loginUser = memberService.getMemberByUserid(member);
			
			HttpSession session = req.getSession();
			
			session.setAttribute("login", true);
			session.setAttribute("userid", loginUser.getUserid());
			session.setAttribute("usernick", loginUser.getUsernick());
			
			resp.sendRedirect("/main");
		} else {
			req.setAttribute("login_failed", result);
			
			req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
		}
	}
}
