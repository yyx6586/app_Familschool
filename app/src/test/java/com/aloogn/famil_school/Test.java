package com.aloogn.famil_school;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/1710:30 PM
 * desc   :
 */
public class Test {
    public static void main(String[] args){

        MyThead myThead1 = new MyThead(1);
        myThead1.start();

        MyThead myThead2 = new MyThead(2);
        myThead2.start();

        MyThead myThead3 = new MyThead(3);
        myThead3.start();

        MyThead myThead4 = new MyThead(4);
        myThead4.start();
    }
}

class MyThead extends Thread{
    private int count;

    public MyThead(int count){
        this.count = count;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("count:"+count);

    }
}
