package com.liaofan_01.fragments;

import com.example.liaofan_01.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPageFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_mypage, container, false);
		return view;
	}
}
