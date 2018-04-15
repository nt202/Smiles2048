package ru.nt202.smiles2048.view;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Map;

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

    private static ImageView[] squares;
    private static ArrayList<Pair<Float, Float>> squaresLayout;
    private GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        gridLayout.setOnTouchListener(this);
        squares = new ImageView[16];
        Resources resources = this.getResources();
        squaresLayout = new ArrayList<>(16);
        for (int n = 0; n < 16; n++) {
            String idName = "square" + n;
            int resourceId = resources.getIdentifier(idName, "id", this.getPackageName());
//            System.out.println(resourceId);
            squares[n] = findViewById(resourceId);
//            Pair<Float, Float> coordinates = new Pair<>(squares[n].getX(), squares[n].getY());
//            squaresLayout.add(coordinates);
        }
        GameModel gameModel = GameModel.getInstance();
        presenter = new GamePresenter(gameModel);
        presenter.attachView(this);
    }

    public void animateSmiles(ArrayList<Smile> smiles) {
        for (int i = 0; i < 16; i++) {
            smiles.get(i).setContext(this);
//            squares[i].setX(squaresLayout.get(i).first);
//            System.out.println(squaresLayout.get(i).first);
//            squares[i].setY(squaresLayout.get(i).second);
//            System.out.println(squaresLayout.get(i).second);
            squares[i].setImageResource(R.drawable.ic_0);
            squares[i].setAlpha(1.0f);
            smiles.get(i).setImageView(squares[i]);
            smiles.get(i).animate();
            System.out.println(smiles.get(i).toString());
        }
    }

    public static float getSquareY(@NonNull int destinationRow) {
//        System.out.println(squares[destinationRow].getX());
//        return squaresLayout.get(destinationRow * 4).second;
        return destinationRow*200;
    }

    public static float getSquareX(@NonNull int destinationColumn) {
//        return squaresLayout.get(destinationColumn).first;
        return destinationColumn*200;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
