package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;


class AlarmingState implements StopwatchState {

    private final StopwatchSMStateView sm;

    public AlarmingState(final StopwatchSMStateView sm) {

        this.sm = sm;
    }

    @Override
    public void onStartStop() {
        sm.actionStopAlarm();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onLapReset() { }

    @Override
    public void onTick() {

        // no countdown here

    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARMING;
    }

    @Override
    public void onSetTime(int time) {

    }
}