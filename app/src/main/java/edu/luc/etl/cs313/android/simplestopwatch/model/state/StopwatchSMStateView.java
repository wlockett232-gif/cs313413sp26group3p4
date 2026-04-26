package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface StopwatchSMStateView {
    // transitions
    void toRunningState();
    void toStoppedState();
    void toAlarmingState();

    // actions (implemented by teammates)
    void actionInit();
    void actionReset();
    void actionStart();
    void actionStop();
    void actionInc();
    void actionDec();
    void actionIncHold();
    void actionResetHold();
    void actionBeep();
    void actionAlarm();
    void actionStopAlarm();
    void actionUpdateView();

    // guards (implemented by teammates)
    boolean isTimeZero();
    boolean isTimeMax();
    boolean isHoldComplete();

    void updateUIRuntime();
}