package service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.face.BoardDao;
import dao.face.CommentDao;
import dao.impl.BoardDaoImpl;
import dao.impl.CommentDaoImpl;
import dto.Board;
import dto.BoardFile;
import dto.Comment;
import service.face.BoardService;
import util.Paging;

public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao = BoardDaoImpl.getInstance();
	private CommentDao commentDao = CommentDaoImpl.getInstance();

	private BoardServiceImpl() {
	}

	private static class Singleton {
		private static BoardService instance = new BoardServiceImpl();
	}

	// 객체 반환
	public static BoardService getInstance() {
		return Singleton.instance;
	}

	@Override
	public List<Board> getList() {
		return boardDao.selectAll();
	}

	@Override
	public Board getBoardno(HttpServletRequest req) {
		Board board = new Board();
		board.setBoardno(Integer.parseInt(req.getParameter("boardno")));
		return board;
	}

	@Override
	public Board view(Board board) {
		boardDao.updateHit(board);
		return boardDao.selectBoardByBoardno(board);
	}

	@Override
	public Paging getPaging(HttpServletRequest req) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if (param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		param = req.getParameter("search");
		String search = null;
		if (param != null && !"".equals(param)) {
			search = param;
		}

		// Board TB와 curPage 값을 이용한 Paging 객체를 생성하고 반환
		int totalCount = boardDao.selectCntAll(search);
		
		// Paging 객체 생성
		Paging paging = new Paging(totalCount, curPage);
		
		paging.setSearch(search);

		return paging;
	}

	@Override
	public List<Board> getList(Paging paging) {
		return boardDao.selectAll(paging);
	}

//	@Override
//	public void write(Board board) {
//		board.setBoardno(boardDao.selectBoardno());
//		boardDao.insert(board);
//	}

	@Override
	public void write(HttpServletRequest req) {
		// 1. 파일업로드 형태의 데이터가 맞는지 확인
		// enctype이 multipart/form-data가 맞는지 확인
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);

		// 1-1 multipart/form-data 인코딩으로 전송되지 않았을 경우
		if (!isMultipart) {
			return;
		}

		// 1-2 여기 이후는 multipart/form-data로 전송된 상황
		// 파일이 전송된 상황

		// 2. 업로드된 파일을 처리하는 아이템팩토리 객체 생성
		// ItemFactory : 업로드된 파일을 처리하는 방식을 정하는 클래스

		// FileItem : 클라이언트로부터 전송된 데이터를 객체화시킨 것

		// DiskFileItemFactory class - > 디스크기반(HDD)의 파일 아이템 처리 API
		// 업로드된 파일을 디스크에 임시 저장하고 후처리한다.
		DiskFileItemFactory factory = null;
		// 생성자로 필요한 객체를 넘겨줄 수 있지만 따로 설정
		factory = new DiskFileItemFactory();

		// 3. 업로드된 아이템이 용량이 작으면 메모리에서 처리
		int maxMem = 1 * 1024 * 1024; // 1MB
		factory.setSizeThreshold(maxMem);

		// 4. 용량이 적당히 크면 임시파일을 만들어서 처리 (디스크)
		ServletContext context = req.getServletContext();
		// 가상경로인 매개변수를 실제 경로로 만들어준다.
		String path = context.getRealPath("tmp");
		File repository = new File(path);
		factory.setRepository(repository);

		// 5. 업로드 허용 용량 기준을 넘지 않을 경우에만 업로드 처리
		int maxFile = 10 * 1024 * 1024; // 10MB
		// 파일 업로드 객체 생성 - DiskFileItemFactory 이용
		ServletFileUpload upload = null;
		upload = new ServletFileUpload(factory);
		// 파일 업로드 용량 제한 설정 : 10MB
		upload.setFileSizeMax(maxFile);

		// --- 파일 업로드 준비 완료 ---

		// 6. 업로드된 데이터 추출(파싱)
		// 임시 파일 업로드도 같이 수행함
		List<FileItem> items = null;

		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		// 7. 파싱된 데이터 처리하기
		// items 리스트에 요청 파라미터가 파싱되어있음

		// 요청정보의 형태 3가지
		// 1. 빈 파일 (용량이 0인 파일)
		// 2. form-data (일반적인 요청 파라미터)
		// 3. 파일

		Iterator<FileItem> iter = items.iterator();

		Board board = new Board();
		BoardFile boardFile = new BoardFile();

		// 모든 요청 정보 처리
		while (iter.hasNext()) {
			FileItem item = iter.next();

			// 1) 빈 파일 처리
			if (item.getSize() <= 0)
				continue;

			if (item.isFormField()) {

				String key = item.getFieldName();
				if ("title".equals(key)) {
					try {
						board.setTitle(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

				} else if ("content".equals(key)) {
					try {
						board.setContent(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

				} else if ("userid".equals(key)) {
					try {
						board.setId(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

			} else {
				UUID uuid = UUID.randomUUID(); // 랜덤 UID 생성

				String u = uuid.toString().split("-")[4];

				File up = new File(context.getRealPath("upload"), item.getName() + "_" + u);

				boardFile = new BoardFile();

				boardFile.setOriginname(item.getName());
				boardFile.setStoredname(item.getName() + "_" + u);
				boardFile.setFilesize((int) item.getSize());

				try {
					item.write(up);
					item.delete(); // 임시 파일 삭제
				} catch (Exception e) {
					e.printStackTrace();
				} // 실제 업로드

			} // 파일 처리 if 끝

		} // 요청 파라미터 처리 while

		board.setBoardno(boardDao.selectBoardno());

		if (board.getTitle() == null)
			board.setTitle("제목없음");

		boardDao.insert(board);

		if (boardFile.getFilesize() != 0) {
			boardFile.setBoardno(board.getBoardno());

			boardDao.insertFile(boardFile);
		}

	}

	/*
	 * @Override public void write(HttpServletRequest req) { // -- 매개변수 준비 -- // 1.
	 * 요청 객체 - req // 2. 파일 저장 위치 - 서버 real path를 이용 ServletContext context =
	 * req.getServletContext(); String saveDirectory =
	 * context.getRealPath("upload"); // 3. 업로드 제한 사이즈 int maxPostSize = 10 * 1024 *
	 * 1024; // 10MB // 4. 인코딩 String encoding = "UTF-8"; // 5. 중복 파일 이름 정책
	 * FileRenamePolicy policy = new DefaultFileRenamePolicy(); //
	 * -------------------
	 * 
	 * // --- COS 파일 업로드 객체 생성 --- MultipartRequest mul = null; try { mul = new
	 * MultipartRequest(req, saveDirectory, maxPostSize, encoding, policy); } catch
	 * (IOException e) { e.printStackTrace(); } // ---------------------------------
	 * if (mul != null) { BoardFile file = new BoardFile();
	 * 
	 * Board board = new Board();
	 * 
	 * board.setTitle(mul.getParameter("title"));
	 * board.setContent(mul.getParameter("content"));
	 * board.setId(mul.getParameter("userid"));
	 * board.setBoardno(boardDao.selectBoardno());
	 * 
	 * boardDao.insert(board);
	 * 
	 * file.setBoardno(board.getBoardno());
	 * 
	 * if (mul.getFile("upfile") != null) {
	 * file.setOriginname(mul.getOriginalFileName("upfile"));
	 * file.setStoredname(mul.getFilesystemName("upfile")); file.setFilesize((int)
	 * mul.getFile("upfile").length()); }
	 * 
	 * boardDao.insertFile(file); }
	 * 
	 * }
	 */

	@Override
	public BoardFile getFile(Board board) {
		return boardDao.selectFile(board);
	}

	@Override
	public BoardFile getFile(BoardFile boardFile) {
		return boardDao.selectFile(boardFile);
	}

	@Override
	public void update(HttpServletRequest req) {
		// 1. 파일업로드 형태의 데이터가 맞는지 확인
		// enctype이 multipart/form-data가 맞는지 확인
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);

		// 1-1 multipart/form-data 인코딩으로 전송되지 않았을 경우
		if (!isMultipart) {
			return;
		}

		// 1-2 여기 이후는 multipart/form-data로 전송된 상황
		// 파일이 전송된 상황

		// 2. 업로드된 파일을 처리하는 아이템팩토리 객체 생성
		// ItemFactory : 업로드된 파일을 처리하는 방식을 정하는 클래스

		// FileItem : 클라이언트로부터 전송된 데이터를 객체화시킨 것

		// DiskFileItemFactory class - > 디스크기반(HDD)의 파일 아이템 처리 API
		// 업로드된 파일을 디스크에 임시 저장하고 후처리한다.
		DiskFileItemFactory factory = null;
		// 생성자로 필요한 객체를 넘겨줄 수 있지만 따로 설정
		factory = new DiskFileItemFactory();

		// 3. 업로드된 아이템이 용량이 작으면 메모리에서 처리
		int maxMem = 1 * 1024 * 1024; // 1MB
		factory.setSizeThreshold(maxMem);

		// 4. 용량이 적당히 크면 임시파일을 만들어서 처리 (디스크)
		ServletContext context = req.getServletContext();
		// 가상경로인 매개변수를 실제 경로로 만들어준다.
		String path = context.getRealPath("tmp");
		File repository = new File(path);
		factory.setRepository(repository);

		// 5. 업로드 허용 용량 기준을 넘지 않을 경우에만 업로드 처리
		int maxFile = 10 * 1024 * 1024; // 10MB
		// 파일 업로드 객체 생성 - DiskFileItemFactory 이용
		ServletFileUpload upload = null;
		upload = new ServletFileUpload(factory);
		// 파일 업로드 용량 제한 설정 : 10MB
		upload.setFileSizeMax(maxFile);

		// --- 파일 업로드 준비 완료 ---

		// 6. 업로드된 데이터 추출(파싱)
		// 임시 파일 업로드도 같이 수행함
		List<FileItem> items = null;

		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		// 7. 파싱된 데이터 처리하기
		// items 리스트에 요청 파라미터가 파싱되어있음

		// 요청정보의 형태 3가지
		// 1. 빈 파일 (용량이 0인 파일)
		// 2. form-data (일반적인 요청 파라미터)
		// 3. 파일

		Iterator<FileItem> iter = items.iterator();

		Board board = new Board();
		BoardFile boardFile = new BoardFile();

		// 모든 요청 정보 처리
		while (iter.hasNext()) {
			FileItem item = iter.next();

			// 1) 빈 파일 처리
			if (item.getSize() <= 0)
				continue;

			if (item.isFormField()) {

				String key = item.getFieldName();
				if ("title".equals(key)) {
					try {
						board.setTitle(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

				} else if ("content".equals(key)) {
					try {
						board.setContent(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

				} else if ("userid".equals(key)) {
					try {
						board.setId(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else if ("boardno".equals(key)) {
					board.setBoardno(Integer.parseInt(item.getString()));
				}

			} else {
				UUID uuid = UUID.randomUUID(); // 랜덤 UID 생성

				String u = uuid.toString().split("-")[4];

				File up = new File(context.getRealPath("upload"), item.getName() + "_" + u);

				boardFile = new BoardFile();

				boardFile.setOriginname(item.getName());
				boardFile.setStoredname(item.getName() + "_" + u);
				boardFile.setFilesize((int) item.getSize());

				try {
					item.write(up);
					item.delete(); // 임시 파일 삭제
				} catch (Exception e) {
					e.printStackTrace();
				} // 실제 업로드

			} // 파일 처리 if 끝

		} // 요청 파라미터 처리 while

//		if (boardFile.getFilesize() != 0) {
//			boardDao.update(board);
//			
//			boardFile.setBoardno(board.getBoardno());
//			
//			BoardFile prevFile = boardDao.selectFile(board);
//			
//			if (prevFile == null) {
//				boardDao.insertFile(boardFile);
//			} else {
//				// 로컬 파일까지 삭제
//				path = req.getServletContext().getRealPath("upload"); // 경로
//				String filename = prevFile.getStoredname(); // 파일이름
//
//				File file = new File(path, filename);
//				file.delete();
//				
//				boardFile.setFileno(prevFile.getFileno());
//				boardDao.update(boardFile);
//			}
//		} else {
//			boardDao.update(board);
//		}

		if (boardFile.getFilesize() != 0) {
			BoardFile prevfile = boardDao.selectFile(board);
			if (prevfile != null) {
				File prev = new File(req.getSession().getServletContext().getRealPath("upload"),
						prevfile.getStoredname());
				prev.delete();

				boardDao.delete(prevfile);
			}

			boardFile.setBoardno(board.getBoardno());
			boardDao.insertFile(boardFile);

		}

		boardDao.update(board);

	}

	@Override
	public void delete(Board board, HttpServletRequest req) {
		
		BoardFile boardFile = boardDao.selectFile(board);

		if (boardFile != null) {
			boardDao.delete(boardFile);
			
			String path = req.getServletContext().getRealPath("upload"); // 경로
			String filename = boardFile.getStoredname(); // 파일이름
			
			File file = new File(path, filename);
			
			file.delete();
		}
		
		boardDao.delete(board);
	}

	@Override
	public void recommend(Board recommendBoard) {
		if (!checkRecommend(recommendBoard)) {
			boardDao.insertRecommend(recommendBoard);
		}
	}
	
	@Override
	public void unRecommend(Board recommendBoard) {
		boardDao.deleteRecommend(recommendBoard);
	}
	
	@Override
	public int cntRecommend(Board recommendBoard) {
		return boardDao.cntRecommend(recommendBoard);
	}

	@Override
	public boolean checkRecommend(Board recommendBoard) {
		int myRecomm = boardDao.cntMyRecommend(recommendBoard);

		if (myRecomm > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Comment> commentList(Board board) {
		return commentDao.selectCommentByBoardNo(board);
	}

	@Override
	public void commentInsert(Comment comment) {
		commentDao.insertComment(comment);
	}
	
	@Override
	public void commentDelete(Comment comment) {
		commentDao.deleteComment(comment);
	}
	
}
