package de.htw.fb4.bilderplattform.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/************************************************
 * <p>example database object</p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 24.10.2012
 * </p>
 ************************************************/
@Entity
@Table(name="Test")
public class Test implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idTest")
	private Integer idTest;
	
	@Column(name="testString", nullable=false)
	private String testString;

	
	public Test() {
	}

	public Test(String testString) {
		this.testString = testString;
	}

	public Integer getIdTest() {
		return this.idTest;
	}

	public void setIdTest(Integer idTest) {
		this.idTest = idTest;
	}

	public String getTestString() {
		return this.testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

}
