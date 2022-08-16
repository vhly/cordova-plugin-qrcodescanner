package com.selfservit.util;

import android.app.Activity;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import static android.app.Activity.RESULT_OK;

public class QRCodeScanner extends CordovaPlugin {
CallbackContext callback;
	
    @ Override
    public boolean execute(final String action, JSONArray args, final CallbackContext callbackContext)throws JSONException {
        
		if(action.equals("QRCodeScan"))
		{
            scanQRCode(callbackContext);
            return true;
        }
        return true;
	}
	public void scanQRCode(CallbackContext callbackContext) {
        callback = callbackContext;
        IntentIntegrator integrator = new IntentIntegrator(this.cordova.getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
        cordova.setActivityResultCallback(this);
        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult.setKeepCallback(true);
        callback.sendPluginResult(pluginResult);
    }

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent intent) {
         if(requestCode == 49374 && callback != null){
             if (resultCode == RESULT_OK) {
                 IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                 callback.success(result.getContents());
             } 
         }
     }
}