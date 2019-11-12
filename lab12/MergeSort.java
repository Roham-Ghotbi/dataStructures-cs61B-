import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
        makeSingleItemQueues(Queue<Item> items) {

        Queue<Queue<Item>> seperated = new Queue<>();

        for (Item item : items) {
            Queue<Item> single = new Queue<>();
            single.enqueue(item);
            seperated.enqueue(single);
        }
        return seperated;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {

        int size = q1.size() + q2.size();
        int counter = 0;
        Queue<Item> sortedList = new Queue<>();

        while (counter < size) {
            if (q1.size() < 1) {
                sortedList.enqueue(q2.dequeue());
            } else if (q2.size() < 1) {
                sortedList.enqueue(q1.dequeue());
            } else {

                sortedList.enqueue(getMin(q1, q2));
            }

            counter += 1;
        }
        return sortedList;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {

        if (items.size() < 1) {
            return items;
        }

        Queue<Queue<Item>> singledQueue = makeSingleItemQueues(items);
        Queue first;
        Queue second;

        while (singledQueue.size() > 1) {
            first = singledQueue.dequeue();
            second = singledQueue.dequeue();
            singledQueue.enqueue(mergeSortedQueues(first, second));
        }

        items = singledQueue.dequeue();

        return items;
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Mark");
        students.enqueue("John");

        System.out.println("Initial List");


        for (String name : students) {
            System.out.print(name + " ");
        }
        System.out.println();
        System.out.println("\nSorted List Using MergeSort");

        Queue<String> studentsSorted = mergeSort(students);

        for (String name : studentsSorted) {
            System.out.print(name + " ");
        }
        System.out.println();
        System.out.println("\nInitial List after creating a sorted one!");

        for (String name : students) {
            System.out.print(name + " ");
        }

    }
}
