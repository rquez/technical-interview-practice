import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GreedyAlgorithms {

    public static void sort(int[] input) {

        // Exit when there's < 2 element
        if (input.length < 2)
            return;

        int mid = input.length / 2;

        // Parition the into left and right arrays
        int[] left = Arrays.copyOfRange(input,0, mid);
        int[] right = Arrays.copyOfRange(input, mid, input.length);

        // Sort the left
        sort(left);

        // Sort the right
        sort(right);

        // Merge the left and right
        merge(input, left, right);
    }

    private static void merge(int[] array, int[] left, int[] right) {

        /*
        i - counter for left
        j - counter for right
        k - counter for the array
         */
        int i = 0, j = 0, k = 0;

        // Loop exits when the counters reach their respective array lengths
        while (i < left.length && j < right.length) {

            if (left[i] <= right[j]) { // Choose the first smallest element
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        /*
        The elements from left and right that HAVEN'T been added to array
        have their respective counters less than their respective lengths,
        so append the end of the array with these elements. These elements
        have to be greater the existing elements.
         */
        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < left.length) {
            array[k++] = right[j++];
        }
    }

    public static void sortOnEndTime(int[][] jobs) {

        if (jobs.length < 2)
            return;

        int mid = jobs.length / 2;

        int[][] left = Arrays.copyOfRange(jobs, 0, mid);
        int[][] right = Arrays.copyOfRange(jobs, mid, jobs.length);

        sortOnEndTime(left);
        sortOnEndTime(right);
        merge(jobs, left, right);
    }

    private static void merge(int[][] array, int[][] left, int[][] right) {

        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {

            if (left[i][1] <= right[j][1]) { // Choose the first smallest element
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    private static void sortEdges(Edge[] edges) {

        if (edges.length < 2)
            return;

        int mid = edges.length / 2;

        Edge[] left = Arrays.copyOfRange(edges, 0, mid);
        Edge[] right = Arrays.copyOfRange(edges, mid, edges.length);

        sortEdges(left);
        sortEdges(right);
        merge(edges, left, right);

    }

    private static void merge(Edge[] edges, Edge[] left, Edge[] right) {

        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {

            if (left[i].compareTo(right[j]) < 0) { // Choose the first smallest element
                edges[k++] = left[i++];
            } else {
                edges[k++] = right[j++];
            }
        }

        /*
        The elements from left and right that HAVEN'T been added to array
        have their respective counters less than their respective lengths,
        so append the end of the array with these elements. These elements
        have to be greater the existing elements.
         */
        while (i < left.length) {
            edges[k++] = left[i++];
        }

        while (j < left.length) {
            edges[k++] = right[j++];
        }
    }

    public static void scheduler() {

        Scanner scanner = new Scanner(System.in);

        int numJobs = scanner.nextInt();
        int[][] jobs = new int[numJobs][2];
        for (int i = 0; i < numJobs; i++) {
            jobs[i][0] = scanner.nextInt();
            jobs[i][1] = scanner.nextInt();
        }

        sortOnEndTime(jobs);

        int[][] bestSchedule = new int[jobs.length][2];
        int currentJob = 0;
        int j = 0;
        int count = 0;
        bestSchedule[j++] = jobs[0];
        for (int i = 1; i < jobs.length; i++) {

            // If current job ends before or at the next job
            if (jobs[currentJob][1] <= jobs[i][0]) {
                bestSchedule[j++] = jobs[i];
                currentJob = i;
                count++;
            }
        }

        for (int i = 0; i <= count; i++) {
            System.out.println(bestSchedule[i][0] + " " + bestSchedule[i][1]);
        }
    }

    public static void kruskalMST(List<Edge> edges, int vertices) {

        DisjointSet disjointSet = new DisjointSet(vertices);
        List<Edge> mst = new ArrayList<>();
        Collections.sort(edges);

        for (int i = 0; i < edges.size() && mst.size() < vertices - 1; i++) {

            Edge e = edges.get(i);
            if (disjointSet.find(e.from) != disjointSet.find(e.to)) {
                mst.add(e);
                disjointSet.union(e.from, e.to);
            }
        }

        for (Edge edge : mst) {
            System.out.println(edge.toString());
        }
    }

    public static class GreedyAlgorithmsTest {

        @Test
        public void test_sort() {
            int[] array = new int[]{3,7,5,4,2};
            GreedyAlgorithms.sort(array);
            assertThat(array, is(new int[]{2,3,4,5,7}));
        }

        @Test
        public void test_sortOnEndTime() {
            int[][] array = new int[][]{ {0,40}, {30,60}, {20,30}, {60,80}, {10,50}, {30,40}, {40,60}, {50,100}, {50, 70}, {50, 60}};
            GreedyAlgorithms.sortOnEndTime(array);
            int[][] sorted = new int[][]{ {20,30}, {0,40}, {30,40}, {10,50}, {40,60}, {30,60}, {50,60}, {50,70}, {60,80}, {50,100}};
            assertThat(array, is(array));
        }

        @Test
        public void test_scheduler() {

            String input = "10\n0 40\n30 60\n20 30\n60 80\n10 50\n30 40\n40 60\n50 100\n50 70\n50 60\n";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            GreedyAlgorithms.scheduler();
            String[] print = out.toString().split("\n");
            assertThat(print.length, is(4));
        }

        @Test
        public void test_kruskalMST() {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            List<Edge> edgeList = new ArrayList();

            edgeList.add(new Edge(0, 1, 1));
            edgeList.add(new Edge(0, 2, 3));

            edgeList.add(new Edge(1, 2, 4));
            edgeList.add(new Edge(1, 4, 5));

            edgeList.add(new Edge(2, 3, 2));

            edgeList.add(new Edge(3, 1, 3));
            edgeList.add(new Edge(3, 4, 3));

            edgeList.add(new Edge(4, 5, 7));

            edgeList.add(new Edge(5, 6, 9));
            GreedyAlgorithms.kruskalMST(edgeList, 9);

            String[] print = out.toString().split("\n");
            assertThat(print.length, is(6));

        }
    }
}

