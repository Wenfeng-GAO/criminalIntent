package com.wenfeng.officecrime;

import java.io.IOException;
import java.util.List;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {
	private static final String TAG = CrimeCameraFragment.class.getSimpleName();
	
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);
		Button buttonTakePhoto = (Button) view.findViewById(R.id.button_fragment_crime_camera);
		buttonTakePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getActivity().finish();
			}
		});
		mSurfaceView = (SurfaceView) view.findViewById(R.id.surfaceView_button_fragment_crime_camera);
		SurfaceHolder holder = mSurfaceView.getHolder();
		// setType() and SURFACE_TYPE_PUSH_BUFFERS are both deprecated,
		// but are required for Camera preview to work on pre-3.0 devices
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// We can do no longer display on this surface, so stop the preview
				if (mCamera != null) {
					mCamera.stopPreview();
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// Tell the camera to use this surface as its preview area
				try {
					if (mCamera != null) {
						mCamera.setPreviewDisplay(holder);
					}
				} catch (IOException e) {
					Log.e(TAG, "Error setting up preview display", e);
				}				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				if (mCamera == null) return;
				
				// The surface has changed size, update the camera preview size
				Camera.Parameters parameters = mCamera.getParameters();
				Size size = getBestSupportedSize(parameters.getSupportedPreviewSizes());
				parameters.setPreviewSize(size.width, size.height);
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					Log.e(TAG, "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				}
			}
		});
		return view;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCamera = Camera.open(0);
		} else {
			mCamera = Camera.open();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
	
	/* A simple algorithm to get the largest size available. For a more robust version,
	 * see CameraPreview.java in the ApiDemos sample app from Android,
	 */
	
	private Size getBestSupportedSize(List<Size> sizes) {
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size size : sizes) {
			int area = size.width * size.height;
			if (area > largestArea) {
				bestSize = size;
				largestArea = area;
			}
		}
		return bestSize;
	}
	
}
