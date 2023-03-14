package Algorithmics.homeworks.homework_4;

public class Temp {
    public static void main(String[] args) {
        Temp temp = new Temp();
//        temp.add(5);
//        temp.add(2);
//        temp.add(1);

        for (int i = 0; i < 10; i++) {
            temp.add(i);
        }

        preOrder(temp.root);
    }

    private Node root;

    public void add(int value) {
        if (root == null) {
            setRoot(value);
            return;
        }

        addNode(root, value);
        root = rebalance(root);
        root.setColor(Color.BLACK);
    }

    private void addNode(Node node, int value) {
        if (node.value > value) {
            if (node.left != null) {
                addNode(node.left, value);
                node.left = rebalance(node.left);
                return;
            }

            node.left = new Node(value);

        } else {
            if (node.right != null) {
                addNode(node.right, value);
                node.right = rebalance(node.right);
                return;
            }

            node.right = new Node(value);
        }
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean isRebalance = true;

        while (isRebalance) {
            boolean isLeftTurn =
                    result.left != null
                            && result.color == Color.RED
                            && result.left.left != null
                            && result.left.left.color == Color.RED;

            boolean isRightTurn =
                    result.right != null
                            && result.right.color == Color.RED
                            && (result.left == null || result.left.color == Color.BLACK);

            boolean isRepaint =
                    result.left != null
                            && result.left.color == Color.RED
                            && result.right != null
                            && result.right.color == Color.RED;

            if (isLeftTurn) {
                result = leftTurn(result);
            }

            if (isRightTurn) {
                result = rightTurn(result);
            }

            if (isRepaint) {
                repaint(result);
            }

            isRebalance = isLeftTurn || isRightTurn || isRepaint;
        }

        return result;
    }

    private Node leftTurn(Node node) {
        Node left = node.left;
        Node right = left.right;

        left.right = node;
        node.left = right;
        left.color = node.color;
        node.color = Color.RED;

        return left;
    }

    private Node rightTurn(Node node) {
        Node right = node.right;
        Node left = right.left;

        right.left = node;
        node.right = left;
        right.color = node.color;
        node.color = Color.RED;

        return right;
    }

    private void repaint(Node node) {
        node.right.color = Color.BLACK;
        node.left.color = Color.BLACK;
        node.color = Color.RED;
    }

    public static void preOrder(Node node) {
        if (node == null) return;

        preOrder(node.left);
        preOrder(node.right);

        System.out.println(node);
    }

    private void setRoot(int value) {
        this.root = new Node(value);
        this.root.color = Color.BLACK;
    }

    private static class Node {
        private final int value;
        private Color color;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
            this.color = Color.RED;
        }

        public void setLeft(Node left) {
            this.left = left;
            this.left.color = Color.RED;
        }

        public void setRight(Node right) {
            this.right = right;
            this.right.color = Color.RED;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return value + ": " + color;
        }
    }

    private enum Color {
        RED,
        BLACK
    }

}
