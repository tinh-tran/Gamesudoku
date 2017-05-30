package Game;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

public class Clock {

    private final Timer timer;//hàm thời gian
    private final JLabel timeLabel;//Label thời gian
    private int seconds;

    public Clock(JLabel label) {
        timeLabel = label;
        seconds = 0;//khởi tạo giây bằng 0
        timer = new Timer();// gáng biến
        timer.schedule(new UpdateUITask(), 1000, 1000);// hàm delay time 
    }

    private class UpdateUITask extends TimerTask {

        @Override
        public void run() {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    seconds += 1000;
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
                    timeLabel.setText("Time: " + String.valueOf(dateFormatter.format(new Date(seconds))));
                }
            });// xu ly thoi gian. Dem tgian da choi
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int newSeconds) {
        seconds = newSeconds;
    }
}
