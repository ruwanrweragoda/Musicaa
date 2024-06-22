package com.s22010360.musicaa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Bandimu_Suda extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1;
    private Button recordButton, playButton, saveButton, stopButton;
    private SeekBar timingBalanceSeekBar;
    private TextView timingBalanceTextView;
    private boolean isRecording = false;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String audioFilePath;
    private String karaokeFilePath;
    private String mixedAudioFilePath;
    private int timingBalance = 0; // milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandimu_suda);

        recordButton = findViewById(R.id.button_record);
        playButton = findViewById(R.id.button_play);
        saveButton = findViewById(R.id.button_save);
        stopButton = findViewById(R.id.button_stop);
        timingBalanceSeekBar = findViewById(R.id.seekBar_timing_balance);
        timingBalanceTextView = findViewById(R.id.tv_timing_balance);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                } else {
                    startRecording();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecording();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayingRecording();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecording();
            }
        });

        timingBalanceSeekBar.setMax(20000); // Range from -10000ms to +10000ms
        timingBalanceSeekBar.setProgress(10000); // Set initial position to 0ms
        timingBalanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timingBalance = progress - 10000; // Range from -10000ms to +10000ms
                timingBalanceTextView.setText("Timing Balance: " + timingBalance + "ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        audioFilePath = getExternalFilesDir(null).getAbsolutePath() + "/recording.mp4";
        karaokeFilePath = getExternalFilesDir(null).getAbsolutePath() + "/karaoke.mp3";
        mixedAudioFilePath = getExternalFilesDir(null).getAbsolutePath() + "/mixed_recording.mp4";

        copyRawResourceToFile(R.raw.bandimu_suda, karaokeFilePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean recordPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean writePermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (recordPermissionGranted && writePermissionGranted) {
                    Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE);
            return;
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(audioFilePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            recordButton.setText("Stop Recording");

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(karaokeFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            try {
                isRecording = false;
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                recordButton.setText("Start Recording");
            } catch (RuntimeException e) {
                e.printStackTrace();
                Toast.makeText(this, "Recording failed. Please try again.", Toast.LENGTH_SHORT).show();
            } finally {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mixAudioFiles();
            }
        }
    }

    private void mixAudioFiles() {
        // Create the FFmpeg command to mix audio
        String timingBalanceDelay = timingBalance < 0 ? String.format("adelay=%d|%d", -timingBalance, -timingBalance) : "anull";
        String karaokeDelay = timingBalance > 0 ? String.format("adelay=%d|%d", timingBalance, timingBalance) : "anull";

        String[] command = {
                "-y",
                "-i", audioFilePath,
                "-i", karaokeFilePath,
                "-filter_complex", "[0:a]" + timingBalanceDelay + "[aud1];[1:a]" + karaokeDelay + "[aud2];[aud1][aud2]amix=inputs=2:duration=first[aout]",
                "-map", "[aout]",
                "-ac", "2",
                "-c:a", "aac",
                mixedAudioFilePath
        };

        FFmpeg.executeAsync(command, (executionId, returnCode) -> {
            if (returnCode == 0) {
                runOnUiThread(() -> Toast.makeText(Bandimu_Suda.this, "Audio Mixed Successfully", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(Bandimu_Suda.this, "Failed to Mix Audio", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void playRecording() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mixedAudioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlayingRecording() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(this, "Playback stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveRecording() {
        // Implement saving functionality here
        Toast.makeText(this, "Recording saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    private void copyRawResourceToFile(int rawResourceId, String filePath) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(rawResourceId);
            File outputFile = new File(filePath);
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
