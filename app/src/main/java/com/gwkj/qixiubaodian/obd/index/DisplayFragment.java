package com.gwkj.qixiubaodian.obd.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 *
 */
public class DisplayFragment {
	private Fragment currentFragment;

	public void showFragment(Fragment fragment, FragmentActivity fraActivity,
			int frameLayoutId) {
		if (fragment == null || currentFragment == fragment)
			return;
		FragmentTransaction fTransaction = fraActivity
				.getSupportFragmentManager().beginTransaction();

		if (currentFragment != null && currentFragment != fragment) {
			fTransaction.hide(currentFragment);
		}

		if (!fragment.isAdded()) {
			fTransaction.add(frameLayoutId, fragment);
			currentFragment = fragment;
		} else if (fragment.isHidden()) {
			fTransaction.show(fragment);
			currentFragment = fragment;
		}
		fTransaction.commitAllowingStateLoss();
	}

}
