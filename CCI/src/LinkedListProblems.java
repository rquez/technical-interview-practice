import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class LinkedListProblems {

    /*
    Write code to remove duplicates from an unsorted linked list. Solve this problem without using a temporary buffer.
     */
    // O(n^2) time, O(1) space - Solution uses a temporary buffer
    public static Node removeDuplicateNodes(Node head) {
        Node helperFromHead = head;
        while (helperFromHead != null) {
            Node helper = helperFromHead.getNext();
            Node prev = helperFromHead;
            while (helper != null) {
                if (helperFromHead.getData() == helper.getData()) {
                    prev.setNext(helper.getNext());
                    helper.setNext(null);
                    helper = prev.getNext();
                } else {
                    prev = helper;
                    helper = helper.getNext();
                }
            }
            helperFromHead = helperFromHead.getNext();
        }
        return head;
    }
    // O(n) time, O(n) space
    public static Node removeDuplicateNodesHashTable(Node head) {
        HashSet<Integer> hashtable = new HashSet<>();
        hashtable.add(head.getData());
        Node helper = head.getNext();
        Node prev = head;
        while (helper != null) {
            if (hashtable.contains(helper.getData())) {
                prev.setNext(helper.getNext());
                helper.setNext(null);
                helper = prev.getNext();
            } else {
                hashtable.add(helper.getData());
                prev = helper;
                helper = helper.getNext();
            }
        }
        return head;
    }
    // Same as above only recursive
    public static Node removeDuplicateNodesHashTableRecursive(Node head, Node prev, Node current, HashSet<Integer> table) {
        if (current == null) {
            return head;
        }
        if (table.contains(current.getData())) {
            prev.setNext(current.getNext());
            current = prev;
        } else {
            if (head == prev)
                table.add(head.getData());
            table.add(current.getData());
        }
        return removeDuplicateNodesHashTableRecursive(head, current, current.getNext(), table);
    }

    /*
    Implement an algorithm to find the kth to last element of a singly linked list.
     */
    // O(2n - k) time, O(1) space
    public static Node kthFromLast(Node head, int k) {
        Node helper = head;
        int i = 0;
        while (helper != null) {
            helper = helper.getNext();
            i++;
        }
        int index = i - k - 1;
        int j = 0;
        while (j != index) {
            head = head.getNext();
            j++;
        }

        return head;
    }
    // O(1) time, O(n) space -- Doesn't return Node
    public static int kthFromLastPrint(Node head, int k) {
        if (head == null) {
            return 0;
        }
        int index = kthFromLastPrint(head.getNext(), k) + 1;
        if (index == k) {
            System.out.println(head.getData());
        }
        return index;
    }
    // O(n) time, O(1) space
    public static Node kthFromLastFaster(Node head, int k) {

        Node p1 = head;
        Node p2 = head;

        for (int i = 0; i < k; i++) {
            p2 = p2.getNext();
        }
        while (p2.getNext() != null) {
            p1 = p1.getNext();
            p2 = p2.getNext();
        }
        return p1;
    }

    /*
       Write code to partition a linked list around a value x, such that all nodes less than x come
       before all nodes greater than or equal to x.
     */
    // O(n) time, O(1) space
    public static Node paritionListOnX(Node head, int x) {
        Node LList = null;
        Node RList = null;
        Node LListHead = null;
        Node RListHead = null;

        while (head != null) {
            Node next = head.getNext();
            head.setNext(null);
            if (head.getData() < x) {
                if (LList != null) {
                    LList.setNext(head);
                    LList = LList.getNext();
                } else {
                    LList = head;
                    LListHead = head;
                }
            } else {
                if (RList != null) {
                    RList.setNext(head);
                    RList = RList.getNext();
                } else {
                    RList = head;
                    RListHead = head;
                }
            }
            head = next;
        }
        if (LList != null) {
            LList.setNext(RListHead);
            return LListHead;
        } else {
            return RListHead;
        }
    }

    /*
    You have two numbers represented by a linked list, where each node contains a single digit.
    The digits are stored in reverse order, such that the 1's digit is at the head of the list.
    Write a function that adds the two numbers and returns the sum as a linked list.
     */
    // O(n) time, O(n + 1) space
    public static Node sumListsReverse(Node aList, Node bList) {

        Node sum;
        Node prev = null;
        Node sumHead = null;
        int r = 0;
        boolean first = true;
        while( aList != null || bList != null) {
            int a = 0, b = 0;

            if (aList != null)
                a = aList.getData();
            if (bList != null)
                b = bList.getData();

            int i = a + b + r;
            sum = new Node(i % 10);
            r = i / 10;

            if (first)
                sumHead = sum;
            else
                prev.setNext(sum);
            prev = sum;
            sum = sum.getNext();
            first = false;

            if (aList != null)
                aList = aList.getNext();
            if (bList != null)
                bList = bList.getNext();
        }

        if (r == 1) {
            sum = new Node(r);
            prev.setNext(sum);
        }

        return sumHead;
    }

    public static Node sumListsReverseRecursive(Node aList, Node bList, int r) {
        if (aList == null && bList == null) {
            if (r == 1)
                return new Node(r);
            else
                return null;
        }

        int a = 0, b = 0;
        Node aNext = null, bNext = null;
        if (aList != null) {
            a = aList.getData();
            aNext = aList.getNext();
        }
        if (bList != null) {
            b = bList.getData();
            bNext = bList.getNext();
        }

        int i = a + b + r;
        Node node = new Node(i % 10);

        node.setNext(sumListsReverseRecursive(aNext, bNext, i / 10));
        return node;
    }


    public static Node sumListsForwardRecursive(Node aList, Node bList) {
        if (aList.getNext() == null && bList.getNext() == null) {
            return new Node(aList.getData() + bList.getData());
        }

        Node aNext = aList, bNext = bList;
        int a = 0, b = 0;
        if (aList.getNext() != null) {
            aNext = aList.getNext();
            a = aList.getData();
        }
        if (bList.getNext() != null) {
            bNext = bList.getNext();
            b = bList.getData();
        }

        Node node = new Node(0);
        node.setNext(sumListsForwardRecursive(aNext, bNext));

        int prevSum = 0;
        if (node.getNext() != null) {
            prevSum = node.getNext().getData();
            if (prevSum >= 10) {
                node.getNext().setData(prevSum % 10);
            }
        }

        int i = a + b + (prevSum / 10);
        node.setData(i);

        return node;
    }
    public static class UnitTest {
        Node head;

        @Before
        public void setup() {
            head = new Node(10);
            Node a = new Node(11);
            Node b = new Node(12);
            Node c = new Node(13);
            Node d = new Node(10);
            Node e = new Node(23);
            Node f = new Node(11);
            Node g = new Node(23);
            head.setNext(a);
            a.setNext(b);
            b.setNext(c);
            c.setNext(d);
            d.setNext(e);
            e.setNext(f);
            f.setNext(g);
        }

        @Test
        public void testRemoveDuplicateNodes() {
            Node noDupHead = new Node(10);
            Node a = new Node(11);
            Node b = new Node(12);
            Node c = new Node(13);
            Node e = new Node(23);
            noDupHead.setNext(a);
            a.setNext(b);
            b.setNext(c);
            c.setNext(e);
            Node test = removeDuplicateNodes(head);
            assertTrue(Node.isEqual(noDupHead, test));

            setup();
            test = removeDuplicateNodesHashTable(head);
            assertTrue(Node.isEqual(noDupHead, test));

            setup();
            test = removeDuplicateNodesHashTableRecursive(head, head, head.getNext(), new HashSet<Integer>());
            assertTrue(Node.isEqual(noDupHead, test));
        }

        @Test
        public void testKthFromLast() {

            Node test = new Node(10);
            Node a = new Node(11);
            Node b = new Node(12);
            Node c = new Node(13);
            Node d = new Node(14);
            test.setNext(a);
            a.setNext(b);
            b.setNext(c);
            c.setNext(d);

            assertTrue(Node.isEqual(d, kthFromLast(test, 0)));
            assertTrue(Node.isEqual(c, kthFromLast(test, 1)));
            assertTrue(Node.isEqual(b, kthFromLast(test, 2)));
            assertTrue(Node.isEqual(a, kthFromLast(test, 3)));
            assertTrue(Node.isEqual(test, kthFromLast(test, 4)));
            assertTrue(Node.isEqual(d, kthFromLastFaster(test, 0)));
            assertTrue(Node.isEqual(c, kthFromLastFaster(test, 1)));
            assertTrue(Node.isEqual(b, kthFromLastFaster(test, 2)));
            assertTrue(Node.isEqual(a, kthFromLastFaster(test, 3)));
            assertTrue(Node.isEqual(test, kthFromLastFaster(test, 4)));
        }

        @Test
        public void testParitionOnX() {

            head = new Node(5);
            Node a = new Node(7);
            Node b = new Node(3);
            Node c = new Node(2);
            Node d = new Node(1);
            Node e = new Node(11);
            Node f = new Node(8);
            Node g = new Node(9);
            head.setNext(a);
            a.setNext(b);
            b.setNext(c);
            c.setNext(d);
            d.setNext(e);
            e.setNext(f);
            f.setNext(g);
            Node test = new Node(5);
            Node at = new Node(3);
            Node bt = new Node(2);
            Node ct = new Node(1);
            Node dt = new Node(7);
            Node et = new Node(11);
            Node ft = new Node(8);
            Node gt = new Node(9);
            test.setNext(at);
            at.setNext(bt);
            bt.setNext(ct);
            ct.setNext(dt);
            dt.setNext(et);
            et.setNext(ft);
            ft.setNext(gt);

            assertTrue(Node.isEqual(test, paritionListOnX(head, 6)));

            head = new Node(1);
            a = new Node(1);
            b = new Node(1);
            c = new Node(1);
            d = new Node(1);
            e = new Node(1);
            f = new Node(8);
            g = new Node(1);
            head.setNext(a);
            a.setNext(b);
            b.setNext(c);
            c.setNext(d);
            d.setNext(e);
            e.setNext(f);
            f.setNext(g);
            test = new Node(1);
            at = new Node(1);
            bt = new Node(1);
            ct = new Node(1);
            dt = new Node(1);
            et = new Node(1);
            ft = new Node(1);
            gt = new Node(8);
            test.setNext(at);
            at.setNext(bt);
            bt.setNext(ct);
            ct.setNext(dt);
            dt.setNext(et);
            et.setNext(ft);
            ft.setNext(gt);

            assertTrue(Node.isEqual(test, paritionListOnX(head, 6)));

            head = new Node(1);
            a = new Node(1);
            b = new Node(1);
            c = new Node(1);
            d = new Node(1);
            e = new Node(1);
            f = new Node(1);
            g = new Node(1);
            head.setNext(a);
            a.setNext(b);
            b.setNext(c);
            c.setNext(d);
            d.setNext(e);
            e.setNext(f);
            f.setNext(g);
            test = new Node(1);
            at = new Node(1);
            bt = new Node(1);
            ct = new Node(1);
            dt = new Node(1);
            et = new Node(1);
            ft = new Node(1);
            gt = new Node(1);
            test.setNext(at);
            at.setNext(bt);
            bt.setNext(ct);
            ct.setNext(dt);
            dt.setNext(et);
            et.setNext(ft);
            ft.setNext(gt);

            assertTrue(Node.isEqual(test, paritionListOnX(head, 6)));
        }

        @Test
        public void testSumListReverse() {
            Node aList= new Node(7);
            Node a = new Node(8);
            Node b = new Node(5);
            aList.setNext(a);
            a.setNext(b);

            Node bList = new Node(2);
            Node c = new Node(2);
            Node d = new Node(9);
            bList.setNext(c);
            c.setNext(d);

            Node sum = new Node(9);
            Node at = new Node(0);
            Node bt = new Node(5);
            Node ct = new Node(1);
            sum.setNext(at);
            at.setNext(bt);
            bt.setNext(ct);

            assertTrue(Node.isEqual(sum, sumListsReverseRecursive(aList, bList, 0)));
            assertTrue(Node.isEqual(sum, sumListsReverse(aList, bList)));

            aList= new Node(1);

            bList = new Node(2);
            c = new Node(2);
            d = new Node(9);
            bList.setNext(c);
            c.setNext(d);

            sum = new Node(3);
            at = new Node(2);
            bt = new Node(9);
            sum.setNext(at);
            at.setNext(bt);
            bt.setNext(null);

            assertTrue(Node.isEqual(sum, sumListsReverseRecursive(aList, bList, 0)));
            assertTrue(Node.isEqual(sum, sumListsReverse(aList, bList)));

            aList= new Node(8);
            aList.setNext(null);

            bList = new Node(9);
            c = new Node(2);
            d = new Node(2);
            bList.setNext(c);
            c.setNext(d);
            d.setNext(null);

            sum = new Node(9);
            at = new Node(3);
            bt = new Node(0);
            sum.setNext(at);
            at.setNext(bt);
            bt.setNext(null);

            assertTrue(Node.isEqual(sum, sumListsForwardRecursive(aList, bList)));
        }


    }
}
