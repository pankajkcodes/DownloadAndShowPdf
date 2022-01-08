package com.esm.downloadandshowpdf;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvUrl;
    private Button downloadBtn, viewBtn;
    private String filepath = "http://www.africau.edu/images/default/sample.pdf";
    private URL url = null;
    private String filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setOnListener();
    }


    private void initView() {
        tvUrl = findViewById(R.id.tvUrl);
        downloadBtn = findViewById(R.id.downloadPdf);
        viewBtn = findViewById(R.id.viewPdf);

        try {
            url = new URL(filepath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        filename = url.getPath();

        tvUrl.setText(filename);

    }

    private void setOnListener() {

        downloadBtn.setOnClickListener(view -> {
            Uri uri = Uri.parse(filepath);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                    | DownloadManager.Request.NETWORK_WIFI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(filename);
            request.setMimeType("application/pdf");
            request.allowScanningByMediaScanner();
            request.setAllowedOverMetered(true);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,
                    "" + filename);
            ((DownloadManager) getSystemService(DOWNLOAD_SERVICE)).enqueue(request);
        });


        viewBtn.setOnClickListener(v -> {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" +
                    filename);


            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse(filepath), "application/pdf");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(i);


        });
    }
}