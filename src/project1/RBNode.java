package project1;



public class RBNode {
	
//	定义红色和黑色
	public static final int BLACK = 0;
	public static final int RED = 1;
	
//	定义nil
//	RBNode NIL;
	
//	左右儿子属性
	private RBNode left;
	private RBNode right;
//	父亲属性
	private RBNode parent;
	
//	颜色属性
	private int color;
//	定义key
	private int key;
//	定义keyStr
	private String keyStr;
//	定义查询内容
	private String value;
//	构造函数1
//	public RBNode (int color){
//		this.color = color;
//	}
//	构造函数2
//	public RBNode(){
//		this.color = RED;
//		this.key = -1;
//		this.value = "";
//		this.parent = NIL;
//	}
	
//	构造函数3
	public RBNode(int key,String keyStr,String value){
		this(null,null,null,BLACK,key,keyStr,value);
	}
//构造函数4
	public RBNode(RBNode parent,RBNode left,RBNode right,int color,int key,String keyStr,String value){
		super();
		this.parent = parent;
		this.left = left;
		this.right = right;
		this.color = color;
		this.key = key;
		this.keyStr = keyStr;
		this.value = value;
	}
	public RBNode(){
		
	}

	//get and set function
	public RBNode getLeft() {
		return left;
	}
	public void setLeft(RBNode left) {
		this.left = left;
	}
	public RBNode getRight() {
		return right;
	}
	public void setRight(RBNode right) {
		this.right = right;
	}
	public RBNode getParent() {
		return parent;
	}
	public void setParent(RBNode parent) {
		this.parent = parent;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKeyStr() {
		return keyStr;
	}
	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

}
