package com.sunnet.org.app.oss.util;

public class ResultValue {
  private String value;

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}

public ResultValue(String value) {
	super();
	this.value = value;
}

public ResultValue() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "ResultValue [value=" + value + ", getvalue()=" + getValue()
			+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
			+ ", toString()=" + super.toString() + "]";
} 

}
