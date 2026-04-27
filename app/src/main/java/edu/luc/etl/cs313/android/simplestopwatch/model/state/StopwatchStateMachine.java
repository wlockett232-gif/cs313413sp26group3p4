package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelSource;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.TickListener;

/**
 * The state machine for the state-based dynamic model of the stopwatch.
 * This interface is part of the State pattern.
 *
 * @author laufer
 */
public interface StopwatchStateMachine extends StopwatchUIListener, TickListener, StopwatchModelSource {
    // transitions
    void toStoppedState();

    void toRunningState();

    void actionInit();
    void onSetTime(int time);

    void actionReset();

    void actionStart();

    void actionStop();

    void actionUpdateView();

    void updateUIRuntime();

    // stubs for teammates
    void actionInc();

    void actionDec();

    void actionIncHold();

    void actionResetHold();

    void actionBeep();

    void actionAlarm();

    void actionStopAlarm();

    boolean isTimeZero();

    boolean isTimeMax();

    boolean isHoldComplete();
}