package com.example.tuinventario;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Libreria de codigo abierto, contiene metodos para obtener datos
 * a partir del lector de codigo de barras
 */

public class SimpleScannerActivity extends Activity implements ZBarScannerView.ResultHandler {
    private static final String TAG = "TEXTO";
    private ZBarScannerView mScannerView;
    final private int REQUEST_CODE_ASK_PERMISSION = 111;




    /**
     *Metodo donde se inicializa el objeto mScannerView y luego se asigna al contenido
     * principal del activity
     */
    @Override
    public void onCreate(Bundle state) {
        solicitarPermisos();
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
    }

    /**
     * Metodo que permite que la camara se desactive cuando la
     * aplicacion pase a segundo plano
     */
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    /**
     * Metodo que permite que la camara se desactive cuando la
     * aplicacion pase a segundo plano
     */
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    /**
     * Metodo que es llamado cuando se recibe un codigo
     * atraves del objeto result el cual contiene toda la informacion.
     */
    @Override
    public void handleResult(Result rawResult) {

        final String code = rawResult.getContents();

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }

        final Intent data = new Intent();
        data.putExtra("Codigo_Barra", code);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    /**
     * Metodo que solicitara permiso de Camara en caso de que
     * no se tenga con anterioridad todo esto en tiempo de
     * ejecucion
     */
    private void solicitarPermisos(){
        int permisoCamara = ActivityCompat.checkSelfPermission(SimpleScannerActivity.this, Manifest.permission.CAMERA);

        if(permisoCamara != PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSION);
            }
        }
    }

}