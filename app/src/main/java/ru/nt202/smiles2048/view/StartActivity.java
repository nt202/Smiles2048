package ru.nt202.smiles2048.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nt202.smiles2048.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.start_start)
    Button buttonStart;
    @BindView(R.id.start_records)
    Button buttonRecords;
    @BindView(R.id.start_quit)
    Button buttonQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        buttonStart.setOnClickListener(this);
        buttonRecords.setOnClickListener(this);
        buttonQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_start:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.start_records:
                startActivity(new Intent(this, RecordsActivity.class));
                break;
            case R.id.start_quit:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
