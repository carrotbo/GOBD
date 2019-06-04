package com.gwkj.qixiubaodian.obd.module.report;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.databinding.ActivityEditReportBinding;

public class EditReportActivity extends AppCompatActivity {

    private ActivityEditReportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_report);
    }
}
