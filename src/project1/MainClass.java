package project1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainClass {
	static RBTree t = new RBTree();
	static BTree  bt = new BTree(10);
	public static void main(String[] args){
//		首先对数据进行初始化
		
//	 long time1 =  System.nanoTime()/1000L;
	 initial(0);
//	 long time2 =  System.nanoTime()/1000L;
	 
//	 System.out.println("\n初始化红黑树，即将1_initial.txt插入 所花的时间为："+(time2-time1)+"微秒");
	 System.out.println("两棵树已经初始化完毕！");
	 System.out.println("请选择您要进行操作的树，h: 红黑树，b: B树：");
	 Scanner choTree = new Scanner(System.in);
	 String choStr = choTree.nextLine();
//	 当选择了红黑树时
	 if(choStr.equals("h")){
		 System.out.println("您已经选择了红黑树，您可以用如下指令进行操作：\n0 输入“ex”退出操作!\n1 输入“addw”添加单词\n2 输入“impo”导入新文件"
			 		+ "\n3 输入“delw”删除单词\n4 输入“delf”删除指定文件里的所有单词\n5 输入“sers”进入批量查询\n6 输入要查询的单词进行查询\n\n");
//		 t.printTree();
		 Scanner input = new Scanner(System.in);
		
		 RBNode re = new RBNode() ;
		 while(true){
			
			 String aStr = input.nextLine();
			 try{
				 if(aStr.equals("ex")){
                     System.out.println("您已成功退出操作!");
					 break;
				 }else if(aStr.equals("impo")){
					 initial(1);
					 System.out.println("已经成功将制定文件3_insert中的内容插入！");
				 }else if(aStr.equals("addw")){
					 System.out.println("请输入您想要添加的单词：");
					 Scanner in0 = new Scanner(System.in);
					 String addW = in0.nextLine();
					 
					 System.out.println("请输入您想要添加的单词释意：");
					 Scanner in1 = new Scanner(System.in);
					 String addMean = in0.nextLine();
					 
					 int addKey = prepareForInsert(addW);
					 t.insert(addKey, addW, addMean);
					 System.out.println("已经添加您想要添加的单词"+addW);
				 }else if(aStr.equals("delw")){
					 System.out.println("请输入您想要删除的单词：");
					 Scanner in = new Scanner(System.in);
					 String deleteW = in.nextLine();
					 int delKey = prepareForInsert(deleteW);
					 int res = t.delete(delKey);
					 if(res == 0)
						 System.out.println("已经删除您想要删除的单词"+deleteW);
					 else {
						 System.out.println("该单词"+deleteW+"不在红黑树中！");
					}
				 }else if(aStr.equals("delf")){
//					 initial(2);
//					 long time1 =  System.nanoTime()/1000L;
					 deleteFile(1);
//					 long time2 =  System.nanoTime()/1000L;
//					 System.out.println("\n红黑树中删除文件 所花的时间为："+(time2-time1)+"微秒");
					 deleteFile(1);
					 System.out.println("已经成功将指定文件2_delete内的内容删除！");
				 }else if(aStr.equals("sers")){
					 System.out.println("请输入起始单词：");
					 Scanner scope = new Scanner(System.in);
					 String startStr = scope.nextLine();
					 System.out.println("请输入截止单词：");
					 String endStr = scope.nextLine();
					 
					 RBsearchSome(prepareForInsert(startStr), prepareForInsert(endStr));
				 }else {
					 if(aStr.equalsIgnoreCase("")){
					 }else{
//						 long time1 =  System.nanoTime()/1000L;
						 re = t.search(prepareForInsert(aStr));
//						 long time2 =  System.nanoTime()/1000L;
						 if(re.getValue()!=null) {
							 System.out.println(re.getValue());
//							 System.out.println("\n查询单词 所花的时间为："+(time2-time1)+"微秒");
						 }
						 else
							 System.out.println("抱歉！词库中没有您想要下查询的词！");
					 }
				}
				 
			 }catch(Exception e){
				 System.out.println("请确认您的输入正确!");
			 }
		 }
//		 当选择了B树时；
	 }else if(choStr.equalsIgnoreCase("b")){
		 initial(3);
//		 bt.printTree();
		 System.out.println("您已经选择了B树，您可以用如下指令进行操作：\n0 输入“ex”退出操作!\n1 输入“addw”添加单词\n2 输入“impo”导入新文件"
			 		+ "\n3 输入“delw”删除单词\n4 输入“delf”删除指定文件里的所有单词\n5 输入“sers”进入批量查询\n6  输入要查询的单词进行查询\n\n");
		 
		 Scanner Binput = new Scanner(System.in);
		 while(true){
			String BinputStr = Binput.nextLine();
			try{
			 if(BinputStr.equals("ex")){
				 System.out.println("您已成功退出操作!");
				 break;
			 }else if(BinputStr.equalsIgnoreCase("addw")){
				 System.out.println("请输入您想要添加的单词：");
				 Scanner Bin = new Scanner(System.in);
				 String BaddW = Bin.nextLine();
				 
				 System.out.println("请输入您想要添加的单词释意：");
				 Scanner Bin1 = new Scanner(System.in);
				 String BaddMean = Bin1.nextLine();
				 
				 int addKey = prepareForInsert(BaddW);
				 bt.insert(addKey, BaddW, BaddMean);
				 System.out.println("已经将您想要添加的单词"+BaddW+"添加到B树中！");
			 }else if(BinputStr.equalsIgnoreCase("delw")){
				 System.out.println("请输入您想要删除的单词：");
				 Scanner Bin2 = new Scanner(System.in);
				 String deleteW = Bin2.nextLine();
				 int delKey = prepareForInsert(deleteW);
				 int res = bt.delete(delKey);//res的值为0时，表示该值不在B树中
				 if(res == 1)
					 System.out.println("已经删除您想要删除的单词"+deleteW+"从B树中删除！");
				 else {
					 System.out.println("该单词"+deleteW+"不在B树中！");
				}
			 }else if(BinputStr.equalsIgnoreCase("delf")){
//				 long time1 =  System.nanoTime()/1000L;
				 deleteFile(2);
//				 long time2 =  System.nanoTime()/1000L;
//				 System.out.println("\nB树中删除文件 所花的时间为："+(time2-time1)+"微秒");
				 System.out.println("已经成功将指定文件2_delete内的内容从B树中删除！");
			 }else if(BinputStr.equalsIgnoreCase("impo")){
				 
				 initial(2);
				 System.out.println("已经成功将制定文件3_insert中的内容插入B树！");
			 }else if(BinputStr.equals("sers")){
				 System.out.println("请输入起始单词：");
				 Scanner scope2 = new Scanner(System.in);
				 String startStr2 = scope2.nextLine();
				 System.out.println("请输入截止单词：");
				 String endStr2 = scope2.nextLine();
				 
				 BsearchSome(prepareForInsert(startStr2), prepareForInsert(endStr2));
				 
			 }else {
				 if(BinputStr.equalsIgnoreCase("")){
				 }else{
//					 long time1 =  System.nanoTime()/1000L;
					 String re = bt.search(prepareForInsert(BinputStr));
//					 long time2 =  System.nanoTime()/1000L;
//					 System.out.println("\nB树中查询单词 所花的时间为："+(time2-time1)+"微秒");
					 if(re.equals("")){
						 System.out.println("抱歉！词库中没有您想要下查询的词！");
					 }else{
						 String[] reArr = re.split("~");
						 System.out.println(reArr[1]);
						 
					 }
				 }
			 }
		 }catch (Exception e) {
			// TODO: handle exception
			 System.out.println("请确认您的输入正确!");
		}
		 
	  } 
		 
//		 当输入不是h或者b时
	 }else 
		 System.out.println("您未选择任何树，操作已结束！");
	}
	
//	初始化红黑树和B树
	public static void initial(int boo){
		 FileInputStream file = null;
		 InputStreamReader isr = null;
		 BufferedReader br = null; //
		 try {
			 String str = "";
			 if(boo==0||boo==3)
				 file = new FileInputStream("E:/xiaoming/AlgorithmsPj1/src/project1/1_initial.txt");// 从指定的文件路径下读取文件
			 else if(boo==1||boo==2){
				 file = new FileInputStream("E:/xiaoming/AlgorithmsPj1/src/project1/3_insert.txt");// 从指定的文件路径下读取文件
			}
	   // 从文件系统中的某个文件中获取字节
			 
			 isr = new InputStreamReader(file,"utf-8");// InputStreamReader 是字节流通向字符流的桥梁,
			 br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
			 br.readLine();
			 while ((str = br.readLine()) != null) {
				 if(boo==3||boo==2)
					 bt.insert(prepareForInsert(str),str,br.readLine());
				 else 
					 t.insert(prepareForInsert(str),str,br.readLine());
				 
//				 System.out.println(str);// 打印出str
			 }
			 
		 } catch (FileNotFoundException e) {
			 System.out.println("找不到指定文件");
		 } catch (IOException e) {
			 System.out.println("读取文件失败");
		 } finally {
			 try {
				 br.close();
				 isr.close();
				 file.close();
	    // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
	}
//	批量删除
	public static void deleteFile(int de){
		 FileInputStream file = null;
		 InputStreamReader isr = null;
		 BufferedReader br = null; //
		 try {
			 String str = "";
			 file = new FileInputStream("E:/xiaoming/AlgorithmsPj1/src/project1/2_delete.txt");// 从指定的文件路径下读取文件
	   // 从文件系统中的某个文件中获取字节
			 
			 isr = new InputStreamReader(file,"utf-8");// InputStreamReader 是字节流通向字符流的桥梁,
			 br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
			 br.readLine();
			 while ((str = br.readLine()) != null) {
				 if(de==1)
					 t.delete(prepareForInsert(str));
				 else 
					 bt.delete(prepareForInsert(str));
//				 System.out.println(str);// 打印出str
//				 br.readLine();
			 }
			 
		 } catch (FileNotFoundException e) {
			 System.out.println("找不到指定文件");
		 } catch (IOException e) {
			 System.out.println("读取文件失败");
		 } catch(Exception a){
//			System.out.println("youcuo"); 
		 }finally {
			 try {
				 br.close();
				 isr.close();
				 file.close();
	    // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
	}
	
//	红黑树的批量查询
	public static void RBsearchSome(int startKey,int endKey){
		while(startKey<=endKey){
        	RBNode res = t.search(startKey);
        	if(res.getValue()!=null){
        		System.out.println(res.getKeyStr()+":"+res.getValue()+", ");
        	}
			startKey+=10;
		}
	}
//	B树的批量查询
	public static void BsearchSome(int startKey2,int endKey2){
		while(startKey2<=endKey2){
        	String res = bt.search(startKey2);
        	if(res.equals("")){
        	}else{
        		String[] resArr = res.split("~");
        		System.out.println(resArr[0]+":"+resArr[1]+", ");
        		res="";
        	}
			startKey2+=10;
			
		}
	}
	
//	插入准备
   public static int prepareForInsert(String keyStr){
	    int a = 0;
		for(int i=0;i<keyStr.length();i++){
			a += keyStr.charAt(i)*getPower(i);
		}
		return a;
//		System.out.println(a);
   }
//
   public static int getPower(int n){
	     int result = 1;
	     for(int i=0;i<n;i++){
	    	result *= 10; 
	     }
	     return result;
	  }
}
