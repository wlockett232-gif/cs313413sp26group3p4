package edu.luc.etl.cs313.android.simplestopwatch.model.time;

public class DefaultTimeModel implements TimeModel {

    private int runningTime = 0;
    private int lapTime = -1;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void setRuntime(int time) {
        if (time >= 0 && time <= 99) {
            this.runningTime = time;
        }
    }

    @Override
    public void onSetTime(int time) {

    }

    @Override
    public void incRuntime() {
        // Only increment if we haven't hit the 99 preset maximum
        if (runningTime < 99) {
            ++runningTime;
        }
    }

    @Override
    public void decRuntime() {
        // Subtract one for every second that elapses, stopping at 0
        if (runningTime > 0) {
            runningTime--;
        }
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    @Override
    public void setLaptime() {
        lapTime = runningTime;
    }

    @Override
    public int getLaptime() {
        return lapTime;
    }
}