package com.test.upload;

public class AverageCT {

	private String geneName;
	private String geneType;
	private double averageCt;
	private double std;
	private String sampleBarCode;

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

	public double getAverageCt() {
		return averageCt;
	}

	public void setAverageCt(double averageCt) {
		this.averageCt = averageCt;
	}

	public double getStd() {
		return std;
	}

	public void setStd(double std) {
		this.std = std;
	}

	public String getSampleBarCode() {
		return sampleBarCode;
	}

	public void setSampleBarCode(String sampleBarCode) {
		this.sampleBarCode = sampleBarCode;
	}

}
