package com.jolies.dames;

import com.jolies.dames.R;
import com.jolies.dames.HelloAndroidActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HelloAndroidActivityTest {
	
	@Test
	public void shouldHaveHappySmiles() throws Exception {
		String appName = new HelloAndroidActivity().getResources().getString(
				R.string.app_name);
		assertThat(appName, equalTo("Jolies Dames"));
	}
}
