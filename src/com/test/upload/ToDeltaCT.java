package com.test.upload;

public class ToDeltaCT {

	private String geneName;
	private String geneType;
	private double foldChange;
	private String sampleBarCode;

	public double getFoldChange() {
		return foldChange;
	}

	public void setFoldChange(double foldChange) {
		this.foldChange = foldChange;
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

	public String getSampleBarCode() {
		return sampleBarCode;
	}

	public void setSampleBarCode(String sampleBarCode) {
		this.sampleBarCode = sampleBarCode;
	}
}
