package ru.nt202.smiles2048.model;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import ru.nt202.smiles2048.view.GameActivity;

public class Smile {
    private static final String TAG = Smile.class.getSimpleName();

    private Context context;
    private Resources resources;
    private ImageView imageView;
    private String currentName;
    private int currentRow, currentColumn;
    private String destinationName;
    private int destinationRow, destinationColumn;
    private boolean isMoveable;
    private boolean isFade;
    private boolean isAppear;
    private static final long DURATION_MOVE = 4000; // milliseconds
    private static final long DURATION_ALPHA = 4000; // milliseconds

    public Smile(boolean isMoveable,
                 int currentName,
                 int currentRow,
                 int currentColumn,
                 int destinationName,
                 int destinationRow,
                 int destinationColumn,
                 boolean isFade,
                 boolean isAppear) {
        this.isMoveable = isMoveable;
        this.currentName = "ic_" + currentName;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
        this.destinationName = "ic_" + destinationName;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.isFade = isFade;
        this.isAppear = isAppear;
    }

    public void setContext(Context context) {
        this.context = context;
        resources = context.getResources();
    }

    public void setImageView(ImageView square) {
        imageView = square;
    }

    public void animate() {
        imageView.setX(currentColumn * 200);
        imageView.setY(currentRow * 200);
        if (context != null) {
            if (currentName.equals("ic_0")) {
//                imageView.setVisibility(View.GONE);
                return;
            } else {
                setImageView(currentName);
            }
            ObjectAnimator moveAnimation = null;
            ObjectAnimator alphaAnimation = null;
            AnimatorSet animatorSet = new AnimatorSet();
            if (isMoveable) {
                if (currentColumn != destinationColumn) {
                    moveAnimation = ObjectAnimator.ofFloat(imageView, "x", GameActivity.getSquareX(destinationColumn));
                } else if (currentRow != destinationRow) {
                    moveAnimation = ObjectAnimator.ofFloat(imageView, "y", GameActivity.getSquareY(destinationRow));
                }
                moveAnimation.setDuration(DURATION_MOVE);
                moveAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setImageView(destinationName);
                        imageView.setAlpha(1.0f);
                    }
                });
            }
            if (isFade) {
                setImageView(currentName);
                alphaAnimation = ObjectAnimator.ofFloat(imageView, View.ALPHA, 1.0f, 0.0f);
                alphaAnimation.setDuration(DURATION_ALPHA - 300);
            }
//            if (isAppear) {
//                setImageView(destinationName);
//                alphaAnimation = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0.0f, 1.0f);
//                alphaAnimation.setDuration(DURATION_ALPHA + 300);
//            }
            if (moveAnimation != null && alphaAnimation != null) {
                animatorSet.playTogether(moveAnimation, alphaAnimation);
                animatorSet.start();
            } else {
                if (moveAnimation != null) {
                    moveAnimation.start();
                }
                if (alphaAnimation != null) {
                    alphaAnimation.start();
                }
            }
        } else {
            Log.e(TAG, "Context is null");
        }
    }

    private void setImageView(String name) {
        int smileId = resources.getIdentifier(name, "drawable", context.getPackageName());
        imageView.setImageResource(smileId);
    }

    private float getFloatValue(int destination) {
        return (float) (destination * 200);
    }

    @Override
    public String toString() {
        return "Smile{" +
                "isMoveable=" + isMoveable +
                ", currentName='" + currentName + '\'' +
                ", currentRow=" + currentRow +
                ", currentColumn=" + currentColumn +
                ", destinationName='" + destinationName + '\'' +
                ", destinationRow=" + destinationRow +
                ", destinationColumn=" + destinationColumn +
                ", isFade=" + isFade +
                ", isAppear=" + isAppear +
                '}';
    }
}
