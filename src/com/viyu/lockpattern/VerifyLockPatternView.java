package com.viyu.lockpattern;

import android.widget.TextView;

import com.viyu.lockpatterndemo.R;

public class VerifyLockPatternView {

	private VerifyLockPatternActivity mActivity = null;

	private LockPatternView mLockPatternView = null;
	private TextView mTipTextView = null;

	public VerifyLockPatternView(VerifyLockPatternActivity activity) {
		mActivity = activity;
		//
		mActivity.setContentView(R.layout.activity_verify_lockpattern);
		mLockPatternView = (LockPatternView) mActivity.findViewById(R.id.lock_pattern_view);
		mLockPatternView.setOnPatternListener(mActivity);
		//
		mTipTextView = (TextView) mActivity.findViewById(R.id.tip_textview);
	}

	public void updateView(int status, int leftTime) {
		switch (status) {
		case VerifyLockPatternActivity.VERIFY_STATUS_READY:
			mTipTextView.setTextColor(mActivity.getResources().getColor(android.R.color.white));
			mTipTextView.setText(R.string.verify_tip_input);
			//
			break;
		case VerifyLockPatternActivity.VERIFY_STATUS_ERROR:
			mTipTextView.setTextColor(mActivity.getResources().getColor(android.R.color.holo_red_dark));
			mTipTextView.setText(
					String.format(mActivity.getResources().getString(R.string.verify_tip_error), leftTime));
			clearPattern();
			//
			break;
		}
	}

	/**
	 * 清除图案
	 */
	private void clearPattern() {
		mLockPatternView.clearPattern();
	}
}