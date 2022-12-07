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

public final class ReviewRowBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final TextView tvRatings;

  @NonNull
  public final TextView tvReviewPreview;

  @NonNull
  public final TextView tvUsername;

  private ReviewRowBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imageView3,
      @NonNull ImageView imageView4, @NonNull TextView tvRatings, @NonNull TextView tvReviewPreview,
      @NonNull TextView tvUsername) {
    this.rootView = rootView;
    this.imageView3 = imageView3;
    this.imageView4 = imageView4;
    this.tvRatings = tvRatings;
    this.tvReviewPreview = tvReviewPreview;
    this.tvUsername = tvUsername;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ReviewRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ReviewRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.review_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ReviewRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.tvRatings;
      TextView tvRatings = ViewBindings.findChildViewById(rootView, id);
      if (tvRatings == null) {
        break missingId;
      }

      id = R.id.tvReviewPreview;
      TextView tvReviewPreview = ViewBindings.findChildViewById(rootView, id);
      if (tvReviewPreview == null) {
        break missingId;
      }

      id = R.id.tvUsername;
      TextView tvUsername = ViewBindings.findChildViewById(rootView, id);
      if (tvUsername == null) {
        break missingId;
      }

      return new ReviewRowBinding((ConstraintLayout) rootView, imageView3, imageView4, tvRatings,
          tvReviewPreview, tvUsername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}