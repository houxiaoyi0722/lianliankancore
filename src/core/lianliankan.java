package core;


import java.util.*;

public class lianliankan {
    private Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21
    };
    private static int xlength = 14;
    private static int ylength = 9;

    //7*12
    private static int[][] buju = new int[ylength][xlength];
    //随机数取值范围
    private int domf = 84;

    private Random random;

    public int[][] getBuju() {
        return buju;
    }

    public void llkbuju(){

        ArrayList<Integer> integers = new ArrayList<>();
        //转变为list
        integers.addAll(Arrays.asList(arr));
        /*for (int num : arr) {
            integers.add(num);
        }*/

        //随机数生成器
        random = new Random();

        //随机生成integers下标，已经存放的元素从integers删除
        for(int i = 1 ; i < 8 ; i++ ){
            for (int j = 1 ; j < 13 ; j++ ){
                int ran = random.nextInt(domf);
                buju[i][j] = integers.get(ran);
                integers.remove(ran);
                domf--;
            }
        }

        for(int i = 0 ; i < 9 ; i++ ){
            for (int j = 0 ; j < 14 ; j++ ){
                System.out.print(buju[i][j]+"\t");
            }
            System.out.println();
        }
    }
    /**
     * 判断是否可以消除的总方法
     *
     * */
    public  boolean isOk(int aXIndex,int aYIndex,int bXIndex,int bYIndex){
        boolean isok = false;

        if (buju[aYIndex][aXIndex]!=buju[bYIndex][bXIndex])//判断两个元素内容是否相同
            return false;
        if(aXIndex==bXIndex || aYIndex==bYIndex)//是否是同一行或同一列
            isok = isConnLine(aXIndex,aYIndex,bXIndex,bYIndex);
        if(!isok)//判断两行的情况
            isok = isConnTwoLine(aXIndex,aYIndex,bXIndex,bYIndex);
        if(!isok)//判断三行的情况
            isok = isConnThreeLine(aXIndex,aYIndex,bXIndex,bYIndex);
        return isok;
    }
    /**
     * a\b同一行或列,两种情况，相邻和不相邻
     * */
    private boolean isConnLine(int aXIndex,int aYIndex,int bXIndex,int bYIndex){

        if (isNextTo(aXIndex,aYIndex,bXIndex,bYIndex))
            return true;
        else if(isCounnectionLine(aXIndex,aYIndex,bXIndex,bYIndex))
            return true;

        return false;
    }

    //判断不相邻的是否可以消除
    private boolean isCounnectionLine(int aXIndex, int aYIndex, int bXIndex, int bYIndex) {
        if (aXIndex == bXIndex){
            return isHasNoZero(aYIndex,bYIndex,aXIndex,false);
        }else if(aYIndex == bYIndex){
            return isHasNoZero(aXIndex,bXIndex,aYIndex,true);
        }

        return false;
    }
    //X或Y轴ab之间是否有不为0的值 true判断横行false判断竖列
    private boolean isHasNoZero(int Index1, int Index2, int OtherIndex,Boolean trueXOrfalseY) {
        int max = (Index1 > Index2)? Index1 : Index2;
        int min = (Index1 < Index2)? Index1 : Index2;
        for (int i = min+1 ; i<max ; i++){
            if(trueXOrfalseY){
                if(buju[OtherIndex][i] != 0)
                    return false;
            } else{
                if(buju[i][OtherIndex] != 0)
                    return false;
            }
        }
        return true;
    }

    /*//X轴ab之间是否有不为0的值
    private boolean isXHasNoZero(int aXIndex, int bXIndex,int xOtherIndex) {

        int max = (aXIndex > bXIndex)? aXIndex : bXIndex;
        int min = (aXIndex < bXIndex)? aXIndex : bXIndex;
        for (int i = min+1 ; i<max ; i++){
            if(buju[xOtherIndex][i]!=0)
                return false;
        }
        return true;

    }*/

    //判断是否相邻
    private Boolean isNextTo(int aXIndex,int aYIndex,int bXIndex,int bYIndex){

        if (aXIndex == bXIndex && aYIndex-bYIndex == 1 || aYIndex-bYIndex == -1)
            return true;
        if (aYIndex == bYIndex && aXIndex-bXIndex == 1 || aXIndex-bXIndex == -1)
            return true;
        return false;

    }

    /**
     * a\b对角 - 两条线 两横两竖 一路通即可
     * */
        // |------->
        // | a--| |--a
        // |    | |
        // \/   b b
    private boolean isConnTwoLine(int aXIndex, int aYIndex, int bXIndex, int bYIndex) {

        int ax = aXIndex;
        int ay = aYIndex;
        int by = bYIndex;
        int bx = bXIndex;
        // |------------------->
        // | a--| |--a
        // |    | |
        // \/   b b
        if (aYIndex>bYIndex){
            ay++;
            by--;
        }
        else{
            ay--;
            by++;
        }
        // |------------->
        // |     b b
        // |     | |
        // \/ a--| |--a
        if (aXIndex>bXIndex) {
            bx--;
            ax++;
        }else {
            bx++;
            ax--;
        }

        boolean leftToRight = isHasNoZero(aXIndex, bx, aYIndex, true);
        boolean downToTop = isHasNoZero(ay, bYIndex, bXIndex,false);
        boolean topToDown = isHasNoZero(aYIndex,by,aXIndex,false);
        boolean rightToLeft = isHasNoZero(bXIndex,ax,bYIndex,true);

        return (leftToRight && downToTop || topToDown && rightToLeft)||(leftToRight && topToDown || rightToLeft && downToTop);
    }

    /**
     * 三条线的情况
     *
     *第一层循环先获取目标上下或左右的数组 向边界遍历取值，为0开始做第二条路径判断，至第一个不为零的位置截至
     *开始
     *第二层 为0的向目标二的位置遍历，为0开始第三层做第三条路径判断，至第一个不为零的位置截至
     *
     *第三层 从第二条路径向目标二遍历，遍历第三条路径上的元素，若全为0，return true ，第一个不为零处截止
     * */
    private Boolean isConnThreeLine(int aXIndex,int aYIndex,int bXIndex,int bYIndex){

        //1:上 2:右 3:下 4:左
        for(int dire = 1; dire<=4 ; dire++ ){
            //控制isHasNoZero方法遍历方向
            boolean xOry = false;
            int aidx = aXIndex;
            int bidx = bXIndex;
            int aidy = aYIndex;
            int bidy = bYIndex;
            //一层遍历初识值
            int temp;
            //一层遍历边界
            int lengths = 0;
            //遍历迭代方向
            boolean plusOrCut = false;

            //调整各个方向的参数值
            if(dire == 1){

                temp = aYIndex-1;
                xOry = true;
                bidx = (aXIndex>bXIndex)? bXIndex-1 : bXIndex+1;


            }else if(dire == 2){

                temp = aXIndex+1;
                bidy = (aYIndex>bYIndex)? bYIndex+1 :bYIndex-1;
                lengths = xlength-1;

            }else if(dire == 3){

                temp = aYIndex+1;
                xOry = true;
                bidx = (aXIndex>bXIndex)? bXIndex-1 : bXIndex+1;
                lengths = ylength-1;

            }else{

                temp = aXIndex-1;
                bidy = (aYIndex>bYIndex)? bYIndex+1 :bYIndex-1;

            }


            //第一层从a向外判断
            while ( (plusOrCut)? temp <= lengths : temp>=lengths ){//向边界遍历++ 和-- 两种情况的边界条件


                //第一层
                if(( (xOry)? buju[temp][aXIndex] : buju[aYIndex][temp] )== 0){//调整遍历数组维度
                    //第二层&&第三层
                    //  |--------
                    //  |       |
                    //  |       |
                    //

                    //上下  or 左右
                    if(xOry)
                        if(isHasNoZero(aidx,bidx,temp,xOry) && isHasNoZero(temp,bidy,bXIndex,!xOry))
                            return true;
                    else
                        if(isHasNoZero(aidy,bidy,temp,xOry) && isHasNoZero(temp,bidx,bYIndex,!xOry))
                            return true;

                }

                if (plusOrCut) {
                    temp++;
                } else {
                    temp--;
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {

        lianliankan lianliankan = new lianliankan();
        lianliankan.llkbuju();

        while(true){
            Scanner scan = new Scanner(System.in);
            System.out.println("请输入x，y，x，y");
            int ax = Integer.parseInt(scan.next());
            int ay = Integer.parseInt(scan.next());
            int bx = Integer.parseInt(scan.next());
            int by = Integer.parseInt(scan.next());
            if (ax<0 || ax>buju[1].length || bx<0 || bx>buju[1].length || ay<0 || ay > 8 || by<0 || by > 8 || ax == bx && ay == by){
                System.out.println("无效的输入");
                continue;
            }
            if (lianliankan.isOk(ax,ay,bx,by)){
                lianliankan.buju[ay][ax] = 0;
                lianliankan.buju[by][bx] = 0;
            }else{
                System.out.println("错误请重新输入");
            }

            for(int i = 0 ; i < 9 ; i++ ){
                for (int j = 0 ; j < 14 ; j++ ){
                    System.out.print(lianliankan.buju[i][j]+"\t");
                }
                System.out.println();
            }

        }

    }
}
