package controller.board;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import dto.BoardFile;
import service.face.BoardService;
import service.impl.BoardServiceImpl;

@WebServlet("/board/file")
public class BoardFileDownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = BoardServiceImpl.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 전달 파라미터 받기
		BoardFile downFile = new BoardFile();
		
		downFile.setFileno(Integer.parseInt(req.getParameter("fileno")));

		// 다운로드 대상 파일 정보 조회하기
		downFile = boardService.getFile(downFile);
		
		// 다운로드용 File 객체 만들기
		String path = getServletContext().getRealPath("upload"); // 경로
		String filename = downFile.getStoredname(); // 파일이름

		File file = new File(path, filename);

		// --- 파일 다운로드 시키기 ---
		// 응답 정보 객체를 설정한다.
		// Response Message의 Header를 수정한다.

		// 응답 body의 길이 설정

		// 파일의 길이를 알려줘야 계속 다운받지 않고 크기에 맞게 다운받는다.
		resp.setHeader("Content-Length", String.valueOf(file.length()));

		// 응답 데이터의 형식(MIME-Type)
		resp.setContentType("application/octet-stream"); // 이진 파일의 형태

		// 응답 파일의 저장위치 지정하기
		// (이름도 변경 가능)
		resp.setHeader("Content-Disposition",
				"attachment;fileName=" + new String(downFile.getOriginname().getBytes("UTF-8"), "8859_1") + ";");

		// --- 응답 메세지의 응답 Body(본문) 작성 ---
		// 파일의 내용을 응답으로 출력

		// 파일 입력 스트림 (서버의 로컬 저장소 파일)
		InputStream is = new BufferedInputStream(new FileInputStream(file));

		// 파일 출력 스트림 (브라우저)
		OutputStream os = resp.getOutputStream();

		// 파일 입력 -> 브라우저 출력
		IOUtils.copy(is, os);

		os.flush();

		is.close();
		os.close();

		// ------------------------------------------

	}
}
