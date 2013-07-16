package com.gy.tabhost;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MyTabHostActivity extends TabActivity {

	private TabHost tabhost;
	private final String kaixin = "开心网";
	private final String qzone = "QQ空间";
	private final String renren = "人人网";
	private final String tencent = "腾讯网";
	private final String weibo = "微博";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);

		tabhost = getTabHost();

		View view = View.inflate(this, R.layout.tab, null);
		((ImageView) view.findViewById(R.id.img))
		.setImageResource(R.drawable.account_icon_kaixin);
		((TextView) view.findViewById(R.id.text)).setText(kaixin);
		tabhost.addTab(tabhost.newTabSpec("kaixin").setIndicator(view)
				.setContent(new Intent(this, OtherActivity.class)));
		
		View view1 = View.inflate(this, R.layout.tab, null);
		((ImageView) view1.findViewById(R.id.img))
		.setImageResource(R.drawable.account_icon_qzone);
		((TextView) view1.findViewById(R.id.text)).setText(qzone);
		tabhost.addTab(tabhost.newTabSpec("qzone").setIndicator(view1)
				.setContent(new Intent(this, OtherActivity.class)));

		View view2 = View.inflate(this, R.layout.tab, null);
		((ImageView) view2.findViewById(R.id.img))
		.setImageResource(R.drawable.account_icon_renren);
		((TextView) view2.findViewById(R.id.text)).setText(renren);
		tabhost.addTab(tabhost.newTabSpec("renren").setIndicator(view2)
				.setContent(new Intent(this, OtherActivity.class)));

		View view3 = View.inflate(this, R.layout.tab, null);
		((ImageView) view3.findViewById(R.id.img))
		.setImageResource(R.drawable.account_icon_tencent);
		((TextView) view3.findViewById(R.id.text)).setText(tencent);
		tabhost.addTab(tabhost.newTabSpec("tencent").setIndicator(view3)
				.setContent(new Intent(this, OtherActivity.class)));

		View view4 = View.inflate(this, R.layout.tab, null);
		((ImageView) view4.findViewById(R.id.img))
		.setImageResource(R.drawable.account_icon_weibo);
		((TextView) view4.findViewById(R.id.text)).setText(weibo);
		tabhost.addTab(tabhost.newTabSpec("weibo").setIndicator(view4)
				.setContent(new Intent(this, OtherActivity.class)));

		
		tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				Toast.makeText(MyTabHostActivity.this, "==="+tabId, Toast.LENGTH_SHORT).show();
			}
		});


	}

}
