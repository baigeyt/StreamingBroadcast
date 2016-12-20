package com.hyc.bean;

public class StuInfo {
	int id;
   String name,className,classCoded,cardNum = null;
   
   public void setCardNum(String cardNum) {
	this.cardNum = cardNum;
}
   public String getCardNum() {
	return cardNum;
}
   public void setClassCoded(String classCoded) {
	this.classCoded = classCoded;
}
   public String getClassCoded() {
	return classCoded;
}
   public void setClassName(String className) {
	this.className = className;
}
   public String getClassName() {
	return className;
}
   public void setName(String name) {
	this.name = name;
}
   public String getName() {
	return name;
}
   public void setId(int id) {
	this.id = id;
}
   public int getId() {
	return id;
}
   
}
