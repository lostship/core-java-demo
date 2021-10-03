package com.example.corejava.classes.innerclass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

public class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    public void start() {
        Timer t = new Timer(interval, this.new TimePrinter());
        t.start();
    }

    private class TimePrinter implements ActionListener, Beepable {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("the time is " + new Date());
            if (TalkingClock.this.beep) {
                System.out.println("beep");
            }
        }
    }

    private interface Beepable {}
}
