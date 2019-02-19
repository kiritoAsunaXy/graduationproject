package com.xuyu.springboot.bean;




public class ArticleInfo {

	
	  private String id;
	  private String title;
	  private String content;
	  private String content_text;
	  private String cover;
	  private int view_count;
	  private String update_time;
	  private int status;
	  private String type_id;
	  private User user; //文章分类名
	private Integer owner;//文章归属者
	private String author;//作者名字
     private String name;
     private double stars;//评分

	public String getId() {
		return id;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_text() {
		return content_text;
	}
	public void setContent_text(String content_text) {
		this.content_text = content_text;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}

	
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public double getStars() {
		return stars;
	}

	public void setStars(double stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		return "ArticleInfo{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", content_text='" + content_text + '\'' +
				", cover='" + cover + '\'' +
				", view_count=" + view_count +
				", update_time='" + update_time + '\'' +
				", status=" + status +
				", type_id='" + type_id + '\'' +
				", user=" + user +
				", owner=" + owner +
				", author='" + author + '\'' +
				", name='" + name + '\'' +
				", stars=" + stars +
				'}';
	}
}
