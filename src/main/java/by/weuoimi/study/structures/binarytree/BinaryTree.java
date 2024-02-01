package by.weuoimi.study.structures.binarytree;

import java.util.function.Consumer;

public class BinaryTree {
    private Node root;

    public BinaryTree() {
        root = new Node(0, null, null);
    }
    public BinaryTree(int key) {
        root = new Node(key, null, null);
    }

    private void traverse(Node node, Consumer<Node> lambda) {
        if (node != null) {
            lambda.accept(node);
            traverse(node.left, lambda);
            traverse(node.right, lambda);
        }
    }

    private record Node(int key, Node left, Node right) {}

    public static void main(String[] args) {
        var bt = new BinaryTree();
        bt.root = new Node(1,
                new Node(2, new Node(4, null, null), new Node(5, null, null)),
                new Node(3, new Node(6, null, null), null));

        bt.traverse(bt.root, node -> {
            System.out.println(node.key + " ");
        });
    }
}
