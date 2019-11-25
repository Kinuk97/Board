package dto;

import java.util.Date;

public class Board {
	private int boardno;
	private String title;
	private String id;
	private String content;
	private int recommend;
	private int hit;
	private Date writtendate;

	public int getBoardno() {
		return boardno;
	}

	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public Date getWrittendate() {
		return writtendate;
	}

	public void setWrittendate(Date writtendate) {
		this.writtendate = writtendate;
	}

	@Override
	public String toString() {
		return "Board [boardno=" + boardno + ", title=" + title + ", id=" + id + ", content=" + content + ", recommend="
				+ recommend + ", hit=" + hit + ", writtendate=" + writtendate + "]";
	}

}
