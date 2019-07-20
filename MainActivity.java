package com.technolifehacker.autorefresh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class MainActivity extends AppCompatActivity {
    AdView adView,adView_medium,adView_setting;
    InterstitialAd interstitialAd;
RelativeLayout rel_ad1,rel_ad_set;
    WebView web;
    EditText url,sec;
    Button save,cancel;
    String url_text,second_in_text;
   int second_in_int;
    String mobile_userAgent = "Mozilla/5.0 (Linux;"+"Android "+ Build.VERSION.RELEASE+"; "+Build.MODEL+" "+"Build/"+Build.DISPLAY+")"+"AppleWebKit/537.36(KHTML,like Gecko) Chrome/69.0.3497.91 Mobile Safari/537.36";
TextView txt_l;

    SharedPreferences sharedpref;
    SharedPreferences sharedpref2;
    public  static  final String PREF_URL = "URL";
    public static final String PREF_SEC = "SEC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Reload();
        adView = new AdView(this,  getString(R.string.banner), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        rel_ad1 = (RelativeLayout) findViewById(R.id.rel_ad1);
        // Add the ad view to your activity layout
        rel_ad1.addView(adView);

        // Request an ad
        adView.loadAd();

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

                adView.loadAd();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });

        sharedpref = getSharedPreferences(PREF_URL,MODE_PRIVATE);
        sharedpref2 = getSharedPreferences(PREF_SEC,MODE_PRIVATE);
        url_text = sharedpref.getString("name","https://www.technolifehacker.com/wp/");
        second_in_text = sharedpref2.getString("second","3");
        second_in_int = Integer.parseInt(second_in_text)*1000;


        Toast.makeText(MainActivity.this, "Start Auto Refresh for\n"+url_text+ "\n\nEvery "+second_in_text+" Second(s)", Toast.LENGTH_LONG).show();

     getSupportActionBar().setSubtitle(getString(R.string.subtitle));


        web = (WebView) findViewById(R.id.webview);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(url_text);
        web.getSettings().getBuiltInZoomControls();
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDisplayZoomControls(false);
        web.getSettings().setDatabaseEnabled(true);
        web.getSettings().setAppCacheEnabled(true);
        web.getSettings().enableSmoothTransition();
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setEnableSmoothTransition(true);
        web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);


            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }
        });
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

            }



            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(MainActivity.this, "Error\n"+error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //Toast.makeText(MainActivity.this, "Loading\n"+url, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               // Toast.makeText(MainActivity.this, "Finished loading.", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_shareapp) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Download "+R.string.app_name+" App Now");
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_text));
            sendIntent.setType("text/plain");

            startActivity(Intent.createChooser(sendIntent, "Share Invitation using"));
            return true;
        }
        if (id == R.id.action_rate) {
            Toast.makeText(this, "Rate Us", Toast.LENGTH_LONG).show();
            String url = "https://play.google.com/store/apps/details?id="+getPackageName();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }
        if (id == R.id.action_more_app) {
            Intent i = new Intent(getApplicationContext(),more_app.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_setting) {
            openSettings();
            return true;
        }
        if (id == R.id.action_refresh) {
            web.reload();
            return true;
        }
        if (id == R.id.action_exit) {
            openDialog();
            return true;
        }
        if (id == R.id.action_desktop){
            if(item.isChecked()){
                // If item already checked then unchecked it
                item.setChecked(false);
                web.getSettings().setUserAgentString(mobile_userAgent);
                Toast.makeText(getApplicationContext(), "Mobile View", Toast.LENGTH_LONG).show();
               web.reload();

            }else{
                web.getSettings().setLoadWithOverviewMode(true);
                // If item is unchecked then checked it
                item.setChecked(true);
                Toast.makeText(getApplicationContext(), "Desktop View", Toast.LENGTH_LONG).show();
                web.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
                web.reload();
            }
        }
        if (id == R.id.action_feedback){
            String recipent = "dev@technolifehacker.com";
            String subject = "Feedback to "+getString(R.string.app_name)+" App  V "+ BuildConfig.VERSION_NAME;
            String device_detail = "\n\n\n\n\n\n\n\n______________________\n"+"Device details\n\n"+"Model : "+ Build.MODEL+"\nAndroid Version : "+Build.VERSION.RELEASE+"\nSDK Version : "+ Build.VERSION.SDK+"\nDevice : "+Build.DEVICE+"\n" + "Brand : "+Build.BRAND+"\n" +"Manufacturer : "+ Build.MANUFACTURER+"\n"+"Product : "+ Build.PRODUCT+"\n"+"Display : "+ Build.DISPLAY+"\n"+"Serial : "+Build.SERIAL;


            Intent intent= new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT,subject);
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{recipent});
            intent.putExtra(Intent.EXTRA_TEXT,device_detail);



            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent,"Choose an email client"));
            // startActivity(intent);
        }








        return super.onOptionsItemSelected(item);
    }

    public void Reload(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                web.reload();
                Reload();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        }, second_in_int);
    }

    private void openSettings() {
       web.reload();
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
        //  dialog.setTitle("Please Confirm!!");
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.settings,null);
        dialog.setView(v);
        dialog.setCancelable(false);
        adView_setting = new AdView(this,  getString(R.string.banner_setting), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        rel_ad_set = v.findViewById(R.id.rel_ad_setting);
        // Add the ad view to your activity layout
        rel_ad_set.addView(adView_setting);

        // Request an ad
        adView_setting.loadAd();

        adView_setting.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

                adView_setting.loadAd();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });
        url =  v.findViewById(R.id.edit_url);
        url.setText(url_text);
        url.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return String.valueOf(source).toLowerCase();
            }
        }});
        sec = v.findViewById(R.id.et_sec);
        sec.setText(second_in_text);

        save = v.findViewById(R.id.save);
        cancel = v.findViewById(R.id.cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               second_in_text = sec.getText().toString();

                getSupportActionBar().setSubtitle(""+second_in_int);
                if (url.getText().toString().startsWith("http://") ||url.getText().toString().startsWith("https://")){
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("name",url.getText().toString());
                    editor.commit();
                    SharedPreferences.Editor editor2 = sharedpref2.edit();
                    editor2.putString("second",sec.getText().toString());
                    editor2.commit();

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    show_full_ad();
                    dialog.dismiss();
                }
                else {
                    url.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    Toast.makeText(MainActivity.this, "Please enter url in correct format", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        dialog.show();








    }
    private void openDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
        //  dialog.setTitle("Please Confirm!!");
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.exit,null);
        dialog.setView(v);
        dialog.setCancelable(false);
        final RelativeLayout rel = v.findViewById(R.id.rel_ad);

        adView_medium = new AdView(this, getString(R.string.medium), AdSize.RECTANGLE_HEIGHT_250);
        // Find the Ad Container
        RelativeLayout adContainer =v.findViewById(R.id.lin_medium_exit);

        // Add the ad view to your activity layout
        adContainer.addView(adView_medium);

        // Request an ad
        adView_medium.loadAd();

        adView_medium.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

                adView_medium.loadAd();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }

        });

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        dialog.show();

        Button btn_more = v.findViewById(R.id.btn_more);
        Button btn_yes = v.findViewById(R.id.btn_yes);
        Button btn_no = v.findViewById(R.id.btn_no);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/store/apps/dev?id=7242335288388906155";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                // Toast.makeText(getApplicationContext(), "Thanks for Visit Technolifehacker App store\n__K A FAROOQUI", Toast.LENGTH_LONG).show();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               show_full_ad();
                finish();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });



    }
    @Override
    public void onBackPressed() {
        if (web.canGoBack()){
            web.goBack();
        }else {
            openDialog();
        }
    }
    public void show_full_ad(){
        interstitialAd = new InterstitialAd(this, getString(R.string.inter));
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e("TAG", "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("TAG", "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {

                // Ad error callback
                Log.e("TAG", "Interstitial ad failed to load: " + adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed

                Log.d("TAG", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("TAG", "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("TAG", "Interstitial ad impression logged!");
            }
        });
        interstitialAd.loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
