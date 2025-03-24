package edu.baylor.cs;

import java.util.ArrayList;
import java.util.List;

public class MyListDemo extends URLLoader {

    protected List<String> list = new ArrayList<>();

    // Task 3
    @Override
//    protected void processLine(String[] tokens) {
//        list.add(tokens);
//    }
    // Task 4
    protected void processLine(String[] tokens) {
    }

    public static void main(String[] args) {
        MyListDemo myListDemo = new MyListDemo();
        myListDemo.loadData();
        System.out.println(myListDemo.list.size());
    }
}
