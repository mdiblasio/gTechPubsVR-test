package main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.vrtoolkit.cardboard.samples.TreasureHuntActivity;
import com.google.vrtoolkit.cardboard.samples.treasurehunt.R;

import common.TextureHelper;
import opengltexture.OpenGLActivity;
import opengltexture.SurfaceRenderer;

public class MainActivity extends AppCompatActivity {

    private Boolean requestedAd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // make ad request and add to linear layout
    public void makeAdRequest() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.adViewContainer);
        linearLayout.removeAllViews();

        PublisherAdView mAdView = new PublisherAdView(this);

        mAdView.setAdUnitId(this.getResources().getString(R.string.banner_ad_unit_id));
        mAdView.setAdSizes(new AdSize(300, 250));
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        linearLayout.addView(mAdView);
    }

    public void makeAdRequest(View v) {
        makeAdRequest();
        requestedAd = true;
    }


    public void startGL(View view) {
        if (!requestedAd) {
            Toast.makeText(this, "First make an ad request", Toast.LENGTH_SHORT).show();
        } else {
            convertToBitmap();

            Intent intent = new Intent(this, OpenGLActivity.class);
            startActivity(intent);
        }
    }

    public void startCardboard(View view) {
        if (!requestedAd) {
            Toast.makeText(this, "First make an ad request", Toast.LENGTH_SHORT).show();
        } else {
            convertToBitmap();

            Intent intent = new Intent(this, TreasureHuntActivity.class);
            startActivity(intent);
        }
    }

    // convert ad view container to bitmap and set static bitmap
    // in both helper classes
    private void convertToBitmap() {
        View v = findViewById(R.id.adViewContainer);

        Bitmap bitmap = Bitmap.createBitmap(
                v.getMeasuredWidth(),//getLayoutParams().width,
                v.getMeasuredHeight(),//v.getLayoutParams().height,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        v.layout(v.getLeft(), v.getTop(),
                v.getRight(), v.getBottom());
        v.draw(c);

        SurfaceRenderer.mBitmap = bitmap;
        TextureHelper.mBitmap = bitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}