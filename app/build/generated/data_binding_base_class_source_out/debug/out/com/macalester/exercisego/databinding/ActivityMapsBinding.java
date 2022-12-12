// Generated by view binder compiler. Do not edit!
package com.macalester.exercisego.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.macalester.exercisego.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMapsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RecyclerView rvNearbyParks;

  @NonNull
  public final TextView tvParksTitle;

  private ActivityMapsBinding(@NonNull LinearLayout rootView, @NonNull RecyclerView rvNearbyParks,
      @NonNull TextView tvParksTitle) {
    this.rootView = rootView;
    this.rvNearbyParks = rvNearbyParks;
    this.tvParksTitle = tvParksTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMapsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMapsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_maps, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMapsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.rvNearbyParks;
      RecyclerView rvNearbyParks = ViewBindings.findChildViewById(rootView, id);
      if (rvNearbyParks == null) {
        break missingId;
      }

      id = R.id.tvParksTitle;
      TextView tvParksTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvParksTitle == null) {
        break missingId;
      }

      return new ActivityMapsBinding((LinearLayout) rootView, rvNearbyParks, tvParksTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
