import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import client.view.ProgressItem;
import client.view.StatusWindow;
import client.view.WorklistItem;
import network.Sniffer;
import network.SnifferCallback;

import rsa.Factorizer;
import rsa.ProgressTracker;

public class CodeBreaker implements SnifferCallback {

    private final JPanel workList;
    private final JPanel progressList;

    private final JProgressBar mainProgressBar;
    private static ExecutorService pool;
    // -----------------------------------------------------------------------

    private CodeBreaker() {
        StatusWindow w = new StatusWindow();

        workList = w.getWorkList();
        progressList = w.getProgressList();
        mainProgressBar = w.getProgressBar();
    }

    // -----------------------------------------------------------------------

    public static void main(String[] args) {

        /*
         * Most Swing operations (such as creating view elements) must be performed in
         * the Swing EDT (Event Dispatch Thread).
         * 
         * That's what SwingUtilities.invokeLater is for.
         */
        pool = Executors.newFixedThreadPool(2);
        SwingUtilities.invokeLater(() -> {
            CodeBreaker codeBreaker = new CodeBreaker();
            new Sniffer(codeBreaker).start();
        });
    }

    // -----------------------------------------------------------------------

    /** Called by a Sniffer thread when an encrypted message is obtained. */
    @Override
    public void onMessageIntercepted(String message, BigInteger n) {
        System.out.println("message intercepted (N=" + n + ")...");
        SwingUtilities.invokeLater(() -> {
            WorklistItem item = new WorklistItem(n, message);
            ProgressItem prog_aight = new ProgressItem(n, message);
            JButton btn = new JButton("Crack onnnn!!!");
            btn.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    workList.remove(item);
                    progressList.add(prog_aight);
                    ProgressTracker tracker = new Tracker(prog_aight);

                    Runnable task = () -> {
                        try {
                            SwingUtilities.invokeLater(() -> {
                                int progress = mainProgressBar.getValue();
                                int maximum = mainProgressBar.getMaximum();
                                mainProgressBar.setMaximum(maximum + 1000000);
                                mainProgressBar.setValue(maximum + 1000000);
                            });
                            String plaintext = Factorizer.crack(message, n, tracker);
                            SwingUtilities.invokeLater(() -> {
                                JButton delete_btn = new JButton("Delete");
                                delete_btn.addActionListener(x -> {
                                    progressList.remove(prog_aight);
                                });
                                prog_aight.getTextArea().setText(plaintext);
                                prog_aight.add(delete_btn);
                            });
                        } catch (InterruptedException ex) {
                            throw new Error(ex);
                        }

                    };
                    pool.submit(task);
                });
            });
            workList.add(item);
            item.add(btn);
        });
    }

    private static class Tracker implements ProgressTracker {
        private int totalProgress = 0;
        private ProgressItem prog_item;

        public Tracker(ProgressItem aight) {
            prog_item = aight;
        }

        /**
         * Called by Factorizer to indicate progress. The total sum of
         * ppmDelta from all calls will add upp to 1000000 (one million).
         * 
         * @param ppmDelta portion of work done since last call,
         *                 measured in ppm (parts per million)
         */
        @Override
        public void onProgress(int ppmDelta) {
            totalProgress += ppmDelta;
            SwingUtilities.invokeLater(() -> {
                prog_item.getProgressBar().setValue(totalProgress);
            });
            System.out.println("progress = " + totalProgress + "/1000000");
        }
    }
}
