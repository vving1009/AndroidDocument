import java.util.Arrays;   
import java.util.List;   
import java.util.ArrayList;   
  
/**  
* Created by IntelliJ IDEA.  
* User: leizhimin  
* Date: 2007-12-3  
* Time: 16:58:24  
* Java5新特征之foreach语句使用总结  
*/  
public class TestArray {   
    public static void main(String args[]) {   
         TestArray test = new TestArray();   
         test.test1();   
         test.listToArray();   
         test.testArray3();   
  
     }   
  
    /**  
     * foreach语句输出一维数组  
     */  
    public void test1() {   
        //定义并初始化一个数组   
        int arr[] = {2, 3, 1};   
         System.out.println("----1----排序前的一维数组");   
        for (int x : arr) {   
             System.out.println(x); //逐个输出数组元素的值   
         }   
  
        //对数组排序   
         Arrays.sort(arr);   
  
        //利用java新特性for each循环输出数组   
         System.out.println("----1----排序后的一维数组");   
        for (int x : arr) {   
             System.out.println(x); //逐个输出数组元素的值   
         }   
     }   
  
    /**  
     * 集合转换为一维数组  
     */  
    public void listToArray() {   
        //创建List并添加元素   
         List<String> list = new ArrayList<String>();   
         list.add("1");   
         list.add("3");   
         list.add("4");   
  
        //利用froeach语句输出集合元素   
         System.out.println("----2----froeach语句输出集合元素");   
        for (String x : list) {   
             System.out.println(x);   
         }   
  
        //将ArrayList转换为数组   
         Object s[] = list.toArray();   
  
        //利用froeach语句输出集合元素   
         System.out.println("----2----froeach语句输出集合转换而来的数组元素");   
        for (Object x : s) {   
             System.out.println(x.toString()); //逐个输出数组元素的值   
         }   
     }   
  
    /**  
     * foreach输出二维数组测试  
     */  
    public void testArray2() {   
        int arr2[][] = {{4, 3}, {1, 2}};   
         System.out.println("----3----foreach输出二维数组测试");   
        for (int x[] : arr2) {   
            for (int e : x) {   
                 System.out.println(e); //逐个输出数组元素的值   
             }   
         }   
     }   
  
    /**  
     * foreach输出三维数组  
     */  
    public void testArray3() {   
        int arr[][][] = {   
                 {{1, 2}, {3, 4}},   
                 {{5, 6}, {7, 8}}   
         };   
  
         System.out.println("----4----foreach输出三维数组测试");   
        for (int[][] a2 : arr) {   
            for (int[] a1 : a2) {   
                for (int x : a1) {   
                     System.out.println(x);   
                 }   
             }   
         }   
     }   
}