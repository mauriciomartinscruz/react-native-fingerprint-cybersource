package com.mauriciomartinscruz.FingerprintCybersource;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.threatmetrix.TrustDefender.TMXProfiling;
import com.threatmetrix.TrustDefender.TMXConfig;
import com.threatmetrix.TrustDefender.TMXStatusCode;
import com.threatmetrix.TrustDefender.TMXProfilingOptions;
import com.threatmetrix.TrustDefender.TMXEndNotifier;
import com.threatmetrix.TrustDefender.TMXProfilingHandle.Result;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RNFingerprintCybersourceModule extends ReactContextBaseJavaModule {

  private static final String CYBERSOURCE_SDK = "RNFingerprintCybersource";
  private TMXProfiling _defender = null;
  public String sessionStr = "";

  public RNFingerprintCybersourceModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return CYBERSOURCE_SDK;
  }

  @ReactMethod
  public void configure(final String orgId, final Promise promise) {
    if (_defender != null) {
      promise.reject(CYBERSOURCE_SDK, "CyberSource SDK is already initialised");
      return;
    }

    _defender = TMXProfiling.getInstance();

    try {
      Log.d("LOGFINGERPRINT", orgId);


      TMXConfig config = new TMXConfig()
        .setOrgId(orgId)
        .setFPServer("h.online-metrix.net")
        .setProfileTimeout(20, TimeUnit.SECONDS)
        .setContext(getReactApplicationContext());

      Log.d("LOGFINGERPRINT", config.toString());

      _defender.init(config);
      Log.d("LOGFINGERPRINT Init", _defender.toString());
    } catch (IllegalArgumentException exception) {
      promise.reject(CYBERSOURCE_SDK, "Invalid parameters");
    }

    promise.resolve(true);
  }

  @ReactMethod
  public void getSessionID(final ReadableArray attributes, final Promise promise) {
    if (_defender == null) {
      promise.reject(CYBERSOURCE_SDK, "CyberSource SDK is not yet initialised");
      return;
    }

    List<String> list = new ArrayList<>();
    sessionStr = "";

    int leni = attributes.size();
    for (int i = 0; i < leni; ++i) {
      String value = attributes.getString(i);
      if (value != null) {
        sessionStr += value;
        list.add(value);
      }
    }

    Log.d("LOGFINGERPRINT", list.toString());

    TMXProfilingOptions options = new TMXProfilingOptions().setCustomAttributes(list);
    options.setSessionID(sessionStr);
    TMXProfiling.getInstance().profile(options, new CompletionNotifier(promise));
  }

  private class CompletionNotifier implements TMXEndNotifier {
      private final Promise _promise;

      CompletionNotifier(Promise promise) {
        super();
        _promise = promise;
      }

      @Override
      public void complete(Result result) {
        Log.d("LOGFINGERPRINT", result.getSessionID());

        WritableMap map = new WritableNativeMap();
        map.putString("sessionId", result.getSessionID());
        map.putInt("status", result.getStatus().ordinal());
        _promise.resolve(map);
      }
  }
}
