package project1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BTree {
	private BNode root;
	private int t ;
	private int full;
	
//	构造函数
	public BTree(int t){
		this.root = new BNode();
		this.t = t;
		this.full = 2*t-1;
	}
	
	
	public void insert(int key,String keyStr,String value){
//		当根节点为空时
		if(root.getKey().size()==0){
			root.getKey().add(key);
			root.getKeyStr().add(keyStr);
			root.getValue().add(value);
		}
		this.BInsert(root, key,keyStr,value);
	}
	
	public void BInsert(BNode node,int key,String keyStr,String value){
//		当key值已经存在时，不再进行插入操作
		if(node.getKey().indexOf(key)!=-1)
			return;
//		当该节点的关键字数目达到上限时将该节点分裂
		if(node.getKey().size()>=full)
			node=split(node);
//		得到key值所应当插入的位置
		int index = searchIndex(node,key);
		
/*		
 * 当该节点的儿子不为空时，如果在该节点应当插入的位置处其儿子存在
 * 那么就递归的在其儿子的某位置处插入该关键字
 */
		if(node.getChildren().size()>0){
//			递归调用
			if(node.getChildren().get(index)!=null){
				BInsert(node.getChildren(index),key,keyStr,value);
			}else {
				node.getKey().add(index, key);
				node.getKeyStr().add(index,keyStr);
				node.getValue().add(index,value);
			}
		}else{
			node.getKey().add(index, key);
			node.getKeyStr().add(index,keyStr);
			node.getValue().add(index,value);
		}
	}
	
//在从上往下的过程中，分裂已经满的节点，即使不会在此处插入
		public BNode split(BNode node){	
			if(node.getKey().size()<full)
				return node;
			
			int temp1=t-1-1;
			int temp2=temp1+1;
			int temp3=full-1;
			BNode nodeFather = node.getFather();
			ArrayList<Integer> newNKey = new ArrayList<Integer>();
			ArrayList<String> newNKeyStr = new ArrayList<String>();
			ArrayList<String> newNValue = new ArrayList<String>();
//			newN是一个存有node中后一半元素的一个节点
			newNKey.addAll(node.getKey().subList(temp2+1, temp3+1));
			newNKeyStr.addAll(node.getKeyStr().subList(temp2+1, temp3+1));
			newNValue.addAll(node.getValue().subList(temp2+1, temp3+1));
			BNode newNode=new BNode(newNKey,newNKeyStr,newNValue);
//			
	        List<Integer> listKey = new LinkedList<Integer>();
	        List<String> listKeyStr = new LinkedList<String>();
	        List<String> listValue = new LinkedList<String>();
	        listKey.addAll(node.getKey().subList(0, temp1+1));
	        listKeyStr.addAll(node.getKeyStr().subList(0, temp1+1));
	        listValue.addAll(node.getValue().subList(0, temp1+1));
//	        当父节点是空时,产生父节点
	        if(nodeFather==null){
				nodeFather=new BNode();
//				将子节点的中间关键字给父节点
				nodeFather.getKey().add(node.getKey(temp2));
				nodeFather.getKeyStr().add(node.getKeyStr(temp2));
				nodeFather.getValue().add(node.getValue(temp2));
				nodeFather.getChildren().add(0,node);
				newNode.setFather(nodeFather);
				nodeFather.getChildren().add(1,newNode);
				node.setFather(nodeFather);
				root=nodeFather;
			}else{
				nodeFather.getKey().add(node.getPosition(node), node.getKey(temp2));
				nodeFather.getKeyStr().add(node.getPosition(node), node.getKeyStr(temp2));
				nodeFather.getValue().add(node.getPosition(node), node.getValue(temp2));
				newNode.setFather(nodeFather);
				nodeFather.getChildren().add(node.getPosition(node)+1,newNode);
			}
//	        
	        if(node.getChildren().size()>0){
	        	ArrayList<BNode> newSons=new ArrayList<BNode>();
	        	ArrayList<BNode> sons=new ArrayList<BNode>();
	        	newSons.addAll(node.getChildren().subList(t, 2*t));
	        	for(int i=0;i<=newSons.size()-1;i++){
	        		newSons.get(i).setFather(newNode);
	        	}
	        	sons.addAll(node.getChildren().subList(0, t));
	        	newNode.setChildren(newSons);
	        	node.getChildren().clear();
	        	node.getChildren().addAll(sons);
	        	
	        }
	        node.getKey().clear();
	        node.getKeyStr().clear();
	        node.getValue().clear();
	        node.getKey().addAll(listKey);
	        node.getKeyStr().addAll(listKeyStr);
	        node.getValue().addAll(listValue);
	        return split(nodeFather);
		}
	
//	插入数据的方法
//	public void BInsert(int k,String value){
//		BNode r = root;
//		if(r.getN()>=full){
//			BNode s =new BNode();
//			root = s;
//			s.setLeaf(false);
//			s.setN(0);
//			s.insertChildren(0, r);
//			BSplitChild(s,0);
//			BInsertNonFull(s,k,value);
//		}
//		else{
//			BInsertNonFull(r,k,value);
//			}
//		}
//	public void BSplitChild(BNode x,int i){
//		BNode z = new BNode();
//		BNode y = x.getChildren(i);//y是x的第i个孩子
//		z.setLeaf(y.isLeaf());
//		z.setN(t-1);
//		for(int j=0;j<t-1;j++){
//			z.insertKey(j, y.getKey(j+t));
//		}
//		
////		当y不是叶节点时，将y的儿子后半段指向z
//		if(!y.isLeaf()){
//			for(int j=0;j<t;j++){
//				z.insertChildren(j, y.getChildren(j+t));
//				
//			}
//		}
////		y.setN(t-1);
//
//		y.setN(t-1);
////		将z插入x的相应位置
//		for(int j=x.getN();j>i+1;j--){
//			x.insertChildren(j, x.getChildren(j-1));
//		}
//		x.insertChildren(i+1, z);
//		for(int j=x.getN();j>i;j--){
//			x.insertKey(j, x.getKey(j-1));
//		}
//		x.insertKey(i, y.getKey(t-1));
//		x.setN(x.getN()+1);
//		y.setFather(x);
//		int p = y.getKey().size();
//		int q = p-t+1;
//		for(int d=t-1;d<=q;d++){
//			y.removeKey(t-1);
//		}
////		z.get
//		for(int d=0;d<z.getChildren().size();d++){
//			z.getChildren(d).setFather(z);
//		}
//		for(int d=0;d<y.getChildren().size();d++){
//			y.getChildren(d).setFather(y);
//		}
//		z.setFather(x);
//		
//	}
//	
////	在非满的B树种插入数据
//	public void BInsertNonFull(BNode x,int k,String value){
//		int i = x.getN();
//		if(x.isLeaf()){
//			while(i>=1&&k<x.getKey(i-1)){
//				x.insertKey(i, x.getKey(i-1));
//				i=i-1;
//			}
//			x.insertKey(i, k);
//			x.setN(x.getN()+1);
//		}else{
//			while(i>=1&&k<x.getKey(i-1)){
//				i=i-1;
//			}
////			i=i+1;
//			if(x.getChildren(i).getN()==2*t-1){
//				BSplitChild(x, i);
//				if(k>x.getKey(i))
//					i=i+1;
//			}
//			BInsertNonFull(x.getChildren(i), k, value);
//		}
//	}
//	
	String searchRe= "";
	public String search(int key){
		searchRe= "";
		String res = Bsearch(root, key);
		return res;
	}
//	String searchRe= "";
	public String Bsearch(BNode node,int key){
		int index = node.getKey().indexOf(key);
		if(index==-1){//当key的值不在x的关键字列表中时
			if(node.getChildren().size()>0){
				if(node.getFather()!=null||node.getKey().size()<=t-1){
					node=configKeys(node);
				}
				index = searchIndex(node,key); //得到key值所在的儿子序列的位置
				Bsearch(node.getChildren(index),key);
			}else if(index==-1){
//				searchRe = "";
			}
		}else{
			searchRe = node.getKeyStr(index)+"~"+node.getValue(index);
		}
		return searchRe;
	}
	public int delete(int key){
		int ye;
//		当根节点是空时停止
		if(root == null){
			return 0;
		}
		ye = this.BDelete(root, key);
		return ye;
	}
	
//	B树中的删除方法
	int exist = 1;
	public int BDelete(BNode x,int key){
		int index = x.getKey().indexOf(key);
		if(index==-1){//当key的值不在x的关键字列表中时
			if(x.getChildren().size()>0){
				if(x.getFather()!=null||x.getKey().size()<=t-1){
					x=configKeys(x);
				}
				index = searchIndex(x,key); //得到key值所在的儿子序列的位置
				BDelete(x.getChildren(index),key);
			}else if(index==-1){
				exist = 0;
			}
		}else{
			  delete_Combine(x,index); 
		}
		return exist;
	}
//	得到key值所在儿子序列的位置
	public int searchIndex(BNode x, int key){
		int index = 0;
	    if(x.getKey().size()>0){ 
	    	int begin = 0;
	    	int end = x.getKey().size()-1;
			int step = 0;
			if(end!=begin){
				while((end-begin)!=1){
					step=(end-begin)/2;    
					if(x.getKey().get(begin+step)>key){    
						end=end-step;    
					}else if(x.getKey(begin+step)<key){    
						begin=begin+step;    
					}else{    
                    	return begin+step;    
					}    
				}
			}
			if(key>=x.getKey(end)){    
				index=end+1;    
			}else if(key<=x.getKey(begin)){    
				index=begin;    
			}else    
				index=end; 
		}
			return index;
    }
	
//	B树种的删除操作需要的调整方法
	public void delete_Combine(BNode node,int index){
		 if(node.getChildren().size()>0){  
	            BNode left = searchLeft(node.getChildren(index+1));  
	            node.getKey().remove(index);
	            node.getKeyStr().remove(index);
	            node.getValue().remove(index);
	            node.getKey().add(index,left.getKey(0)); 
	            node.getKeyStr().add(index,left.getKeyStr(0)); 
	            node.getValue().add(index,left.getValue(0)); 
	            left.getKey().remove(0); 
	            left.getKeyStr().remove(0);
	            left.getValue().remove(0);
	            configKeys(left);  
	        }else{  
	            node.getKey().remove(index); 
	            node.getKeyStr().remove(index);
	            node.getValue().remove(index);
	            configKeys(node);  
	        }  
	}
	
//搜索node子节点中最左结点  
    public BNode searchLeft(BNode node){  
        if(node.getChildren().size()>0){  
            return searchLeft(node.getChildren(0));  
        }else{  
            return node;  
        }  
    }  
	
/*  
 *  检测node的左右兄弟的关键字个数，如果存在关键字大于t-1的
 *  那么就和向其借关键字，如果都不大于t-1，就和其中一个合并
 */
    public BNode configKeys(BNode node){  
        if(node.getKey().size()<=t-1){  
            BNode f=node.getFather();  
            BNode nodeRight=null;  
            BNode nodeLeft=null;  
            if(f==null)  
                return node;  
//            当node是第一个节点时
            if(node.getPosition(node)==0)  
                nodeRight=f.getChildren(node.getPosition(node)+1); 
//            当node是最后一个节点时
            else if(node.getPosition(node)==f.getKey().size())  
                nodeLeft=f.getChildren(node.getPosition(node)-1); 
//            当node不是第一和最后一个节点时
            else{  
                nodeLeft=f.getChildren(node.getPosition(node)-1);  
                nodeRight=f.getChildren(node.getPosition(node)+1);  
            }  
              
//            当node的右兄弟的关键字大于t-1时
            if(nodeRight!=null&&nodeRight.getKey().size()>t-1){  
                int temp=f.getKey(node.getPosition(node));
                String tempp=f.getKeyStr(node.getPosition(node));
                String temppp=f.getValue(node.getPosition(node));
                f.getKey().remove(node.getPosition(node));
                f.getKeyStr().remove(node.getPosition(node));
                f.getValue().remove(node.getPosition(node));
                f.getKey().add(node.getPosition(node), nodeRight.getKey(0));
                f.getKeyStr().add(node.getPosition(node), nodeRight.getKeyStr(0));
                f.getValue().add(node.getPosition(node), nodeRight.getValue(0));
                nodeRight.getKey().remove(0); 
                nodeRight.getKeyStr().remove(0);
                nodeRight.getValue().remove(0);
                node.getKey().add(temp);
                node.getKeyStr().add(tempp);
                node.getValue().add(temppp);
                if(nodeRight.getChildren().size()>0){  
                    BNode n=nodeRight.getChildren(0);  
//    zhuyi                n.position=node.sons.size();  
                    n.setFather(node);  
                    node.getChildren().add(n);  
                    nodeRight.getChildren().remove(0);  
//                    for(int i=0;i<nodeRight.getChildren().size();i++)  
//                        nodeRight.getChildren(i).position=i;  
                }  
                return node;  
//                当node的左兄弟的关键字大于t-1时
            }else if(nodeLeft!=null&&nodeLeft.getKey().size()>t-1){  
                int temp0 = f.getKey(node.getPosition(node)-1); 
                String temp1 = f.getKeyStr(node.getPosition(node)-1);
                String temp2 = f.getValue(node.getPosition(node)-1);
                
                f.getKey().remove(node.getPosition(node)-1);
                f.getKeyStr().remove(node.getPosition(node)-1);
                f.getValue().remove(node.getPosition(node)-1);
                
                f.getKey().add(node.getPosition(node)-1, nodeLeft.getKey(nodeLeft.getKey().size()-1));  
                f.getKeyStr().add(node.getPosition(node)-1, nodeLeft.getKeyStr(nodeLeft.getKeyStr().size()-1));
                f.getValue().add(node.getPosition(node)-1, nodeLeft.getValue(nodeLeft.getValue().size()-1));
                
                nodeLeft.getKey().remove(nodeLeft.getKey().size()-1);
                nodeLeft.getKeyStr().remove(nodeLeft.getKeyStr().size()-1);
                nodeLeft.getValue().remove(nodeLeft.getValue().size()-1);
                
                node.getKey().add(0,temp0);
                node.getKeyStr().add(0,temp1);
                node.getValue().add(0,temp2);
                
                if(nodeLeft.getChildren().size()>0){  
                    BNode n=nodeLeft.getChildren(nodeLeft.getChildren().size()-1);  
//                    n.position=0;  
                    n.setFather(node);  
                    node.getChildren().add(0,n);  
//                    for(int i=1;i<node.getChildren().size();i++)  
//                        node.getChildren().get(i).position=i;  
                    nodeLeft.getChildren().remove(nodeLeft.getChildren().size()-1);  
                }  
                return node;
//                当node的左右兄弟的关键字都不大于t-1时
            }else{  
                if(nodeLeft!=null){  
                    combine(nodeLeft,node);  
                    return nodeLeft;  
                }else if(nodeRight!=null){  
                    combine(node,nodeRight);  
                    return node;  
                }  
            }  
        }  
        return node;  
    }  
    
    
    //合并两个节点  
    public void combine(BNode node1,BNode node2){  
        BNode f=node1.getFather();  
        if(f.getChildren().size()==2){  
            node1.getKey().addAll(f.getKey()); 
            node1.getKeyStr().addAll(f.getKeyStr());
            node1.getValue().addAll(f.getValue());
            
            node1.getKey().addAll(node2.getKey());
            node1.getKeyStr().addAll(node2.getKeyStr());
            node1.getValue().addAll(node2.getValue());
            f.getChildren().remove(1);  
            node1.setFather(null);  
            root=node1;  
        }else{  
            node1.getKey().add(f.getKey().get(node1.getPosition(node1)));  
            node1.getKeyStr().add(f.getKeyStr().get(node1.getPosition(node1)));
            node1.getValue().add(f.getValue().get(node1.getPosition(node1)));
            
            node1.getKey().addAll(node2.getKey()); 
            node1.getKeyStr().addAll(node2.getKeyStr());
            node1.getValue().addAll(node2.getValue());
            
            f.getKey().remove(node1.getPosition(node1));
            f.getKeyStr().remove(node1.getPosition(node1));
            f.getValue().remove(node1.getPosition(node1));
            
            f.getChildren().remove(node2.getPosition(node2));  
        }  
//        for(int i=node2.getPosition(node2);i<f.getChildren().size();i++)  
//            f.sons.get(i).position=i;  
        for(int i=0,j=node1.getChildren().size();i<node2.getChildren().size();i++,j++){  
//            node2.sons.get(i).position=j;  
            node2.getChildren(i).setFather(node1);  
        }  
        node1.getChildren().addAll(node2.getChildren());  
        configKeys(f);  
          
    }  

	public static void main(String[] args){
		BTree b = new BTree(2);
		int[] keys = {4,5,6,1,3,2,26,8,9,10,12,13,14};
		for(int i=0;i<keys.length;i++){
			b.insert(keys[i],keys[i]+"",keys[i]+"s");
		}
		
//		System.out.println(b.root.getKey(0)+"  ");//b.root.getChildren(0).getKey(0));
		
		b.printTree();
//		System.out.println(b.delete(20));
		
//	        for(int key: b.root.getKey()){  
//	            b.delete(key);  
//	            System.out.println(key);  
//	            b.printTree();  
//	        }  
//		for(int i=0;i<20;i++){
//			
//		}
		
//		System.out.println("\n\n删除节点4后：");
//		 b.delete(4);
//		 b.printTree();
		
		
//		 b.add(3);
//		 b.printTree();
	}
	
	  public void printTree(){  
//	        String keys="";  
//	        keys+=this.printNode(root);  
//	        System.out.println(keys);  
		  System.out.println("先序遍历：");
		  String ree = preOrder_print(root);
		  System.out.println(ree);
		  
		  System.out.println("\n后序遍历：");
		  String rePo = postOrder_print(root);
		  System.out.println(rePo);
		  
	    }  
	  
	  
//	    先序遍历
//	  int c = 0;
	  String str="";
	  public String preOrder_print(BNode node){
		
		System.out.println("作为儿子节点的位置是："+node.getPosition(node)+""
								+ "\n关键字有："+node.getKeyStr().toString()+""
							    + "\n汉语释义为"+node.getValue().toString()+"\n");
		str += node.getKeyStr().toString();
		if(node.getChildren().size()>0){
			for(BNode n:node.getChildren()){
				preOrder_print(n);
			}
//			preOrderWalk(node.getChildren(c++));
		}
//		System.out.println("\n先序遍历结束！");
		return str;
	  }

//	  后序遍历
	  String strPo = "";
	  public String postOrder_print(BNode node){
		  if(node.getChildren().size()>0){
				for(BNode n:node.getChildren()){
					postOrder_print(n);
				}
//				preOrderWalk(node.getChildren(c++));
		  }
		  System.out.println("作为儿子节点的位置是："+node.getPosition(node)+""
					+ "\n关键字有："+node.getKeyStr().toString()+""
				    + "\n汉语释义为"+node.getValue().toString()+"\n");
		  strPo += node.getKeyStr().toString();
		  return strPo;
	  }
	  
	  public String printNode(BNode node){  
	        String str=" \n作为儿子节点的位置 "+node.getPosition(node)+" "+node.getValue().toString();  
//	        str+=node.getKey().toString();  
	        if(node.getChildren().size()>0){  
	            for(BNode n:node.getChildren()){  
	                str+=printNode(n);  
	            }  
	        }  
	        return str;  
	    }  
	
}

class BNode{
	private boolean leaf;
	private int n;
//	private int key;
	private ArrayList<Integer> key = new ArrayList<Integer>();
	private ArrayList<String> keyStr = new ArrayList<String>();
//	private int[] key;
	private ArrayList<String> value = new ArrayList<String>();
	private ArrayList<BNode> children = new ArrayList<BNode>();
	private BNode father = null;
//	public int position;
//	constructor
	public BNode(){
		this.leaf = true;
		this.n = 0;
	}
	
	public BNode(ArrayList<Integer> keys,ArrayList<String> keyStr,ArrayList<String> value){
		this.key=keys;
		this.keyStr = keyStr;
		this.value = value;
	}
//	得到特定位置处的key值
	int getKey(int i){
		return key.get(i);
	}
//	得到特定位置处的key值
	String getKeyStr(int i){
		return keyStr.get(i);
	}
//	得到特定位置处的value值
	String getValue(int i){
		return value.get(i);
	}
//	得到特定位置的children
	BNode getChildren(int i){
		return children.get(i);
	}
//	添加key
	void insertKey(int i,int k){
		key.add(i,k);
	}
//	添加value
	void insertKeyStr(int i,String nkeyStr){
		keyStr.add(i,nkeyStr);
	}
//	添加value
	void insertValue(int i,String v){
		value.add(i,v);
	}
//	添加children
	void insertChildren(int i,BNode ch){
		children.add(i,ch);
	}
//	删除特定的key
	void removeKey(int i){
		key.remove(i);
	}
//	删除特定的keyStr
	void removeKeyStr(int i){
		keyStr.remove(i);
	}
//	删除特定的value
	void removeValue(int i){
		value.remove(i);
	}
//	删除特定的child
	void removeChildren(int i){
		children.remove(i);
	}
//  得到某个元素在数组中的下标位置
  public int getPosition(BNode node){
  	BNode fa = node.getFather();
  	int re = -1;
  	if(fa==null){
  		re = 0;
  	}else {
  		for(int d=0;d<fa.getChildren().size();d++){
  			if(fa.getChildren(d)==node){
  				re = d;
  			}
  		}
  	}
  	return re;
  }
	
	
	public ArrayList<String> getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(ArrayList<String> keyStr) {
		this.keyStr = keyStr;
	}

	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public ArrayList<Integer> getKey() {
		return key;
	}
	public void setKey(ArrayList<Integer> key) {
		this.key = key;
	}
	public ArrayList<String> getValue() {
		return value;
	}
	public void setValue(ArrayList<String> value) {
		this.value = value;
	}
	public ArrayList<BNode> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<BNode> children) {
		this.children = children;
	}
	public BNode getFather() {
		return father;
	}
	public void setFather(BNode father) {
		this.father = father;
	}
	
	
}