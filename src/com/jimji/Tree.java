package com.jimji;

import com.sun.istack.internal.Interned;
import com.sun.istack.internal.NotNull;

public abstract class Tree<T> {
    public Tree<T> leftChild;
    public Tree<T> rightChild;
    public T data;

    public Tree<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Tree<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Tree<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Tree<T> rightChild) {
        this.rightChild = rightChild;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    abstract static class Visitor {
        /**
         * 普通打印
         */
        public static Visitor PRINT = new Visitor() {
            @Override
            void visit(Object... o) {
                System.out.print(((Tree) o[0]).data + " ");
            }
        };

        /**
         * 凹入表示法
         */
        public static Visitor PRETTY_PRINT = new Visitor() {
            @Override
            void visit(Object... o) {
                for (int i = 0; i < ((int) o[1]); i++) {
                    System.out.print("  ");
                }
                System.out.println(((Tree) o[0]).data + "------" + o[2] + "----");
            }
        };

        abstract void visit(Object... o);
    }

    abstract boolean delete(T data);

    abstract void clear();

    abstract boolean insert(T data);

    public void preOrderVisit(Visitor visitor) {
        visitor.visit(this);
        if (leftChild != null) {
            leftChild.preOrderVisit(visitor);
        }
        if (rightChild != null) {
            rightChild.preOrderVisit(visitor);
        }
    }

    public void preOrderVisit() {
        Visitor.PRINT.visit(this);
        if (leftChild != null) {
            leftChild.preOrderVisit();
        }
        if (rightChild != null) {
            rightChild.preOrderVisit();
        }
    }

    private void preOrderVisit_prettyPrint(int level, String witch) {
        Visitor.PRETTY_PRINT.visit(this, level, witch);
        if (leftChild != null) {
            leftChild.preOrderVisit_prettyPrint(level + 1, "L");
        }
        if (rightChild != null) {
            rightChild.preOrderVisit_prettyPrint(level + 1, "R");
        }
    }

    /**
     * 凹入表示法打印树
     */
    public void prettyPrint() {
        preOrderVisit_prettyPrint(0, "");
    }

}
