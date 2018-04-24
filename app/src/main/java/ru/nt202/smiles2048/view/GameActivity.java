package ru.nt202.smiles2048.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nt202.smiles2048.R;
import ru.nt202.smiles2048.model.GameModel;
import ru.nt202.smiles2048.presenter.GamePresenter;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = GameActivity.class.getSimpleName();

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    private GamePresenter presenter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        GameModel gameModel = GameModel.getInstance();
        presenter = new GamePresenter(gameModel);
        presenter.attachView(this);
        frameLayout.setOnTouchListener(this);
        presenter.update();
    }

    public void animateSmiles(ArrayList<Smile> smiles) {
        for (final Smile smile : smiles) {
            smile.setContext(this);
            smile.animate();
            System.out.println(smile);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                presenter.actionDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                presenter.actionUp(event.getX(), event.getY());
                presenter.update();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
