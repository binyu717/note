package dataStructureAndAlgorithm;

/**
 * 二叉树
 *
 * @author bin.yu
 * @create 2018-09-01 8:07
 **/
public class TreeNodeDemo {
    static class TreeNode{
        int val;

        TreeNode left;

        TreeNode right;
    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.left = new TreeNode();
        root.left.val = 2;
//        root.left.left.val = 3;
        System.out.println(maxDeath(root));

    }

//    1. 求二叉树的最大深度
    private static int maxDeath(TreeNode node){
        if(node==null){
            return 0;
        }
        int left = maxDeath(node.left);
        int right = maxDeath(node.right);
        return Math.max(left,right) + 1;
    }
}
