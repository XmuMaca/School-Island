/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wxm.com.androiddesign.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import wxm.com.androiddesign.R;

public class ExpressionAdapter extends ArrayAdapter<String>{

	public ExpressionAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = View.inflate(getContext(), R.layout.row_expression, null);
		}
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_expression);
		
		String filename = getItem(position);
		Log.d("filename",filename);
		Log.d("packagename", getContext().getPackageName());
		int resId = getContext().getResources().getIdentifier(filename, "drawable", getContext().getPackageName());
		Log.d("resId",resId+"");
        imageView.setImageResource(resId);
		//imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.+""));
		return convertView;
	}
	
}
