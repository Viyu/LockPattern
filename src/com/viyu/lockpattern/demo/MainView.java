package com.viyu.lockpattern.demo;

import android.widget.TextView; 

import com.viyu.lockpatterndemo.R;

public class MainView {
	public static final int STATUS_LOCK_PATTERN_SAVED = 0;
	public static final int STATUS_LOCK_PATTERN_NOT_SAVED = 1;
	public static final int STATUS_LOCK_PATTERN_VERFIED = 2;
	public static final int STATUS_LOCK_PATTERN_VERFIED_FAILED = 3;

	private MainActivity mActivity = null;

	protected TextView mTipTextView = null;

	public MainView(MainActivity activity) {
		mActivity = activity;
		//
		mActivity.setContentView(R.layout.activity_main);
		//
		mTipTextView = (TextView) mActivity.findViewById(R.id.tip_textview);
	}

	public void updateView(int status) {
		switch (status) {
		case STATUS_LOCK_PATTERN_SAVED:
			mTipTextView.setText("图案密码已设置");
			break;
		case STATUS_LOCK_PATTERN_NOT_SAVED:
			mTipTextView.setText("没有设置图案密码");
			break;

		case STATUS_LOCK_PATTERN_VERFIED:
			mTipTextView.setText("解锁成功");
			break;

		case STATUS_LOCK_PATTERN_VERFIED_FAILED:
			mTipTextView.setText("解锁失败");
			break;
		}
	}
}