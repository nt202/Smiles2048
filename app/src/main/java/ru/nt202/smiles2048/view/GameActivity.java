package ru.nt202.smiles2048.view;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nt202.smiles2048.R;
import ru.nt202.smiles2048.model.GameModel;
import ru.nt202.smiles2048.model.Smile;
import ru.nt202.smiles2048.presenter.GamePresenter;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = GameActivity.class.getSimpleName();

    @BindView(R.id.game_grid)
    GridLayout gridLayout;

    ImageView[] imageSquares;

    GamePresenter presenter;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        gridLayout.setOnTouchListener(this);
        resources = this.getResources();
        imageSquares = new ImageView[16];
        for (int n = 0; n < 16; n++) {
            String idName = "square" + n;
            int resourceId = resources.getIdentifier(idName, "id", this.getPackageName());
            System.out.println(resourceId);
            imageSquares[n] = findViewById(resourceId);
        }
        GameModel gameModel = GameModel.getInstance();
        presenter = new GamePresenter(gameModel);
        presenter.attachView(this);
    }

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

    public void redraw(ArrayList<Smile> smiles) {
        drawCurrent(smiles);
        drawAnimation(smiles);
    }

    private void drawCurrent(ArrayList<Smile> smiles) {
        for (int i = 0; i < 16; i++) {
            String smileName = smiles.get(i).getCurrentName();
            int smileId = resources.getIdentifier(smileName, "drawable", this.getPackageName());
            imageSquares[i].setImageResource(smileId);
        }
    }
    private void drawAnimation(ArrayList<Smile> smiles) {
        for (int i = 0; i < 16; i++) {
            ObjectAnimator animator = new O
            imageSquares[i].setImageResource(smileId);
            String smileName = smiles.get(i).getCurrentName();
            int smileId = resources.getIdentifier(smileName, "drawable", this.getPackageName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
