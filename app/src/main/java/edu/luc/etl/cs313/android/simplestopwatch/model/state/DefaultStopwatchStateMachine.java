package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultStopwatchStateMachine implements StopwatchStateMachine {

    private final TimeModel timeModel;
    private final ClockModel clockModel;
    private StopwatchState state;
    private StopwatchModelListener listener;

    public DefaultStopwatchStateMachine(final TimeModel timeModel, final ClockModel clockModel) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
    }

    protected void setState(final StopwatchState state) {
        this.state = state;
        listener.onStateUpdate(state.getId());
    }

    @Override
    public void setModelListener(final StopwatchModelListener listener) {
        this.listener = listener;
    }

    @Override public synchronized void onStartStop() { state.onStartStop(); }
    @Override public synchronized void onLapReset()  { state.onLapReset(); }
    @Override public synchronized void onTick()      { state.onTick(); }

    // known states
    private final StopwatchState STOPPED  = new StoppedState(this);
    private final StopwatchState RUNNING  = new RunningState(this);
    private final StopwatchState ALARMING = new AlarmingState(this);

    // transitions
    @Override public void toStoppedState()  { setState(STOPPED); }
    @Override public void toRunningState()  { setState(RUNNING); }
    @Override public void toAlarmingState() { setState(ALARMING); }

    // actions — leave stubs for teammates to implement
    @Override public void actionInit()        { toStoppedState(); actionReset(); }

    @Override
    public void onSetTime(int time) {
        // Route the manually typed time down into the current state
        state.onSetTime(time);
    }

    @Override public void actionReset()       { timeModel.resetRuntime(); actionUpdateView(); }
    @Override public void actionStart()       { clockModel.start(); }
    @Override public void actionStop()        { clockModel.stop(); }
    @Override public void actionUpdateView()  { state.updateView(); }
    @Override public void updateUIRuntime()   { listener.onTimeUpdate(timeModel.getRuntime()); }

    // stubs for teammates
    @Override
    public void actionInc() {
        timeModel.incRuntime();
        updateUIRuntime();
    }
    @Override
    public void actionDec() {
        timeModel.decRuntime();
        updateUIRuntime();
    }
    @Override public void actionIncHold()     { /* teammate: clock logic */ }
    @Override public void actionResetHold()   { /* teammate: clock logic */ }
    @Override public void actionBeep()        { /* teammate: MediaPlayer */ }
    @Override public void actionAlarm()       { /* teammate: MediaPlayer */ }
    @Override public void actionStopAlarm()   { /* teammate: MediaPlayer */ }
    @Override
    public boolean isTimeZero() {
        return timeModel.getRuntime() == 0;
    }
    @Override
    public boolean isTimeMax() {
        return timeModel.getRuntime() == 99;
    }
    @Override public boolean isHoldComplete() { return false; /* teammate: clock logic */ }
}