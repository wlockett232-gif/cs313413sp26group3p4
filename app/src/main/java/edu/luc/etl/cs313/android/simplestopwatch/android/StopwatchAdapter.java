package edu.luc.etl.cs313.android.simplestopwatch.android;

import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Locale;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.EditText;
import android.media.MediaPlayer;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.ConcreteStopwatchModelFacade;
import edu.luc.etl.cs313.android.simplestopwatch.model.StopwatchModelFacade;

public class StopwatchAdapter extends Activity implements StopwatchModelListener {

    private StopwatchModelFacade model;
    private MediaPlayer mediaPlayer;

    protected void setModel(final StopwatchModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setModel(new ConcreteStopwatchModelFacade());
        model.setModelListener(this);
        final EditText tvS = findViewById(R.id.seconds);
        tvS.setOnEditorActionListener((v, actionId, event) -> {
            // Triggers when the user presses "Enter" or "Done" on the keyboard
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try {
                    int typedTime = Integer.parseInt(tvS.getText().toString());
                    model.onSetTime(typedTime); // Set the time
                    model.onStartStop();        // Start the timer
                } catch (NumberFormatException e) {
                    // Ignore if empty
                }

                // Hide the on-screen keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // Clear focus from the text box
                tvS.clearFocus();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.start();
    }


    @Override
    public void onTimeUpdate(final int time) {
        runOnUiThread(() -> {
            final TextView tvS = findViewById(R.id.seconds);
            final var locale = Locale.getDefault();
            tvS.setText(String.format(locale,"%02d", time));
        });
    }

    @Override
    public void onStateUpdate(final int stateId) {
        runOnUiThread(() -> {
            final TextView stateName = findViewById(R.id.stateName);
            stateName.setText(getString(stateId));

            // --- ADD THE BOOLEAN LOGIC HERE ---
            // Check if the current state is "stopped"
            boolean isStopped = getString(stateId).equals(getString(R.string.stopped));

            // Find the EditText and enable/disable it based on that boolean
            final EditText tvS = findViewById(R.id.seconds);
            tvS.setEnabled(isStopped);
            tvS.setFocusableInTouchMode(isStopped);

            if (getString(stateId).equals(getString(R.string.alarming))) {
                playAlarm();
            } else {
                stopAlarm();
            }
        });
    }
    /**
     * Handlers for the three specific UI buttons
     */
    public void onStartStop(final View view) { // time logic
        // routes to the state machine.
        model.onStartStop();
    }
    public void playAlarm() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), android.provider.Settings.System.DEFAULT_NOTIFICATION_URI);
            mediaPlayer.setLooping(true);
        }
        mediaPlayer.start();
    }

    public void stopAlarm() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }
    }
