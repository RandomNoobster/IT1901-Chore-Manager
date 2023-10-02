package ui;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.Node;

/**
 * A class with a bunch of methods for traversing the UI
 */
public class TestUtilities {

    /**
    * Finds the nth node that matches the given predicate.
    *
    * @param nodePredicate The predicate to test the node with.
    * @param nthVisited Which node to return, starting from 1 (example nthVisited=2 returns the second visited node)
    * @return The nth node that matches the predicate, or null if no such node is found.
    */
    private static Node findNode(Predicate<Node> nodePredicate, int nthVisited) {
        int count = 0;
        FxRobot robot = new FxRobot();
        for (Node node : robot.lookup(nodePredicate).queryAll()) {
            if (nodePredicate.test(node) && count++ == nthVisited) {
                return node;
            }
        }
        return null;
    }

    /**
    * Finds all nodes that match the given predicate.
    *
    * @param nodePredicate The predicate to test the nodes with.
    * @return A list of all nodes that match the predicate.
    */
    private static List<Node> findAllNodes(Predicate<Node> nodePredicate) {
        List<Node> nodes = new ArrayList<>();
        FxRobot robot = new FxRobot();
        for (Node node : robot.lookup(nodePredicate).queryAll()) {
            if (nodePredicate.test(node)) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    /**
     * Waits for a node that matches the given predicate and is the nth node visited.
     * 
     * @param nodePredicate The predicate to test the node with.
     * @param nthVisited Which node to return, starting from 1 (example nthVisited=2 returns the second visited node)
     * @return The node that matches the predicate and is the nth node visited
     */
    public static Node waitForNode(Predicate<Node> nodePredicate, int nthVisited) {
        WaitForAsyncUtils.waitForFxEvents();
        Node[] nodes = new Node[1];
        try {
            WaitForAsyncUtils.waitFor(2000, TimeUnit.MILLISECONDS,
                    () -> {
                        while (true) {
                            if ((nodes[0] = findNode(nodePredicate, nthVisited)) != null) {
                                return true;
                            }
                            Thread.sleep(100);
                        }
                    });
        } catch (TimeoutException e) {
            fail("No appropriate node available");
        }
        return nodes[0];
    }

    /**
     * Example usage, this gets all labels with the text "Reminder": 
     * {@code List<Node> nodesWithRemainderLabel = TestUtilities.waitForAllNodes((node -> node instanceof Label && ((Label) node).getText().equals("Reminder")));}
     * @param nodePredicate The predicate to test the node with. It will return the node if the predicate returns true.
     * @return A list of nodes that match the predicate
     */
    public static List<Node> waitForAllNodes(Predicate<Node> nodePredicate) {
        WaitForAsyncUtils.waitForFxEvents();
        List<Node> nodes = new ArrayList<>();
        try {
            WaitForAsyncUtils.waitFor(2000, TimeUnit.MILLISECONDS,
                    () -> {
                        while (true) {
                            List<Node> allNodes = findAllNodes(nodePredicate);
                            if (allNodes != null) {
                                nodes.addAll(allNodes);
                                return true;
                            }
                            Thread.sleep(100);
                        }
                    });
        } catch (TimeoutException e) {
            fail("No appropriate node available");
        }
        return nodes;
    }

}
