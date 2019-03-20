package dataStructureAndAlgorithm.dataStructure;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 队列学习
 *
 * @author bin.yu
 * @create 2019-03-18 19:30
 **/
public class QueueDemo {

    public static void main(String[] args) {
        dequeDemo();
    }


    /**
     * PriorityQueue保存队列元素的顺序并不是按照加入队列的顺序，而是按照队列元素的大小进行重新排序。
     * peek()方法取出队列头部元素时候，不删除该元素，而poll()方法取出元素时会删除元素。如果遇到队列为空的情况是，两者都会返回null
     * add() 等同于 offer()
     * PriorityQueue不允许插入null元素，还要对队列元素进行排序，两种排序方式：
     * 自然排序：集合中的元素必须实现Comparable接口，而且应该是同一个类的多个实例，否则可能导致ClassCastException异常。
     * 定制排序：创建队列时，传入一个Comparator对象，该对象负责对队列中的所有元素进行排序。采用定制排序时不需要元素实现Comparable接口。
     */
    private static void priorityQueueDemo(){
        PriorityQueue<Object> queues = new PriorityQueue<>();
        queues.add(5);
        queues.add(4);
        queues.add(8);
        queues.offer(-2);
        queues.offer(-10);
        System.out.println(queues);
        queues.peek();
        System.out.println(queues);
        queues.poll();
        System.out.println(queues);

        while (!queues.isEmpty()) {
            System.out.println(queues.poll());
        }
    }


    /**
     * 往后增加元素 add() == addLast() == offer() == offerLast()
     * 往前增加元素 push() == addFirst() == offerFirst()
     * 获取最后一个元素不删除 getLast() == peekLast()
     * 获取最后一个元素删除    pollLast() == removeLast()
     * 获取头部元素不删除 peek() == element() == getFirst()
     * 获取头部元素删除  poll() == pop() == pollFirst() == removeFirst()
     * Deque接口是Queue接口的子接口，代表一个双端队列。同时Deque不仅可以作为双端队列使用，而且可以被当成栈来使用
     *
     */
    private static void dequeDemo(){
        ArrayDeque stack = new ArrayDeque();
        stack.push(2);
        stack.push(5);
        stack.push(7);
        stack.add(12);
        stack.addLast(1);
        stack.offer(11);
        System.out.println(stack);

        System.out.println(stack.removeLast());
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);
        System.out.println(stack.poll());
        System.out.println(stack);

        LinkedList linkedList = new LinkedList();
        linkedList.add(5);
        linkedList.add(1);
        System.out.println(linkedList);


    }
}
