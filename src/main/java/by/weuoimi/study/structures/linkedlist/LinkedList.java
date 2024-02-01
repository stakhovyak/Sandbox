package by.weuoimi.study.structures.linkedlist;

/* TODO pushHead, pushTail, add() - macro for insert, set, get.
 */

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class LinkedList {
    private Node head;
    private Node tail;

    /**
     * variable that caches length() method's result with every
     * insertion, pushHead and pushTail
     */
    private int length;

    /**
     * the method simply traverses through all list elements and returns
     * its length.
     *
     * @return length of the list.
     */
    private int length() { // Found no usages for this so far, may be useful to ensure actual length
        var atom = new AtomicInteger();
        traverseForward(node ->
                atom.incrementAndGet()
        );
        return atom.get();
    }

    public void pushTail(int value) {
        if (length == 0) {
            tail = new Node(value, null, null);
            head = tail;
        } else {
            var newNode = new Node(value, null, tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        length++;
    }

    public void pushHead(int value) {
        if (length == 0) {
            head = new Node(value, null, null);
            tail = head;
        } else {
            var newNode = new Node(value, head, null);
            head.setPrev(newNode);
            head = newNode;
        }
        length++;
    }

    public void add(int value, int position) {
        if (position == 0)
            pushHead(value);
        else if (position == length)
            pushTail(value);
        else {
            int i = 0;
            var current = head;
            while (i <= position - 1) {
                current = current.next;
                i++;
            }
            var newNode = new Node(value, current, current.prev);
            current.setPrev(newNode);
            current = current.prev.prev;
            current.setNext(newNode);
        }
        length++;
    }

    public void set(int value, int position) {
        try {
            Objects.requireNonNull(getNode(position)).value = value;
        } catch (NullPointerException e) {
            // TODO handle this
        }
    }

    public int get(int position) {
        try {
            return Objects.requireNonNull(getNode(position)).value;
        } catch (IndexOutOfBoundsException e) {
            // TODO handle this better
            return -1;
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("[");
        traverseForward(node ->
            sb.append(node.value).append(", ")
        );
        sb.append("Length: ").append(length).append("]");
        return sb.toString();
    }

    private Node getNode(int position) {
        try {
            if (position == 0)
                return head;
            else if (position == length - 1)
                return tail;
            else {
                int i = 0;
                var current = head;
                while (i <= position - 1) {
                    current = current.next;
                    i++;
                }
                return current;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void traverseForward(Consumer<Node> lambda) {
        var current = head;
        while (current != null) {
            lambda.accept(current);
            current = current.next;
        }
    }

    /**
     * The node of the list.
     */
    private static final class Node {
        private int value;
        private Node next;
        private Node prev;

        /**
         * @param value node's value.
         * @param next  link to element next to current node.
         * @param prev  link to element previous to current node.
         */
        private Node(int value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        private void setNext(Node node) {
            next = node;
        }

        private void setPrev(Node node) {
            prev = node;
        }

        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Node) obj;
            return this.value == that.value &&
                    Objects.equals(this.next, that.next) &&
                    Objects.equals(this.prev, that.prev);
        }
    }

    public static void main(String[] args) {
        var ll = new LinkedList();
        ll.pushHead(1);
        ll.pushTail(2);
        ll.pushTail(3);
        ll.pushTail(6);
        ll.pushHead(0);
        ll.pushHead(10);
        ll.add(11, 2);
        System.out.println(ll);
        System.out.println("ll.get(2) == " + ll.get(2));
        System.out.println("ll.get(2) == " + ll.get(0));
        System.out.println("ll.get(2) == " + ll.get(6));
        System.out.println(ll);
        ll.set(7, 0);
        ll.set(8, 3);
        ll.set(9, 6);
        System.out.println(ll);
    }
}

