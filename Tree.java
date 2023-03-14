package Algorithmics.homeworks.homework_4;

public class Tree {
    public static void main(String[] args) {
        Tree tree = new Tree(5);
        tree.add(4);
        tree.add(1);
        tree.add(0);

        Node.postOrder(tree.root);
    }

    Node root;

    public Tree(int value) {
        this.root = new Node(value);
        root.color = Color.BLACK;
    }

    private Node find(int value) {
        if (root == null) return null;

        Node node = root;
        while (true) {
            if (value < node.value) {
                if (node.left == null) {
                    return node;
                }
                node = node.left;

            } else {
                if (node.right == null) {
                    return node;
                }
                node = node.right;
            }
        }
    }

    private void add(int value) {
        Node father = find(value);
        if (father == null) {
            root = new Node(value, Color.BLACK);
            return;
        } else if (value < father.value) {
            father.setLeft(new Node(value, Color.RED));
        } else {
            father.setRight(new Node(value, Color.RED));
        }

        father.balance();
    }

    private class Node {
        private final int value;
        private Color color;
        private Node left;
        private Node right;
        private Node parent;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Color color) {
            this.value = value;
            this.color = color;
        }

        public void setLeft(Node left) {
            this.left = left;
            if (left != null) left.parent = this;
        }

        public void setRight(Node right) {
            this.right = right;
            if (right != null) right.parent = this;
        }

        public void setRoot() {
            root = this;
            this.parent = null;
            root.color = Color.BLACK;
        }

        private void balance() {
            if (this.parent == null) {
                root = this;
                return;
            }

            boolean isLeftTurn =
                            this.right != null &&
                            this.left != null &&
                            this.right.color == Color.RED &&
                            this.left.color == Color.BLACK;

            boolean isRightTurn =
                            this == this.parent.left &&
                            this.color == Color.RED &&
                            this.left != null &&
                            this.left.color == Color.RED;

            boolean isRepaint =
                            this.left != null &&
                            this.right != null &&
                            this.left.color == Color.RED &&
                            this.right.color == Color.RED;

            if (isLeftTurn) {
                this.rightTurn();
            }

            if (isRightTurn) {
                this.leftTurn();
            }

            if (isRepaint) {
                this.color = Color.RED;
                this.left.color = Color.BLACK;
                this.right.color = Color.BLACK;
            }

            root.color = Color.BLACK;
        }

        private void leftTurn() {
            Node father = this.parent;
            Node grandfather = father.parent;

            if (grandfather == null) {
                this.setRoot();
            } else {
                grandfather.setRight(this);
            }

            father.setLeft(this.right);
            this.setRight(father);
        }

        private void rightTurn() {
            Node father = this.parent;
            Node grandfather = father.parent;

            if (grandfather == null) {
                this.setRoot();
            } else {
                grandfather.setLeft(this);
            }

            father.setRight(this.left);
            this.setLeft(father);
        }

        public static void postOrder(Node node) {
            if (node == null) return;

            postOrder(node.left);
            postOrder(node.right);

            System.out.println(node);
        }

        @Override
        public String toString() {
            return value + ": " + color + " >> " + parent;
        }
    }

    private enum Color {
        RED,
        BLACK
    }
}
