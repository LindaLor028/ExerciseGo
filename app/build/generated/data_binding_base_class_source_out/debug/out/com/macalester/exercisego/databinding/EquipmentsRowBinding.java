// Generated by view binder compiler. Do not edit!
package com.macalester.exercisego.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.macalester.exercisego.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EquipmentsRowBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imgBars;

  @NonNull
  public final ImageView imgBench;

  @NonNull
  public final ImageView imgCycle;

  @NonNull
  public final ImageView imgPress;

  @NonNull
  public final ImageView imgTwist;

  @NonNull
  public final ImageView imgWalker;

  @NonNull
  public final TextView tvBars;

  @NonNull
  public final TextView tvBenches;

  @NonNull
  public final TextView tvCycles;

  @NonNull
  public final TextView tvPresses;

  @NonNull
  public final TextView tvTwisters;

  @NonNull
  public final TextView tvWalkers;

  private EquipmentsRowBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imgBars,
      @NonNull ImageView imgBench, @NonNull ImageView imgCycle, @NonNull ImageView imgPress,
      @NonNull ImageView imgTwist, @NonNull ImageView imgWalker, @NonNull TextView tvBars,
      @NonNull TextView tvBenches, @NonNull TextView tvCycles, @NonNull TextView tvPresses,
      @NonNull TextView tvTwisters, @NonNull TextView tvWalkers) {
    this.rootView = rootView;
    this.imgBars = imgBars;
    this.imgBench = imgBench;
    this.imgCycle = imgCycle;
    this.imgPress = imgPress;
    this.imgTwist = imgTwist;
    this.imgWalker = imgWalker;
    this.tvBars = tvBars;
    this.tvBenches = tvBenches;
    this.tvCycles = tvCycles;
    this.tvPresses = tvPresses;
    this.tvTwisters = tvTwisters;
    this.tvWalkers = tvWalkers;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static EquipmentsRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EquipmentsRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.equipments_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EquipmentsRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgBars;
      ImageView imgBars = ViewBindings.findChildViewById(rootView, id);
      if (imgBars == null) {
        break missingId;
      }

      id = R.id.imgBench;
      ImageView imgBench = ViewBindings.findChildViewById(rootView, id);
      if (imgBench == null) {
        break missingId;
      }

      id = R.id.imgCycle;
      ImageView imgCycle = ViewBindings.findChildViewById(rootView, id);
      if (imgCycle == null) {
        break missingId;
      }

      id = R.id.imgPress;
      ImageView imgPress = ViewBindings.findChildViewById(rootView, id);
      if (imgPress == null) {
        break missingId;
      }

      id = R.id.imgTwist;
      ImageView imgTwist = ViewBindings.findChildViewById(rootView, id);
      if (imgTwist == null) {
        break missingId;
      }

      id = R.id.imgWalker;
      ImageView imgWalker = ViewBindings.findChildViewById(rootView, id);
      if (imgWalker == null) {
        break missingId;
      }

      id = R.id.tvBars;
      TextView tvBars = ViewBindings.findChildViewById(rootView, id);
      if (tvBars == null) {
        break missingId;
      }

      id = R.id.tvBenches;
      TextView tvBenches = ViewBindings.findChildViewById(rootView, id);
      if (tvBenches == null) {
        break missingId;
      }

      id = R.id.tvCycles;
      TextView tvCycles = ViewBindings.findChildViewById(rootView, id);
      if (tvCycles == null) {
        break missingId;
      }

      id = R.id.tvPresses;
      TextView tvPresses = ViewBindings.findChildViewById(rootView, id);
      if (tvPresses == null) {
        break missingId;
      }

      id = R.id.tvTwisters;
      TextView tvTwisters = ViewBindings.findChildViewById(rootView, id);
      if (tvTwisters == null) {
        break missingId;
      }

      id = R.id.tvWalkers;
      TextView tvWalkers = ViewBindings.findChildViewById(rootView, id);
      if (tvWalkers == null) {
        break missingId;
      }

      return new EquipmentsRowBinding((ConstraintLayout) rootView, imgBars, imgBench, imgCycle,
          imgPress, imgTwist, imgWalker, tvBars, tvBenches, tvCycles, tvPresses, tvTwisters,
          tvWalkers);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
