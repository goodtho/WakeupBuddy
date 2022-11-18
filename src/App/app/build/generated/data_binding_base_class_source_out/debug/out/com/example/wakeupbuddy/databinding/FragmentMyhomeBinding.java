// Generated by view binder compiler. Do not edit!
package com.example.wakeupbuddy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.wakeupbuddy.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMyhomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonHomeAppliance1;

  @NonNull
  public final Button buttonHomeAppliance2;

  @NonNull
  public final Button buttonHomeAppliance3;

  @NonNull
  public final Button buttonHomeAppliance4;

  @NonNull
  public final ImageView clock;

  @NonNull
  public final ImageView containerHomeAppliance1;

  @NonNull
  public final ImageView containerHomeAppliance2;

  @NonNull
  public final TextView headerHomeDevices;

  @NonNull
  public final ImageButton imageButton;

  @NonNull
  public final TextView textFavorites;

  @NonNull
  public final TextView textMyDevices;

  @NonNull
  public final TextView timeLeftCorner;

  private FragmentMyhomeBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonHomeAppliance1, @NonNull Button buttonHomeAppliance2,
      @NonNull Button buttonHomeAppliance3, @NonNull Button buttonHomeAppliance4,
      @NonNull ImageView clock, @NonNull ImageView containerHomeAppliance1,
      @NonNull ImageView containerHomeAppliance2, @NonNull TextView headerHomeDevices,
      @NonNull ImageButton imageButton, @NonNull TextView textFavorites,
      @NonNull TextView textMyDevices, @NonNull TextView timeLeftCorner) {
    this.rootView = rootView;
    this.buttonHomeAppliance1 = buttonHomeAppliance1;
    this.buttonHomeAppliance2 = buttonHomeAppliance2;
    this.buttonHomeAppliance3 = buttonHomeAppliance3;
    this.buttonHomeAppliance4 = buttonHomeAppliance4;
    this.clock = clock;
    this.containerHomeAppliance1 = containerHomeAppliance1;
    this.containerHomeAppliance2 = containerHomeAppliance2;
    this.headerHomeDevices = headerHomeDevices;
    this.imageButton = imageButton;
    this.textFavorites = textFavorites;
    this.textMyDevices = textMyDevices;
    this.timeLeftCorner = timeLeftCorner;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMyhomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMyhomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_myhome, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMyhomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonHomeAppliance1;
      Button buttonHomeAppliance1 = ViewBindings.findChildViewById(rootView, id);
      if (buttonHomeAppliance1 == null) {
        break missingId;
      }

      id = R.id.buttonHomeAppliance2;
      Button buttonHomeAppliance2 = ViewBindings.findChildViewById(rootView, id);
      if (buttonHomeAppliance2 == null) {
        break missingId;
      }

      id = R.id.buttonHomeAppliance3;
      Button buttonHomeAppliance3 = ViewBindings.findChildViewById(rootView, id);
      if (buttonHomeAppliance3 == null) {
        break missingId;
      }

      id = R.id.buttonHomeAppliance4;
      Button buttonHomeAppliance4 = ViewBindings.findChildViewById(rootView, id);
      if (buttonHomeAppliance4 == null) {
        break missingId;
      }

      id = R.id.clock;
      ImageView clock = ViewBindings.findChildViewById(rootView, id);
      if (clock == null) {
        break missingId;
      }

      id = R.id.containerHomeAppliance1;
      ImageView containerHomeAppliance1 = ViewBindings.findChildViewById(rootView, id);
      if (containerHomeAppliance1 == null) {
        break missingId;
      }

      id = R.id.containerHomeAppliance2;
      ImageView containerHomeAppliance2 = ViewBindings.findChildViewById(rootView, id);
      if (containerHomeAppliance2 == null) {
        break missingId;
      }

      id = R.id.headerHomeDevices;
      TextView headerHomeDevices = ViewBindings.findChildViewById(rootView, id);
      if (headerHomeDevices == null) {
        break missingId;
      }

      id = R.id.imageButton;
      ImageButton imageButton = ViewBindings.findChildViewById(rootView, id);
      if (imageButton == null) {
        break missingId;
      }

      id = R.id.textFavorites;
      TextView textFavorites = ViewBindings.findChildViewById(rootView, id);
      if (textFavorites == null) {
        break missingId;
      }

      id = R.id.textMyDevices;
      TextView textMyDevices = ViewBindings.findChildViewById(rootView, id);
      if (textMyDevices == null) {
        break missingId;
      }

      id = R.id.timeLeftCorner;
      TextView timeLeftCorner = ViewBindings.findChildViewById(rootView, id);
      if (timeLeftCorner == null) {
        break missingId;
      }

      return new FragmentMyhomeBinding((ConstraintLayout) rootView, buttonHomeAppliance1,
          buttonHomeAppliance2, buttonHomeAppliance3, buttonHomeAppliance4, clock,
          containerHomeAppliance1, containerHomeAppliance2, headerHomeDevices, imageButton,
          textFavorites, textMyDevices, timeLeftCorner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
