package io.codementor.vetting.ideapool.model;

import java.sql.Timestamp;

public class Idea {
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


	public int getImpact() {
		return impact;
	}


	public void setImpact(int impact) {
		this.impact = impact;
	}


	public int getEase() {
		return ease;
	}


	public void setEase(int ease) {
		this.ease = ease;
	}


	public int getConfidence() {
		return confidence;
	}


	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}


	public double getAverage() {
		return average;
	}


	public void setAverage(double average) {
		this.average = average;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	public Timestamp getLastUpdatedAt() {
		return lastUpdatedAt;
	}


	public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}


	private String id;
	private String content;
	private int impact;
	private int ease;
	private int confidence;
	private double average;
	private String createdBy;
	private Timestamp createdAt;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedAt;
	
	
	@Override
	public String toString() {
		return "id= "+id+
				", content= "+content+
				", impact= "+impact+
				", ease"+ease+
				", confidence= "+confidence+
				", average"+average+
				", createdBy"+createdBy+
				", createdAt"+createdAt+
				", lastUpdatedBy"+lastUpdatedBy+
				", lastUpdatedAt"+lastUpdatedAt;
	}
}
