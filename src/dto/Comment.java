package dto;

import java.util.Date;

public class Comment {
	private int commentno;
	private int boardno;
	private String userid;
	private String content;
	private Date writtendate;

	@Override
	public String toString() {
		return "Comment [commentno=" + commentno + ", boardno=" + boardno + ", userid=" + userid + ", content="
				+ content + ", writtendate=" + writtendate + "]";
	}

	public int getCommentno() {
		return commentno;
	}

	public void setCommentno(int commentno) {
		this.commentno = commentno;
	}

	public int getBoardno() {
		return boardno;
	}

	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWrittendate() {
		return writtendate;
	}

	public void setWrittendate(Date writtendate) {
		this.writtendate = writtendate;
	}

}
