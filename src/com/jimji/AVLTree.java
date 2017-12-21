package com.jimji;

import java.util.List;
import java.util.Stack;

public class AVLTree<T extends Comparable> extends Tree<T> implements Cloneable {
    public int bln = SAME_HIGH;
    public static final int LEFT_HIGH = 1;
    public static final int RIGHT_HIGH = -1;
    public static final int SAME_HIGH = 0;
    static Stack<AVLTree> stack = new Stack<>();

    public AVLTree() {
    }

    public AVLTree(T data) {
        this.data = data;
    }

    public static <L extends Comparable> AVLTree<L> buildFromList(List<L> ls) {
        //TODO build
        return new AVLTree<>();
    }

    @Override
    boolean delete(T data) {
        _delete(data, new Boolean());
        stack.clear();
        return false;
    }

    boolean _delete(T data, Boolean isShorter) {
        switch (data.compareTo(this.data)) {
            case 0:
                if (stack.isEmpty()) {
                    if (leftChild == null && rightChild == null) {
                        this.data = null;
                        isShorter.value = true;
                    } else if (leftChild == null) {
                        this.leftChild = rightChild.leftChild;
                        this.bln = ((AVLTree) rightChild).bln;
                        this.data = rightChild.data;
                        this.rightChild = rightChild.rightChild;
                    } else if (rightChild == null) {
                        this.rightChild = leftChild.rightChild;
                        this.bln = ((AVLTree) leftChild).bln;
                        this.data = leftChild.data;
                        this.leftChild = leftChild.leftChild;
                    } else {
                        stack.push(this);
                        AVLTree<T> rc = (AVLTree) rightChild;
                        //跑去直接后继找替代
                        while (rc.leftChild != null) {
                            rc = (AVLTree) rc.leftChild;
                        }
                        this.data = rc.data;
                        //问题往下转化
                        ((AVLTree) rightChild)._delete(rc.data, isShorter);
                        checkIfNeedBalance(true, isShorter);
                    }
                } else {
                    AVLTree tree = stack.pop();
                    if (leftChild == null && rightChild == null) {
                        if (tree.rightChild == this) {
                            tree.rightChild = null;
                        } else {
                            tree.leftChild = null;
                        }
                        isShorter.value = true;
                        return true;
                    } else if (leftChild == null) {
                        if (tree.rightChild == this) {
                            tree.rightChild = rightChild;
                        } else {
                            tree.leftChild = rightChild;
                        }
                        isShorter.value = true;
                    } else if (rightChild == null) {
                        if (tree.rightChild == this) {
                            tree.rightChild = leftChild;
                        } else {
                            tree.leftChild = leftChild;
                        }
                        isShorter.value = true;
                    } else {
                        stack.push(this);
                        AVLTree<T> rc = (AVLTree) rightChild;
                        //跑去直接后继找替代
                        while (rc.leftChild != null) {
                            rc = (AVLTree) rc.leftChild;
                        }
                        this.data = rc.data;
                        //问题往下转化
                        ((AVLTree) rightChild)._delete(rc.data, isShorter);
                        checkIfNeedBalance(true, isShorter);
                    }
                }

                break;
            case 1:
                stack.push(this);
                if (rightChild != null) {
                    //右边不空去右边删
                    ((AVLTree) rightChild)._delete(data, isShorter);
                    //删完看看右边短了没
                    checkIfNeedBalance(true, isShorter);
                } else {
                    return false;
                }
            case -1:
                stack.push(this);
                if (leftChild != null) {
                    //右边不空去左边删
                    ((AVLTree) leftChild)._delete(data, isShorter);
                    //删完看看左边短了没
                    checkIfNeedBalance(false, isShorter);
                } else {
                    return false;
                }
        }
        return false;
    }


    @Override
    void clear() {
        bln = 0;
        data = null;
        leftChild = null;
        rightChild = null;
    }

    @Override
    boolean insert(T data) {
        return _insert(data, new Boolean());
    }

    private boolean _insert(T data, Boolean isTaller) {
        switch (data.compareTo(this.data)) {
            //大小相等，即树中存在，不用插入
            case 0:
                return false;
            //带插入数据比该节点小，应插左边
            case -1:
                //左边为空，直接插
                if (leftChild == null) {
                    leftChild = new AVLTree<>(data);
                    isTaller.value = true;
                    //左边不空，插一插试试，插不进就算了
                } else if (!((AVLTree<T>) leftChild)._insert(data, isTaller)) {
                    return false;
                }
                //插进去了，如果变高了，看看要不要调整高度
                if (isTaller.value) {
                    switch (bln) {
                        case SAME_HIGH://原本等高，那就不用调整，改一下平衡就好
                            bln = LEFT_HIGH;
                            break;
                        case LEFT_HIGH://原本左边高,那就调左边，调回左边高1，平衡不用改
                            balanceLeft();//调整左边
                            isTaller.value = false;//调完这棵没长高
                            break;
                        case RIGHT_HIGH://原本右边高，那就不用调，改一下平衡就好
                            bln = SAME_HIGH;
                            isTaller.value = false;//调完这棵没长高
                            break;
                        default:
                            return false;
                    }
                }


                break;
            //带插入数据比该节点大，应插右边
            case 1:
                //右边为空，直接插
                if (rightChild == null) {
                    rightChild = new AVLTree<>(data);
                    isTaller.value = true;
                    //右边不空，插一插试试，插不进就算了
                } else if (!((AVLTree<T>) rightChild)._insert(data, isTaller)) {
                    return false;
                }
                //插进去了，如果变高了，看看要不要调整高度
                if (isTaller.value) {
                    switch (bln) {
                        case SAME_HIGH://原本等高，那就不用调整，改一下平衡就好
                            bln = RIGHT_HIGH;
                            break;
                        case RIGHT_HIGH://原本右边高,那就调右边，调回右边高1，平衡不用改
                            balanceRight();//调整右边
                            isTaller.value = false;//调完这棵没长高
                            break;
                        case LEFT_HIGH://原本左边高，那就不用调，改一下平衡就好
                            bln = SAME_HIGH;
                            isTaller.value = false;//调完这棵没长高
                            break;
                        default:
                            return false;
                    }
                }


                break;
            default:
                return false;
        }
        //插进去了但是没长高，那就ok了
        return true;
    }

    private void rightRotate() {
        try {
            AVLTree rootCopy = (AVLTree) this.clone();
            this.data = leftChild.data;
            this.bln = ((AVLTree) leftChild).bln;
            this.rightChild = leftChild.rightChild;
            this.leftChild = leftChild.leftChild;
            rootCopy.leftChild = this.rightChild;
            this.rightChild = rootCopy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void leftRotate() {
        try {
            AVLTree rootCopy = (AVLTree) this.clone();
            this.data = rightChild.data;
            this.bln = ((AVLTree) rightChild).bln;
            this.leftChild = rightChild.leftChild;
            this.rightChild = rightChild.rightChild;

            rootCopy.rightChild = this.leftChild;
            this.leftChild = rootCopy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }

    private void balanceLeft() {
        AVLTree lc = (AVLTree) leftChild;
        switch (lc.bln) {
            case RIGHT_HIGH://LR,左旋在优选
                AVLTree lc的rc = (AVLTree) lc.rightChild;
                switch (lc的rc.bln) {
                    case SAME_HIGH:
                        bln = lc.bln = SAME_HIGH;
                        break;
                    case LEFT_HIGH:
                        bln = RIGHT_HIGH;
                        lc.bln = SAME_HIGH;
                        break;
                    case RIGHT_HIGH:
                        bln = SAME_HIGH;
                        lc.bln = LEFT_HIGH;
                        break;
                }
                lc的rc.bln = SAME_HIGH;
                ((AVLTree) leftChild).leftRotate();
                rightRotate();
                break;
            case LEFT_HIGH://LL,右旋就搞定
                bln = lc.bln = SAME_HIGH;
                rightRotate();
                break;

            case SAME_HIGH:
                lc.bln = SAME_HIGH;
                bln = LEFT_HIGH;
                rightRotate();
        }
    }

    private void balanceRight() {
        AVLTree rc = (AVLTree) rightChild;
        switch (rc.bln) {
            case LEFT_HIGH://RL,右旋在左旋
                AVLTree rc的lc = (AVLTree) rc.leftChild;
                switch (rc的lc.bln) {
                    case SAME_HIGH:
                        bln = rc.bln = SAME_HIGH;
                        break;
                    case RIGHT_HIGH:
                        bln = LEFT_HIGH;
                        rc.bln = SAME_HIGH;
                        break;
                    case LEFT_HIGH:
                        bln = SAME_HIGH;
                        rc.bln = RIGHT_HIGH;
                        break;
                }
                rc的lc.bln = SAME_HIGH;
                ((AVLTree) rightChild).rightRotate();
                leftRotate();
                break;
            case RIGHT_HIGH://RR,左旋就搞定
                bln = rc.bln = SAME_HIGH;
                leftRotate();
                break;
            case SAME_HIGH:
                rc.bln = SAME_HIGH;
                bln = RIGHT_HIGH;
                leftRotate();

        }
    }

    private AVLTree search(T data) {
        switch (data.compareTo(this.data)) {
            case 0:
                return this;
            case 1:
                if (rightChild != null) {
                    return ((AVLTree) rightChild).search(data);
                } else {
                    return null;
                }
            case -1:
                if (leftChild != null) {
                    return ((AVLTree) leftChild).search(data);
                } else {
                    return null;
                }
        }
        return null;
    }

    private boolean checkIfNeedBalance(boolean isRight, Boolean isShorter) {
        if (!isRight) {
            if (isShorter.value) {
                switch (bln) {
                    case SAME_HIGH:
                        //原本等高那么不影响,书的高度没变
                        bln = RIGHT_HIGH;
                        isShorter.value = false;
                        return true;
                    case RIGHT_HIGH:
                        //原本右边高，现在删完对数的高度没有影响，但是要调整自己的平衡
                        balanceRight();
                        if (((AVLTree) rightChild).bln == SAME_HIGH) {
                            isShorter.value = false;
                        } else {
                            isShorter.value = true;
                        }
                        return true;

                    case LEFT_HIGH:
                        //原本左边高，删完左边矮了，整个树都矮了，但是自己是平衡的
                        bln = SAME_HIGH;
                        isShorter.value = true;
                        break;
                }
            } else {
                //没变短直接返回
                return true;
            }
        } else {
            if (isShorter.value) {
                switch (bln) {
                    case SAME_HIGH:
                        //原本等高那么不影响,书的高度没变
                        bln = LEFT_HIGH;
                        isShorter.value = false;
                        return true;
                    case LEFT_HIGH:
                        //原本左边高，现在删完对数的高度没有影响，但是要调整自己的平衡
                        balanceLeft();
                        if (((AVLTree) leftChild).bln == SAME_HIGH) {
                            isShorter.value = false;
                        } else {
                            isShorter.value = true;
                        }
                        return true;
                    case RIGHT_HIGH:
                        //原本右边高，删完右边矮了，整个树都矮了，但是自己是平衡的
                        bln = SAME_HIGH;
                        isShorter.value = true;
                        break;
                }
            } else {
                //没变短直接返回
                return true;
            }
        }
        return true;
    }

//    private void preOrderVisit_prettyPrint(Visitor visitor, int level, String witch) {
//        visitor.visit(this, level, witch,bln);
//        if (leftChild != null) {
//            ((AVLTree) leftChild).preOrderVisit_prettyPrint(visitor, level + 1, "L");
//        }
//        if (rightChild != null) {
//            ((AVLTree) rightChild).preOrderVisit_prettyPrint(visitor, level + 1, "R");
//        }
//    }
//
//    @Override
//    public void prettyPrint() {
//
//       preOrderVisit_prettyPrint(new Visitor() {
//           @Override
//           void visit(Object... o) {
//               for (int i = 0; i < ((int) o[1]); i++) {
//                   System.out.print("  ");
//               }
//               System.out.println(((Tree) o[0]).data + " bln = "+o[3]+"------" + o[2] + "----");
//           }
//       },0,"");
//    }
}

class Boolean {
    public boolean value;
}
