package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class RunningState implements StopwatchState {

    private final StopwatchSMStateView sm;

    public RunningState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }

    @Override
    public void onStartStop() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onLapReset() { }

    @Override
    public void onTick() {
        sm.actionDec();
        if (sm.isTimeZero()) {
            sm.actionStop();
            sm.actionAlarm();
            sm.toAlarmingState();
        } else {
            sm.toRunningState();
        }
    }

    @Override
    public void updateView() { sm.updateUIRuntime(); }

    @Override
    public int getId() { return R.string.RUNNING; }

    @Override
    public void onSetTime(int time) {

    }
}