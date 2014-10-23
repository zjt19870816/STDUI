package com.st.fragmentbottom;

import com.google.gson.Gson;
import com.st.adapter.MyListAdapter;
import com.st.app.STApplaction;
import com.st.sliding.MainActivity;
import com.st.sliding.R;
import com.st.strut.MyMessage;
import com.st.view.ImgListView;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment") public class GroupFragment extends Fragment {
	private ImgListView mLvImg;
	private MyListAdapter mAdapter;
	private Context mContext;
	
	
	public GroupFragment(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_group, container, false);
		mLvImg = (ImgListView) rootView.findViewById(R.id.list_img);
		new MyTask().execute();
		return rootView;
	}
	
	





	class MyTask extends AsyncTask<Void, Void, MyMessage> {

		@Override
		protected MyMessage doInBackground(Void... params) {
			String json = "{\"code\":200,\"msg\":\"ok\",list:["
					+ "{\"id\":110,\"avator\":\"http://img0.bdstatic.com/img/image/shouye/leimu/mingxing.jpg\",\"name\":\"张三\",\"content\":\"大家好\",\"urls\":[]},"
					+ "{\"id\":111,\"avator\":\"http://img0.bdstatic.com/img/image/shouye/leimu/mingxing2.jpg\",\"name\":\"李四\",\"content\":\"大家好\",\"urls\":[\"http://d.hiphotos.baidu.com/album/w%3D2048/sign=14b0934b78310a55c424d9f4837d42a9/a8014c086e061d95e9fd3e807af40ad163d9cacb.jpg\"]},"
					+ "{\"id\":112,\"avator\":\"http://img0.bdstatic.com/img/image/shouye/leimu/mingxing1.jpg\",\"name\":\"王五\",\"content\":\"大家好\",\"urls\":[\"http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=ccd33b46d53f8794d7ff4b26e2207fc9/0d338744ebf81a4c0f993437d62a6059242da6a1.jpg\",\"http://c.hiphotos.bdimg.com/album/s%3D900%3Bq%3D90/sign=b8658f17f3d3572c62e290dcba28121a/5fdf8db1cb134954bb97309a574e9258d0094a47.jpg\"]},"
					+ "{\"id\":113,\"avator\":\"http://img0.bdstatic.com/img/image/shouye/leimu/mingxing6.jpg\",\"name\":\"赵六\",\"content\":\"大家好\",\"urls\":[\"http://f.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=6b62f61bac6eddc422e7b7f309e0c7c0/6159252dd42a2834510deef55ab5c9ea14cebfa1.jpg\",\"http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=e58fb67bc8ea15ce45eee301863b4bce/a5c27d1ed21b0ef4fd6140a0dcc451da80cb3e47.jpg\",\"http://c.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=cdab1512d000baa1be2c44b3772bc82f/91529822720e0cf3855c96050b46f21fbf09aaa1.jpg\",\"http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=ccd33b46d53f8794d7ff4b26e2207fc9/0d338744ebf81a4c0f993437d62a6059242da6a1.jpg\",\"http://c.hiphotos.bdimg.com/album/s%3D900%3Bq%3D90/sign=b8658f17f3d3572c62e290dcba28121a/5fdf8db1cb134954bb97309a574e9258d0094a47.jpg\"]}]}";
			Gson gson = new Gson();
			MyMessage msg = gson.fromJson(json, MyMessage.class);
			return msg;
		}

		@Override
		protected void onPostExecute(MyMessage result) {
			mAdapter = new MyListAdapter(mContext, result.list);
			mLvImg.setAdapter(mAdapter);
		}

	}
}
