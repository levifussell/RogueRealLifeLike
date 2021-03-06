package com.example.danielmccarragher.gohide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danielmccarragher.gohide.BackendGameLogic.GAME_CHARACTERS;
import com.example.danielmccarragher.gohide.BackendGameLogic.GridWorld;
import com.example.danielmccarragher.gohide.BackendGameLogic.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button leftButton;
    Button upButton;
    Button rightButton;
    Button downButton;
    Button playerTile;
    char grid[][];
    TextView scoreText;

    private SensorManager sensorManager;
    double ax, ay, az;
    double d_ax, d_ay, d_az = 0;   // these are the acceleration in x,y and z axis
    double i_ay;
    long lastSensorCall = System.currentTimeMillis();

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intialise sensor data
        final Animation translateAnimation = AnimationUtils.loadAnimation(this,R.anim.translate);
        final Animation translateLeftAnimation = AnimationUtils.loadAnimation(this,R.anim.translation_left);
        final Animation translateRightAnimation = AnimationUtils.loadAnimation(this,R.anim.translation_right);
        final Animation translateDownAnimation = AnimationUtils.loadAnimation(this,R.anim.translation_down);
        playerTile = (Button)findViewById(R.id.button4);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);


        leftButton = (Button) findViewById(R.id.leftDirection);
        upButton = (Button)findViewById(R.id.upDirection);
        rightButton = (Button) findViewById(R.id.rightDirection);
        downButton = (Button)findViewById(R.id.downDirection);
//initialize mAuth
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //user has signed in
                    Log.i("UserSignIn", "onAuthStateChanged:signed_in:" + user.getUid());
                }else{
                    //user is signed out
                    Log.i("UserSignIn","onAuthStateChanged:signed_out");
                }
            }
        };

        GridWorld.LOAD();

        GridWorld.LOAD_LEVEL_FROM_FILE("TestLevels/Test1.txt", this.getBaseContext());

        GridWorld.DEBUG_DRAW();

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This is working");
                if(GridWorld.players != null) {
                    playerTile.startAnimation(translateAnimation);
                    ((Player) GridWorld.players.get(0)).moveUp();
                    grid = ((Player) GridWorld.players.get(0)).getScope(1);
                    updateGrid(grid);
                }
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This is working");
                if(GridWorld.players != null) {
                    playerTile.startAnimation(translateLeftAnimation);
                    ((Player) GridWorld.players.get(0)).moveLeft();
                    grid = ((Player) GridWorld.players.get(0)).getScope(1);
                    updateGrid(grid);
                }
            }
        });



        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This is working");
                if(GridWorld.players != null) {
                    playerTile.startAnimation(translateRightAnimation);
                    ((Player) GridWorld.players.get(0)).moveRight();
                    grid = ((Player) GridWorld.players.get(0)).getScope(1);
                    updateGrid(grid);
                }
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This is working");
                if(GridWorld.players != null) {
                    playerTile.startAnimation(translateDownAnimation);
                    ((Player) GridWorld.players.get(0)).moveDown();
                    grid = ((Player) GridWorld.players.get(0)).getScope(1);
                    updateGrid(grid);
                }
            }
        });

        mVisible = true;
        //when movement has been logged
        grid = ((Player) GridWorld.players.get(0)).getScope(1);
        updateGrid(grid);




    }
    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    public void updateGrid(char map[][]){
        int buttonIterator = 0;
        int buttons[] = {R.id.button0,R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8};
        for(int i = 0; i <3; i++){

            for(int j = 0; j < 3; j++){

                switch(map[i][j]){
                    case GAME_CHARACTERS.ENEMY:
                        Button monster = (Button)findViewById(buttons[buttonIterator]);
                        monster.setBackgroundResource(R.drawable.monster);
                        break;
                    case GAME_CHARACTERS.PLAYER:
                        Button hero = (Button)findViewById(buttons[buttonIterator]);
                        hero.setBackgroundResource(R.drawable.hero);
                        break;
                    case GAME_CHARACTERS.EMPTY_SPACE:
                        Button space = (Button)findViewById(buttons[buttonIterator]);
                        space.setBackgroundResource(R.drawable.free_tile);
                        break;
                    case GAME_CHARACTERS.NULL_SPACE:
                        Button outOfBounds = (Button)findViewById(buttons[buttonIterator]);
                        outOfBounds.setBackgroundResource(R.drawable.fire);
                        break;
                    case GAME_CHARACTERS.GOLD:
                        Button gold = (Button)findViewById(buttons[buttonIterator]);
                        gold.setBackgroundResource(R.drawable.gold);
                        break;

                }
                buttonIterator++;
            }
        }

    }

    public void updateFightInfo(String text){
        scoreText.setText(text);
    }
    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("CreateAccount", "createUserWithEmail:onComplete:" + task.isSuccessful());
                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
//        if (mLastAccelerometerSet && mLastMagnetometerSet) {
//            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
//            SensorManager.getOrientation(mR, mOrientation);
//            float azimuthInRadians = mOrientation[0];
//            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
//
//            System.out.println("degrees: " + -azimuthInDegress);
//        }

        long diffLastCall = System.currentTimeMillis() - lastSensorCall;


        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            if(diffLastCall > 800) {
                d_ay = 0;
                lastSensorCall = System.currentTimeMillis();
            }

            d_ax += event.values[0];
            d_ay += event.values[1];
            d_az += event.values[2];

            //check here for a drastic state change
            double step_thresh = 50;
//            if(diffLastCall > 1000) {

                if (d_ay < -step_thresh) {
//                    System.out.println("STEP BACKWARD: " + d_ay);
                    d_ay = 0;
                }
                else if (d_ay > step_thresh) {
//                    System.out.println("STEP FORWARD: " + d_ay);
                    d_ay = 0;
                }
//            }

            i_ay = Math.abs(event.values[1] - ay);
            if(i_ay > 0.5) {
                d_ay = 0;
                lastSensorCall = System.currentTimeMillis();
            }

            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
//            System.out.println("Sensor Readings: " + ax + ", " + ay + ", " + az);
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
//            float xAx = event.values[0];
//            float yAx = event.values[1];
//            float zAx = event.values[2];

            System.out.println("GYRO Y: " + event.values[0]);
        }
    }
}
