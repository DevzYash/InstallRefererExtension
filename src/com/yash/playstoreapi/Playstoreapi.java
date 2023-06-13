package com.yash.playstoreapi;

import android.content.Context;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;


public class Playstoreapi extends AndroidNonvisibleComponent {
public Context context;
  public Playstoreapi(ComponentContainer container) {
    super(container.$form());
    context = container.$context();
  }

  @SimpleFunction(description = "Returns the sum of the given list of integers.")
  public void initailize() {
    InstallReferrerClient referrerClient;
    String referrer = "";

    referrerClient = InstallReferrerClient.newBuilder(context).build();
    referrerClient.startConnection(new InstallReferrerStateListener() {
      @Override
      public void onInstallReferrerSetupFinished(int i) {
        switch (i) {
          case InstallReferrerClient.InstallReferrerResponse.OK:
            ReferrerDetails response = null;
            try {
              response = referrerClient.getInstallReferrer();

              // on below line we are getting referrer url.
              String referrerUrl = response.getInstallReferrer();

              // on below line we are getting referrer click time.
              long referrerClickTime = response.getReferrerClickTimestampSeconds();

              // on below line we are getting app install time
              long appInstallTime = response.getInstallBeginTimestampSeconds();

              // on below line we are getting our time when
              // user has used our apps instant experience.
              boolean instantExperienceLaunched = response.getGooglePlayInstantParam();

              // on below line we are getting our
              // apps install referrer.
              String refrer = response.getInstallReferrer();

              GotDetails(referrerUrl,refrer,referrerClickTime,appInstallTime);

            } catch (Exception e) {
              // handling error case.
              ErrorOccured(e.toString());
            }
            break;
          case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
            // API not available on the current Play Store app.
            ErrorOccured("Feature Not Supported On this playstore version");
            break;
          case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
            // Connection couldn't be established.
            ErrorOccured("Failed to establish connection");
            break;
        }
      }

      @Override
      public void onInstallReferrerServiceDisconnected() {
        ErrorOccured("Service Disconnect");
      }
    });

  }

  @SimpleEvent(description = "")
  public void GotDetails(String referralUrl, String referData, long referClickTime, long appInstallTime ){
    EventDispatcher.dispatchEvent(this,"GotDetails",referralUrl,referData,referClickTime,appInstallTime);
  }

  @SimpleEvent(description = "")
  public void ErrorOccured(String error){
    EventDispatcher.dispatchEvent(this,"ErrorOccured",error);
  }

}
