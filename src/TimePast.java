public class TimePast implements Runnable {
    private final int waiter = 500;

    public void run() {

        while (true) {
            Main.game.repaint();
            Main.game.downCurrentBlock();
            try {
                Thread.sleep(this.waiter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
