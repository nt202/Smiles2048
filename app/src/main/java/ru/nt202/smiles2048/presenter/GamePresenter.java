package ru.nt202.smiles2048.presenter;

import java.util.ArrayList;

import ru.nt202.smiles2048.model.GameModel;
import ru.nt202.smiles2048.model.MotionHelper;
import ru.nt202.smiles2048.model.Smile;
import ru.nt202.smiles2048.view.GameActivity;

public class GamePresenter {
    private static final String TAG = GamePresenter.class.getSimpleName();

    private GameActivity view;
    private final GameModel model;

    public GamePresenter(GameModel model) {
        this.model = model;
    }

    public void attachView(GameActivity gameActivity) {
        view = gameActivity;
    }

    public void detachView() {
        view = null;
    }

    public void actionDown(float x, float y) {
        MotionHelper.xDown = x;
        MotionHelper.yDown = y;
    }

    public void actionUp(float x, float y) {
        MotionHelper.xUp = x;
        MotionHelper.yUp = y;
    }

    public void update() {
        model.updateModel(MotionHelper.getDirection());
        ArrayList<Smile> smiles = model.getSmiles();
        view.animateSmiles(smiles);
    }
}
