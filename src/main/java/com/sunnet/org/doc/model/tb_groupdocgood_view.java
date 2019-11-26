package com.sunnet.org.doc.model;

import javax.persistence.Id;

import com.sunnet.org.competition.model.Tb_contest;

public class tb_groupdocgood_view {
	@Id
	private int contest_id; // 赛事
	private String ContestName;
	private int ContestStatus;
	private int ContestStartTime;
	private int doccount;
	public int getContest_id() {
		return contest_id;
	}
	public void setContest_id(int contest_id) {
		this.contest_id = contest_id;
	}
	public String getContestName() {
		return ContestName;
	}
	public void setContestName(String contestName) {
		ContestName = contestName;
	}
	public int getContestStatus() {
		return ContestStatus;
	}
	public void setContestStatus(int contestStatus) {
		ContestStatus = contestStatus;
	}
	public int getContestStartTime() {
		return ContestStartTime;
	}
	public void setContestStartTime(int contestStartTime) {
		ContestStartTime = contestStartTime;
	}
	public int getDoccount() {
		return doccount;
	}
	public void setDoccount(int doccount) {
		this.doccount = doccount;
	}
	@Override
	public String toString() {
		return "tb_groupdocgood_view [contest_id=" + contest_id
				+ ", ContestName=" + ContestName + ", ContestStatus="
				+ ContestStatus + ", ContestStartTime=" + ContestStartTime
				+ ", doccount=" + doccount + "]";
	}
	
}
