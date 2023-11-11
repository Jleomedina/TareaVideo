package com.example.tareavideo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> videoCaptureLauncher;
    private VideoView videoView;
    private Button recordButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        recordButton = findViewById(R.id.record);
        saveButton = findViewById(R.id.save);

        videoCaptureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleVideoCaptureResult
        );

        recordButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoCaptureLauncher.launch(intent);


            saveButton.setVisibility(View.VISIBLE);
        });

        saveButton.setOnClickListener(view -> {

            saveVideo();
        });
    }

    private void saveVideo() {
        saveButton.setVisibility(View.GONE);
    }

    private void handleVideoCaptureResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                videoView.setVideoURI(data.getData());
                videoView.start();
            }
        }
    }
}

