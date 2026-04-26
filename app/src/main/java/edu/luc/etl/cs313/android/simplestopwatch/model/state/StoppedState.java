package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class StoppedState implements StopwatchState {

    private final StopwatchSMStateView sm;

    public StoppedState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }

    @Override
    public void onStartStop() {
        sm.actionResetHold();
        sm.actionInc();
        if (sm.isTimeMax()) {
            sm.actionBeep();
            sm.actionStart();
            sm.toRunningState();
        } else {
            sm.toStoppedState();
        }
    }

    @Override
    public void onLapReset() { }

    @Override
    public void onTick() {
        sm.actionIncHold();
        if (sm.isHoldComplete() && !sm.isTimeZero()) {
            sm.actionBeep();
            sm.actionStart();
            sm.toRunningState();
        } else {
            sm.toStoppedState();
        }
    }

    @Override
    public void updateView() { sm.updateUIRuntime(); }

    @Override
    public int getId() { return R.string.STOPPED; }

    @Override
    public void onSetTime(int time) {

    }
}
