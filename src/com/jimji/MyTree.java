package com.jimji;

public class MyTree<T> extends Tree<T> {
    public MyTree(T data) {
        this.setData(data);
    }
    public MyTree() {

    }
    @Override
    boolean delete(T data) {
        return false;
    }

    @Override
    void clear() {

    }

    @Override
    boolean insert(T data) {
        return false;
    }
}
