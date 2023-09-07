package com.beaker.cpen321tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private Button locationButton;
    private Button mapsButton;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN  = 1;
    private Button signOutButton;
    private Button phoneDetailsButton;
    final static String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationButton = findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Requesting location permissions");

                checkLocationPermissions();
                Toast.makeText(MainActivity.this, "Trying to request location permissions", Toast.LENGTH_SHORT).show();

            }
        });

        mapsButton = findViewById(R.id.maps_button);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Trying to open google maps");
                Intent mapsIntent =  new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapsIntent);
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        phoneDetailsButton = findViewById(R.id.phone_det_but);
        phoneDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneDetailsIntent = new Intent(MainActivity.this, PhoneDetailsActivity.class);
                startActivity(phoneDetailsIntent);
            }
        });


    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "LOGOUT successful");
                        // ...
                    }
                });
    }

    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account == null)
        {
            Log.d(TAG, "There is no user signed in");
            // TODO add dialog
        }
        else
        {
            Log.d(TAG, "Preferred name: " + account.getDisplayName());
            Log.d(TAG, "Email: " + account.getEmail());
            Log.d(TAG, "Given Name " + account.getGivenName());
            Log.d(TAG, "Family Name " + account.getFamilyName());
            Log.d(TAG, "Display URL " + account.getPhotoUrl());

            //send token to backend
            account.getIdToken();
            //move to another activity
            Intent serverDetailsIntent = new Intent(MainActivity.this, SeverDetails.class);
            serverDetailsIntent.putExtra("account", account);
            startActivity(serverDetailsIntent);

        }

    }

    private void checkLocationPermissions()
    {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this, "Permissions Grangted!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(MainActivity.this, "We need these location permissions to run!", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("Need location permissions")
                        .setMessage("Need location permissions to mark location on map")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "We need these permissions to run!", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                            }
                        })
                        .create()
                        .show();
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


    }
}