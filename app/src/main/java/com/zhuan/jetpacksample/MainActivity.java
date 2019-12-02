package com.zhuan.jetpacksample;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zhuan.jetpacksample.livedata.NetworkLiveData;
import com.zhuan.jetpacksample.livemodel.TestLiveModel;

public class MainActivity extends AppCompatActivity {

    private TextView mContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentTv = findViewById(R.id.content);

        final TestLiveModel mTestViewModel = ViewModelProvider
                .AndroidViewModelFactory.getInstance(getApplication())
                .create(TestLiveModel.class);

        MutableLiveData<String> nameEvent = mTestViewModel.getNameEvent();
        nameEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.i("MutableLiveData", "onChanged: s = " + s);
                mContentTv.setText(s);
            }
        });

        NetworkLiveData.getInstance(this).observe(this, new Observer<NetworkInfo>() {
            @Override
            public void onChanged(@Nullable NetworkInfo networkInfo) {
                Log.d("NetworkLiveData", "onChanged: networkInfo=" + networkInfo);
                mTestViewModel.getNameEvent().postValue(networkInfo == null ? "null" : networkInfo.toString());
            }
        });


    }
}
