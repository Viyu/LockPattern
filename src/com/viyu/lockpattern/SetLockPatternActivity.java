package com.viyu.lockpattern;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.viyu.lockpattern.LockPatternView.Cell;
import com.viyu.lockpattern.LockPatternView.OnPatternListener;
import com.viyu.lockpatterndemo.R;

public class SetLockPatternActivity extends Activity implements OnPatternListener, OnClickListener {

	private final static String TAG = "LockPatternActivity";

	public static final int STATUS_FIRST_READY = 101;
	public static final int STATUS_FIRST_STARTED = 102;
	public static final int STATUS_TOO_SHORT = 103;
	public static final int STATUS_FIRST_OK = 105;
	public static final int STATUS_SECOND_READY = 106;
	public static final int STATUS_SECOND_STARTED = 107;
	public static final int STATUS_SECOND_NOT_MATCHED = 108;
	public static final int STATUS_SECOND_OK = 109;

	private SetLockPatternView mView = null;
	private SetLockPatternModel mModel = null;

	private int mCurrentStatus = STATUS_FIRST_READY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		mModel = new SetLockPatternModel();
		mView = new SetLockPatternView(this);
		//
		reset();
	}

	private void dealLockPattern(Context context, List<Cell> lockPattern) {
		String data = LockPatternUtils.convertToSequence(lockPattern);
		//
		if (mModel.getLockPattern() == null) {
			if (mModel.isTooShort(data) == true) {
				mCurrentStatus = STATUS_TOO_SHORT;
				Log.d(TAG, "is too short.");
				return;
			}
			mModel.setLockPattern(data);
			mCurrentStatus = STATUS_FIRST_OK;
			Log.d(TAG, "first pattern is ok.");
			return;
		} else {
			if (mModel.getLockPattern().equals(data)) {
				mCurrentStatus = STATUS_SECOND_OK;
				Log.d(TAG, "pattern is matched and saved.");
				return;
			} else {
				mCurrentStatus = STATUS_SECOND_NOT_MATCHED;
				Log.d(TAG, "pattern is not matched.");
				return;
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.process_btn) {
			if (mCurrentStatus == STATUS_FIRST_OK) {
				mCurrentStatus = STATUS_SECOND_READY;
			} else if (mCurrentStatus == STATUS_SECOND_OK) {
				LockPatternUtils.saveToPreferences(this, mModel.getLockPattern());
				closeActivity(true);
			}
			mView.updateView(mCurrentStatus);
			
		} else if (v.getId() == R.id.cancel_btn) {
			if (mCurrentStatus == STATUS_FIRST_OK) {
				reset();
			} else {
				closeActivity(false);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		closeActivity(false);
	}

	private void reset() {
		mCurrentStatus = STATUS_FIRST_READY;
		mModel.setLockPattern(null);
		mView.updateView(mCurrentStatus);
	}
	
	public void closeActivity(boolean lockPatternSaved) {
		if(lockPatternSaved == true) {
			setResult(Activity.RESULT_OK);
		} else {
			setResult(Activity.RESULT_CANCELED);
		}
		finish();
	}

	@Override
	public void onPatternStart() {
		Log.d(TAG, "onPatternStart");
		//
		if (mCurrentStatus == STATUS_FIRST_READY) {
			mCurrentStatus = STATUS_FIRST_STARTED;
		} else if (mCurrentStatus == STATUS_SECOND_READY) {
			mCurrentStatus = STATUS_SECOND_STARTED;
		}
		//
		mView.updateView(mCurrentStatus);
	}

	@Override
	public void onPatternDetected(List<Cell> pattern) {
		Log.d(TAG, "onPatternDetected:" + pattern);
		//
		dealLockPattern(this, pattern);
		//
		mView.updateView(mCurrentStatus);
	}

	@Override
	public void onPatternCellAdded(List<Cell> pattern) {
		Log.d(TAG, "onPatternCellAdded:" + pattern);
	}

	@Override
	public void onPatternCleared() {
		Log.d(TAG, "onPatternCleared");
	}
}
