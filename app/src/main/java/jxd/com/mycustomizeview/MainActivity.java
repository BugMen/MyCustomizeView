package jxd.com.mycustomizeview;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    MyDashBoard mDashBoard;
    MyProgress mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDashBoard = (MyDashBoard) findViewById(R.id.myDashBoard);
        mProgress = (MyProgress) findViewById(R.id.myProgress);
        initDashBoard();
        initProgress();
    }

    /**
     * 初始化仪表盘
     */
    private void initDashBoard() {
        mDashBoard.setmDashColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
        mDashBoard.setmDashProColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        mDashBoard.setmDashProgress(60);
        mDashBoard.setmSweepAngle(290);
        mDashBoard.setmRingWidth(8);
        mDashBoard.setmRingStartAngle(125);
        mDashBoard.setmDegrees(2f);
        mDashBoard.setmDashStartAngle(220);
        mDashBoard.setmDashWidth(6);
        mDashBoard.setmDashLength(30);
        mDashBoard.setmRadius(360);
        mDashBoard.setmDashTextSize(25);
    }

    /**
     * 初始化进度条
     */
    private void initProgress() {
        mProgress.setmDefaultColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
        mProgress.setmProgressWidth(10);

        mProgress.setProgress(10f);
        mProgress.setProgressColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        mProgress.setProgressMax(16);
        mProgress.setContent("10V");
    }
}
