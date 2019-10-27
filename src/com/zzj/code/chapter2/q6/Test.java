package com.zzj.code.chapter2.q6;



public class Test {
    /**
     *
     * @param preOrder 前序遍历数组
     * @param inOrder 中序遍历数组
     * @param length 数组长度
     * @return 根结点
     */
    public static BinaryTreeNode Construct(int[] preOrder, int[] inOrder,int length){
        if (preOrder == null || inOrder == null || length <= 0) {
            return null;
        }
        try {
            return ConstructCore(preOrder, 0, preOrder.length - 1, inOrder, 0,inOrder.length - 1);
        } catch (InvalidPutException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BinaryTreeNode ConstructCore(int[] preOrder,int startPreIndex, int endPreIndex,
                                               int[] inOrder ,int startInIndex , int endInIndex) throws InvalidPutException {
        int rootValue = preOrder[startPreIndex];

        BinaryTreeNode root = new BinaryTreeNode(rootValue);
        // 只有一个元素
        if (startPreIndex == endPreIndex) {
            if (startInIndex == endInIndex
                    && preOrder[startPreIndex] == inOrder[startInIndex]) {
                System.out.println("only one element");
                return root;
            } else {
                throw new InvalidPutException();
            }
        }
        // 在中序遍历中找到根结点的索引
        int rootInIndex = startInIndex;
        while (rootInIndex <= endInIndex && inOrder[rootInIndex] != rootValue) {
            ++rootInIndex;
        }
        if (rootInIndex == endInIndex && inOrder[rootInIndex] != rootValue) {
            throw new InvalidPutException();
        }
        // 中序节点中 属于子树 的长度
        int leftLength = rootInIndex - startInIndex;

        // 左子树前序中结束的下标
        int leftPreOrderEndIndex = startPreIndex + leftLength;

        if (leftLength > 0) {
            // 构建左子树
            root.leftNode = ConstructCore(preOrder, startPreIndex + 1,
                    leftPreOrderEndIndex, inOrder, startInIndex,
                    rootInIndex - 1);
        }

        if (leftLength < endPreIndex - startPreIndex) {
            // 右子树有元素,构建右子树
            root.rightNode = ConstructCore(preOrder, leftPreOrderEndIndex + 1,
                    endPreIndex, inOrder, rootInIndex + 1, endInIndex);
        }
        return root;
    }
    static class InvalidPutException extends Exception {
        private static final long serialVersionUID = 1L;
    }
    public static void printPreOrder(BinaryTreeNode root) {
        if (root == null) {
            return;
        } else {
            System.out.print(root.value + " ");
        }
        if (root.leftNode != null) {
            printPreOrder(root.leftNode);
        }
        if (root.rightNode != null) {
            printPreOrder(root.rightNode);
        }
    }
    public static void main(String[] args) throws Exception{
        BinaryTreeNode test=new BinaryTreeNode();
        int[] preOrder={1,2,4,7,3,5,6,8};
        int[] inOrder={4,7,2,1,5,3,8,6};
        printPreOrder(Construct(preOrder, inOrder, preOrder.length));
    }
}