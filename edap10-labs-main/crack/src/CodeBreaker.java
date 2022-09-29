import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        w.enableErrorChecks();
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
        // System.out.println("message intercepted (N=" + n + ")...");
        SwingUtilities.invokeLater(() -> {
            WorklistItem item = new WorklistItem(n, message);
            ProgressItem prog_aight = new ProgressItem(n, message);
            JButton btn = new JButton("Crack onnnn!!!");
            btn.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    workList.remove(item);
                    progressList.add(prog_aight);

                    Tracker tracker = new Tracker(prog_aight, mainProgressBar);
                    int maximum = mainProgressBar.getMaximum();
                    mainProgressBar.setMaximum(maximum + 1000000);
                    JButton cancel_btn = new JButton("Cancel");

                    Runnable task = () -> {
                        try {
                            String plaintext = Factorizer.crack(message, n, tracker);
                            SwingUtilities.invokeLater(() -> {
                                prog_aight.remove(cancel_btn);
                                JButton delete_btn = new JButton("Delete");
                                delete_btn.addActionListener(x -> {
                                    progressList.remove(prog_aight);
                                    SwingUtilities.invokeLater(() -> {
                                        mainProgressBar.setValue(mainProgressBar.getValue() - 1000000);
                                        mainProgressBar.setMaximum(mainProgressBar.getMaximum() - 1000000);
                                    });
                                });
                                prog_aight.getTextArea().setText(plaintext);
                                prog_aight.add(delete_btn);
                            });
                        } catch (InterruptedException ex) {
                            throw new Error(ex);
                        }
                    };

                    Future<?> future = pool.submit(task);
                    SwingUtilities.invokeLater(() -> {
                        cancel_btn.addActionListener(a -> {
                            future.cancel(true);
                            prog_aight.getTextArea().setText("[CANCELLED]");
                            tracker.cancel_progress();
                            prog_aight.remove(cancel_btn);

                        });
                        prog_aight.add(cancel_btn);
                    });
                });
            });
            workList.add(item);
            item.add(btn);
        });
    }

    private static class Tracker implements ProgressTracker {
        private int totalProgress = 0;
        private ProgressItem prog_item;
        private JProgressBar mainProgressBar;

        public Tracker(ProgressItem aight, JProgressBar mainProgressBar) {
            prog_item = aight;
            this.mainProgressBar = mainProgressBar;
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
            int ppmDeltaActual = Math.min((ppmDelta), 1000000 - prog_item.getProgressBar().getValue());
            totalProgress += ppmDeltaActual;
            SwingUtilities.invokeLater(() -> {
                int progress = mainProgressBar.getValue();
                prog_item.getProgressBar().setValue(totalProgress);
                mainProgressBar.setValue(progress + ppmDeltaActual);
            });
            // System.out.println("progress = " + totalProgress + "/1000000");
        }

        public void cancel_progress() {
            SwingUtilities.invokeLater(() -> {
                prog_item.getProgressBar().setValue(1000000);
                int progress = mainProgressBar.getValue();
                int remaining_progress = 1000000 - totalProgress;
                mainProgressBar.setValue(progress + remaining_progress);
            });

        }
    }
}
