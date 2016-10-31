package yosumo.src.activity;



/**
 * Created by David Ricardo on 30/10/2016.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import yosumo.src.R;

import org.json.JSONException;

public class FacebookLoginActivity extends AppCompatActivity
{
    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";

    private static final int PICK_PERMS_REQUEST = 0;

    private CallbackManager callbackManager;

    private ProfilePictureView profilePictureView;
    private TextView userNameView;
    private LoginButton fbLoginButton;

    private Button yoSumoButton;
    private Button shareButton;
   /* private PendingAction pendingAction = PendingAction.NONE;
    private boolean canPresentShareDialog;
    private ShareDialog shareDialog;
       */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login_unique);

        callbackManager = CallbackManager.Factory.create();
        Log.d("HOLA", "login activity creada");

        fbLoginButton = (LoginButton) findViewById(R.id._fb_login);
        profilePictureView = (ProfilePictureView) findViewById(R.id.user_pic);
        profilePictureView.setCropped(true);
        yoSumoButton = (Button) findViewById(R.id.ir_YoSumo);
        shareButton = (Button) findViewById(R.id.postStatusUpdateButton);

        userNameView = (TextView) findViewById(R.id.user_name);

        final Button deAuthButton = (Button) findViewById(R.id.deauth);
        deAuthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isLoggedIn()) {
                    Toast.makeText(
                            FacebookLoginActivity.this,
                            R.string.app_not_logged_in,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                GraphRequest.Callback callback = new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            if(response.getError() != null) {
                                Toast.makeText(
                                        FacebookLoginActivity.this,
                                        getResources().getString(
                                                R.string.failed_to_deauth,
                                                response.toString()),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                            else if (response.getJSONObject().getBoolean(SUCCESS)) {
                                LoginManager.getInstance().logOut();
                                // updateUI();?
                            }
                        } catch (JSONException ex) { /* no op*/ }
                    }
                };
                GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                        GRAPH_PATH, new Bundle(), HttpMethod.DELETE, callback);
                request.executeAsync();
            }
        });

        yoSumoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacebookLoginActivity.this, HomeActivity.class));

            }
        });
         /*
        final Button permsButton = (Button) findViewById(R.id.perms);
        permsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Intent selectPermsIntent =
                        new Intent(FacebookLoginActivity.this, PermissionSelectActivity.class);
                startActivityForResult(selectPermsIntent, PICK_PERMS_REQUEST);
            }
        });*/

        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
                Toast.makeText(
                        FacebookLoginActivity.this,
                        R.string.success,
                        Toast.LENGTH_LONG).show();
                updateUI();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(
                        FacebookLoginActivity.this,
                        R.string.cancel,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
                Toast.makeText(
                        FacebookLoginActivity.this,
                        R.string.error,
                        Toast.LENGTH_LONG).show();
            }
        });

        new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    final Profile oldProfile,
                    final Profile currentProfile) {
                updateUI();
            }
        };

        /*shareDialog = new ShareDialog(this);
        /*shareDialog.registerCallback(
                callbackManager,
                shareCallback);

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPostStatusUpdate();
            }
        });
        // Can we present the share dialog for regular links?
        canPresentShareDialog = ShareDialog.canShow(
                ShareLinkContent.class);
        */
    }

    private enum PendingAction {
        NONE,
        //POST_PHOTO,
        POST_STATUS_UPDATE
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void updateUI() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            profilePictureView.setProfileId(profile.getId());
            userNameView
                    .setText(String.format("%s %s",profile.getFirstName(), profile.getLastName()));
            Log.d("BIEN", "Hay perfil");
        } else {
            profilePictureView.setProfileId(null);
            userNameView.setText("No hay perfil cargado");
            Log.d("MAL","no Hay perfil");
        }
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*
    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }


    private void postStatusUpdate() {
        Profile profile = Profile.getCurrentProfile();
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle("Hello Facebook")
                .setContentDescription(
                        "The 'Hello Facebook' sample  showcases simple Facebook integration")
                .setContentUrl(Uri.parse("http://developers.facebook.com/docs/android"))
                .build();
        if (canPresentShareDialog) {
            shareDialog.show(linkContent);
        } else if (profile != null && hasPublishPermission()) {
            ShareApi.share(linkContent, shareCallback);
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }

    private void performPublish(PendingAction action, boolean allowNoToken) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null || allowNoToken) {
            pendingAction = action;
            handlePendingAction();
        }
    }*/

}
