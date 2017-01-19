package com.test.upload;

public class CTData {

	private String fileName;
	private String well;
	private String geneName;
	private String geneType;
	private String sample;
	private String ct;
	private double adjustedCt;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getWell() {
		return well;
	}

	public void setWell(String well) {
		this.well = well;
	}

	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

	public String getGeneType() {
		return geneType;
	}

	public void setGeneType(String geneType) {
		this.geneType = geneType;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public double getAdjustedCt() {
		return adjustedCt;
	}

	public void setAdjustedCt(double adjustedCt) {
		this.adjustedCt = adjustedCt;
	}

}
