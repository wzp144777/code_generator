package study.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReduceNode {

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {

        List<Integer> list1 = new ArrayList<>();
        list1.add(9);
        list1.add(8);
        list1.add(7);
        ListNode l1 = createNodeList(list1);

        List<Integer> list2 = new ArrayList<>();
        list2.add(5);
        list2.add(1);
        list2.add(2);
        ListNode l2 = createNodeList(list2);

        ListNode listNode = reduceTwoNumbers(l1, l2).next;

        while (listNode != null) {
            if (listNode.next != null) {
                System.out.print(listNode.val + " ");
            } else {
                System.out.print(listNode.val);
            }
            listNode = listNode.next;
        }
    }

    public static ListNode createNodeList (List<Integer> nodeText) {
        ListNode listNode = new ListNode();
        ListNode head1 = listNode;
        if(nodeText == null && nodeText.size() == 0) {
            System.err.println("链表节点为空");
            throw new RuntimeException();
        }
        Iterator<Integer> iterable = nodeText.iterator();
        while (iterable.hasNext()) {

            int value = iterable.next();
            if(value > 10) {
                System.err.println("数值异常 数值应小于10");
                throw new RuntimeException();
            }
            ListNode next = new ListNode(value);
            // 从头节点往后插入新的节点
            head1.next = next;
            head1 = next;
        }
        return listNode;
    }

    public static ListNode reduceTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode cursor = root;
        while (l1 != null || l2 != null ) {
            int l1val = l1 != null ? l1.val : 0;
            int l2val = l2 != null ? l2.val : 0;
            if (l1val < l2val) {
                System.err.println("被减数大于减数");
                throw new RuntimeException();
            }
            int reduceVal = l1val - l2val;
            ListNode reduceNode = new ListNode(reduceVal);
            cursor.next = reduceNode;
            cursor = cursor.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return root.next;
    }
}
