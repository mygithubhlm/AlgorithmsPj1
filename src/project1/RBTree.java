package project1;

public class RBTree {
	private RBNode root;
//	定义nil
	private  RBNode nil = new RBNode(null,null,null,0,-1,null,null);
	
	
//	定义红色和黑色
	public static final int BLACK = 0;
	public static final int RED = 1;
    public RBTree(){
    	root = nil;
    }
    
    public RBTree(RBNode root){
    	this.root = root;
    }
    
    public void insert(int key,String keyStr,String value){
    	RBNode a = new RBNode(key,keyStr,value);
    	RBInsert(a);
    }
//插入方法
	public void RBInsert(RBNode z){
		RBNode y = nil;
		RBNode x = root;
			
//		找到z应该在的位置的父亲节点y
		while (x!=nil){
			y = x;
			if(z.getKey()<x.getKey()){
				x = x.getLeft();
			}else 
				x = x.getRight();
		}
//		将z的父亲指向y
		z.setParent(y);
		if(y == nil){
			root = z;
		}else if(z.getKey()<y.getKey()){
			y.setLeft(z);
		}else 
			y.setRight(z);
		z.setLeft(nil);
		z.setRight(nil);
		z.setColor(RED);
			
//		FIXUP function
		RBInsertFixup(z);
		
	}
		
//	插入后的调整方法
	public void RBInsertFixup(RBNode z){
		while(z.getParent().getColor()==RED){
			if(z.getParent()==z.getParent().getParent().getLeft()){//z的父亲是z的祖父的左二子
				RBNode y = z.getParent().getParent().getRight();
				if(y.getColor()==RED){
					z.getParent().setColor(BLACK);
					y.setColor(BLACK);
					z.getParent().getParent().setColor(RED);
					z = z.getParent().getParent();
				}else if(z==z.getParent().getRight()){
						z = z.getParent();
						LeftRotation(z);
					}else{
						z.getParent().setColor(BLACK);
						z.getParent().getParent().setColor(RED);
						RightRotation(z.getParent().getParent());
				}
			}else{//z的父亲是其祖父的右儿子
				RBNode y = z.getParent().getParent().getLeft();
				if(y.getColor()==RED){
					z.getParent().setColor(BLACK);
					y.setColor(BLACK);
					z.getParent().getParent().setColor(RED);
					z = z.getParent().getParent();
				}else if(z==z.getParent().getLeft()){
						z = z.getParent();
						RightRotation(z);
					}else{
						z.getParent().setColor(BLACK);
						z.getParent().getParent().setColor(RED);
						LeftRotation(z.getParent().getParent());
				}
			}
		}
		root.setColor(BLACK);
	}
		
//	左旋转方法
	public void LeftRotation(RBNode x){
		RBNode y = x.getRight();   //set y
		x.setRight(y.getLeft());     //turn y's left subtree into x's right subtree
		if(y.getLeft()!= nil){
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());  //link x's parent to y
		if(x.getParent() == nil){
			root = y;
		}else if(x==x.getParent().getLeft()){
			x.getParent().setLeft(y);
		}else {
			x.getParent().setRight(y);
		}
		y.setLeft(x);         //put x on y's left
		x.setParent(y);
	}
//	右旋转方法
	public void RightRotation(RBNode x){
		RBNode y = x.getLeft();   //set y
		x.setLeft(y.getRight());     //turn y's right subtree into x's left subtree
//		System.out.println((!y.getRight().equals(nil))+""+y.getKey()+"color");
		if(y.getRight()!=nil){
			y.getRight().setParent(x);
//			System.out.println("fffff "+y.getRight().getKey());
		}
		y.setParent(x.getParent());    //link x's parent to y
		if(x.getParent() == nil){
			root = y;
		}else if(x==x.getParent().getRight()){
			x.getParent().setRight(y);
		}else {
			x.getParent().setLeft(y);
		}
		y.setRight(x);          //put x on y's right
		x.setParent(y); 
	}
		
//	搜索方法
	public RBNode search(int key){
		RBNode x = root;   //开始搜索的地方记为x
		while((x!= nil)&&(key!=x.getKey())){
			if (key<x.getKey()){
				x=x.getLeft();
			}else 
				x=x.getRight();
		}
		return x;
	}
		
//		两个节点转移（为删除过程调用的子过程）
		public void RBTransplant(RBNode u,RBNode v){
//			将u用v来替换
			if(u.getParent() == nil){
				root = u;
			}else if(u==u.getParent().getLeft()){
				u.getParent().setLeft(v);
			}else {
				u.getParent().setRight(v);
			}
			v.setParent(u.getParent());
		}
//		寻找最小的元素的方法
		public RBNode TreeMinimum(RBNode x){
			while(x.getLeft()!= nil){
				x = x.getLeft();
			}
			return x;
		}
		
		public int delete(int key){
			RBNode a = search(key);
			if(a==nil)
				return -1;
			else {
				RBDelete(a);
				return 0;
			}
		}
//		删除方法
		public void RBDelete(RBNode z){
			RBNode y = z;
			int yOriginalColor = y.getColor();//记录下z的初始颜色
			RBNode x ;
			if (z.getLeft()==nil){//左儿子为空时
				x = z.getRight();
				RBTransplant(z, z.getRight());
			}else if(z.getRight()==nil){//右儿子为空时
				x = z.getLeft();
				RBTransplant(z, z.getLeft());
			}else {                //有左右儿子都不为空时
//				System.out.println("zdeyou："+z.getRight().getKey());
//				找到z的后继
				y = TreeMinimum(z.getRight());
				yOriginalColor = y.getColor();
				x = y.getRight();
				if(y.getParent()==z){
					x.setParent(y);
				}else{
					RBTransplant(y, y.getRight());
					y.setRight(z.getRight());
					y.getRight().setParent(y);
				}
				RBTransplant(z, y);
				y.setLeft(z.getLeft());
				y.getLeft().setParent(y);
				y.setColor(z.getColor());
			}
			if(yOriginalColor == BLACK){
				RBDeleteFixup(x);
			}
		}
//		删除后的调整
		public void RBDeleteFixup(RBNode x){
			while((x!=root)&&(x.getColor()==BLACK)){
//				System.out.println("xde "+x.getParent());
				if(x==(x.getParent().getLeft())){   //当x是其父亲的左儿子时
					RBNode w = x.getParent().getRight();  //w是的sibling
					if(w.getColor()==RED){   //w的颜色为红色时
						w.setColor(BLACK);
						x.getParent().setColor(RED);
						LeftRotation(x.getParent());
						w = x.getParent().getRight();
					}
	                if(w.getLeft().getColor() == BLACK&&w.getRight().getColor() == BLACK){//当w的左右儿子都是黑色的时候
	                	w.setColor(RED);
	                	x=x.getParent();
	                }else if(w.getRight().getColor()==BLACK){                   //当w的右儿子的颜色是黑色的时候
	                	w.getLeft().setColor(BLACK);
	                	w.setColor(RED);
	                	RightRotation(w);
	                	w = x.getParent().getRight();
	                }
	                w.setColor(x.getParent().getColor());
	                x.getParent().setColor(BLACK);
	                w.getRight().setColor(BLACK);
	                LeftRotation(x.getParent());
	                x=root;
	                }else{      //当x是其父亲的右儿子时
	                	RBNode w = x.getParent().getLeft();  //w是的sibling
	    				if(w.getColor()==RED){   //w的颜色为红色时
	    					w.setColor(BLACK);
	    					x.getParent().setColor(RED);
	    					LeftRotation(x.getParent());
	    					w = x.getParent().getLeft();
	    				}
	                    if(w.getRight().getColor() == BLACK&&w.getLeft().getColor() == BLACK){//当w的左右儿子都是黑色的时候
	                    	w.setColor(RED);
	                    	x=x.getParent();
	                    }else if(w.getLeft().getColor()==BLACK){                   //当w的右儿子的颜色是黑色的时候
	                    	w.getRight().setColor(BLACK);
	                    	w.setColor(RED);
	                    	RightRotation(w);
	                    	w = x.getParent().getLeft();
	                    }
	                    w.setColor(x.getParent().getColor());
	                    x.getParent().setColor(BLACK); 
	                    w.getLeft().setColor(BLACK);
	                    LeftRotation(x.getParent());
	                    x=root;
	                }
			}
			x.setColor(BLACK);
		}
		
		//遍历红黑树
		public void printTree() {
			System.out.println("\n先序遍历:\n");
			preOrder_print(root);
			System.out.println("\n中序遍历:\n");
			inOrder_print(root);
			System.out.println("\n后序遍历:\n");
			postOrder_print(root);
		}
//		先序遍历
		public void preOrder_print(RBNode node){
			if(node != nil){
				System.out.println(" 节点关键字："+node.getKeyStr() + " 颜色为：" + getColorOfNode(node.getColor())+" 值为："+node.getValue());
				preOrder_print(node.getLeft());
				preOrder_print(node.getRight());
			}
		}
//		中序遍历
		public void inOrder_print(RBNode node) {
			if (node != nil) {
				inOrder_print(node.getLeft());
				System.out.println(" 节点关键字："+node.getKeyStr() + " 颜色为：" +getColorOfNode(node.getColor())+" 值为："+node.getValue());
				inOrder_print(node.getRight());
			}
		}
//		后序遍历
		public void postOrder_print(RBNode node){
			if(node != nil){
				postOrder_print(node.getLeft());
				postOrder_print(node.getRight());
				System.out.println(" 节点关键字："+node.getKeyStr() + " 颜色为：" + getColorOfNode(node.getColor())+" 值为："+node.getValue());
			}
		}
//		得到颜色
		public static String getColorOfNode(int color0){
			String re = "";
			if(color0==0){
				re = "BLACK";
			}else 
				re= "RED";
			return re;
		}
		
		public static void main(String[] args){
			int[] aa = {10,2,12,4,6,8,1,9,7,3,11,5};
			RBTree T = new RBTree();
			System.out.println("插入之前的数组是：");
			for(int i=0;i<aa.length;i++){
//				RBNode a = new RBNode(aa[i],"sss");
				T.insert(aa[i],aa[i]+"","sss");
				System.out.print(aa[i]+",");
			}
			T.printTree();
			System.out.println("删除关键字3，2，9，4，8之后：");
			T.delete(3);
			T.delete(2);
			T.delete(9);
			T.delete(4);
			T.delete(8);
			T.printTree();
		}
		public RBNode getRoot() {
			return root;
		}
		public void setRoot(RBNode root) {
			this.root = root;
		}
		public RBNode getNil() {
			return nil;
		}
		public void setNil(RBNode nil) {
			this.nil = nil;
		}
	
}


