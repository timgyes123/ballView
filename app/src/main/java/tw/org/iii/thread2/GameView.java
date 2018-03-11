package tw.org.iii.thread2;

        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Matrix;
        import android.support.annotation.Nullable;
        import android.util.AttributeSet;
        import android.view.View;

        import java.util.Timer;
        import java.util.TimerTask;

/**
 * Created by Administrator on 2018/3/11.
 */

public class GameView extends View {
    private Context context;
    private Resources res;
    private float viewW, viewH, ballW, ballH, ballX, ballY, dx, dy;
    private boolean isInit;
    private Bitmap ballBmp;
    private Timer timer;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        res = context.getResources();
        timer = new Timer();
        dx = dy = 16;
        setBackgroundResource(R.drawable.bg2);
    }

    private void init(){
        isInit = true;
        viewW = getWidth();
        viewH = getHeight();
        ballW = viewW / 20; ballH = ballW;

        ballBmp = BitmapFactory.decodeResource(res, R.drawable.ball);
        float ow = ballBmp.getWidth(), oh = ballBmp.getHeight();

        Matrix matrix = new Matrix();

        matrix.reset();
        matrix.postScale(ballW / ow, ballH / oh);
        ballBmp = Bitmap.createBitmap(ballBmp,0,0,
                (int)ow,(int)oh,matrix,false);

        timer.schedule(new BallTask(), 1000, 80);
    }

    private class BallTask extends TimerTask {
        @Override
        public void run() {
            if (ballX < 0 || ballX + ballW > viewW){
                dx *= -1;
            }
            if (ballY <0 || ballY + ballH > viewH){
                dy *= -1;
            }
            ballX += dx; ballY += dy;
            //invalidate();
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();

        canvas.drawBitmap(ballBmp,ballX,ballY,null);

    }
}