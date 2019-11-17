package com.renee.jin.model;

import java.math.BigDecimal;
import java.util.HashMap;

public class BoardBean {
	private int seq;
	private String id;
	private int useFlag;
	private int readCount;
	private int script;
	private String sTitle;
	private String sContent;
	private String sFilepath;
	private String sFilename;
	private String sJoindate;
	private String sJoinUpdate;
	private int sGroup = 0;
	private int sStep = 0;
	private int sIndent = 0;
	private int childrenCount = 0;
	private int pagestart;
	private int pageline;
	private int page = 1;
	private BigDecimal num;
	private String searchField;
	private String searchString;
	private String pw;
	
    private HashMap<String, String> where;
	private HashMap<String, String> whereNot;
    private HashMap<String, String> like;
    
    private int limitStart = 0;
    private int limitEnd;
    
    
    public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getChildrenCount() {
        return childrenCount;
    }
    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
    public int getScript() {
        return script;
    }
    public void setScript(int script) {
        this.script = script;
    }
    public HashMap<String, String> getWhere() {
        return where;
    }
    public void setWhere(HashMap<String, String> where) {
        this.where = where;
    }
    public HashMap<String, String> getWhereNot() {
        return whereNot;
    }
    public void setWhereNot(HashMap<String, String> whereNot) {
        this.whereNot = whereNot;
    }
    public HashMap<String, String> getLike() {
        return like;
    }
    public void setLike(HashMap<String, String> like) {
        this.like = like;
    }
    public int getLimitStart() {
        return limitStart;
    }
    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }
    public int getLimitEnd() {
        return limitEnd;
    }
    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getUseFlag() {
        return useFlag;
    }
    public void setUseFlag(int useFlag) {
        this.useFlag = useFlag;
    }
    public int getReadCount() {
        return readCount;
    }
    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }
    public String getsTitle() {
        return sTitle;
    }
    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }
    public String getsContent() {
        return sContent;
    }
    public void setsContent(String sContent) {
        this.sContent = sContent;
    }
    public String getsFilepath() {
        return sFilepath;
    }
    public void setsFilepath(String sFilepath) {
        this.sFilepath = sFilepath;
    }
    public String getsFilename() {
        return sFilename;
    }
    public void setsFilename(String sFilename) {
        this.sFilename = sFilename;
    }
    public String getsJoindate() {
        return sJoindate;
    }
    public void setsJoindate(String sJoindate) {
        this.sJoindate = sJoindate;
    }
    public String getsJoinUpdate() {
        return sJoinUpdate;
    }
    public void setsJoinUpdate(String sJoinUpdate) {
        this.sJoinUpdate = sJoinUpdate;
    }
    public int getsGroup() {
        return sGroup;
    }
    public void setsGroup(int sGroup) {
        this.sGroup = sGroup;
    }
    public int getsStep() {
        return sStep;
    }
    public void setsStep(int sStep) {
        this.sStep = sStep;
    }
    public int getsIndent() {
        return sIndent;
    }
    public void setsIndent(int sIndent) {
        this.sIndent = sIndent;
    }
    public int getPagestart() {
        return pagestart;
    }
    public void setPagestart(int pagestart) {
        this.pagestart = pagestart;
    }
    public int getPageline() {
        return pageline;
    }
    public void setPageline(int pageline) {
        this.pageline = pageline;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public BigDecimal getNum() {
        return num;
    }
    public void setNum(BigDecimal num) {
        this.num = num;
    }
    public String getSearchField() {
        return searchField;
    }
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
    public String getSearchString() {
        return searchString;
    }
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

}
