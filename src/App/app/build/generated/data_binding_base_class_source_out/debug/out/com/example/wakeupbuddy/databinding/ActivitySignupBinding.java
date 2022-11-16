// Generated by view binder compiler. Do not edit!
package com.example.wakeupbuddy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.wakeupbuddy.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btnSignup;

  @NonNull
  public final TextInputEditText etConfirmPassword;

  @NonNull
  public final TextInputEditText etEmail;

  @NonNull
  public final TextInputEditText etName;

  @NonNull
  public final TextInputEditText etPassword;

  @NonNull
  public final TextInputEditText etUsername;

  @NonNull
  public final TextView haveAcc;

  @NonNull
  public final TextView signupTitle;

  @NonNull
  public final TextInputLayout tilConfirmPassword;

  @NonNull
  public final TextInputLayout tilEmail;

  @NonNull
  public final TextInputLayout tilName;

  @NonNull
  public final TextInputLayout tilPassword;

  @NonNull
  public final TextInputLayout tilUsername;

  private ActivitySignupBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btnSignup, @NonNull TextInputEditText etConfirmPassword,
      @NonNull TextInputEditText etEmail, @NonNull TextInputEditText etName,
      @NonNull TextInputEditText etPassword, @NonNull TextInputEditText etUsername,
      @NonNull TextView haveAcc, @NonNull TextView signupTitle,
      @NonNull TextInputLayout tilConfirmPassword, @NonNull TextInputLayout tilEmail,
      @NonNull TextInputLayout tilName, @NonNull TextInputLayout tilPassword,
      @NonNull TextInputLayout tilUsername) {
    this.rootView = rootView;
    this.btnSignup = btnSignup;
    this.etConfirmPassword = etConfirmPassword;
    this.etEmail = etEmail;
    this.etName = etName;
    this.etPassword = etPassword;
    this.etUsername = etUsername;
    this.haveAcc = haveAcc;
    this.signupTitle = signupTitle;
    this.tilConfirmPassword = tilConfirmPassword;
    this.tilEmail = tilEmail;
    this.tilName = tilName;
    this.tilPassword = tilPassword;
    this.tilUsername = tilUsername;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_signup;
      AppCompatButton btnSignup = ViewBindings.findChildViewById(rootView, id);
      if (btnSignup == null) {
        break missingId;
      }

      id = R.id.et_confirm_password;
      TextInputEditText etConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (etConfirmPassword == null) {
        break missingId;
      }

      id = R.id.et_email;
      TextInputEditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.et_name;
      TextInputEditText etName = ViewBindings.findChildViewById(rootView, id);
      if (etName == null) {
        break missingId;
      }

      id = R.id.et_password;
      TextInputEditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.et_username;
      TextInputEditText etUsername = ViewBindings.findChildViewById(rootView, id);
      if (etUsername == null) {
        break missingId;
      }

      id = R.id.have_acc;
      TextView haveAcc = ViewBindings.findChildViewById(rootView, id);
      if (haveAcc == null) {
        break missingId;
      }

      id = R.id.signup_title;
      TextView signupTitle = ViewBindings.findChildViewById(rootView, id);
      if (signupTitle == null) {
        break missingId;
      }

      id = R.id.til_confirm_password;
      TextInputLayout tilConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (tilConfirmPassword == null) {
        break missingId;
      }

      id = R.id.til_email;
      TextInputLayout tilEmail = ViewBindings.findChildViewById(rootView, id);
      if (tilEmail == null) {
        break missingId;
      }

      id = R.id.til_name;
      TextInputLayout tilName = ViewBindings.findChildViewById(rootView, id);
      if (tilName == null) {
        break missingId;
      }

      id = R.id.til_password;
      TextInputLayout tilPassword = ViewBindings.findChildViewById(rootView, id);
      if (tilPassword == null) {
        break missingId;
      }

      id = R.id.til_username;
      TextInputLayout tilUsername = ViewBindings.findChildViewById(rootView, id);
      if (tilUsername == null) {
        break missingId;
      }

      return new ActivitySignupBinding((ConstraintLayout) rootView, btnSignup, etConfirmPassword,
          etEmail, etName, etPassword, etUsername, haveAcc, signupTitle, tilConfirmPassword,
          tilEmail, tilName, tilPassword, tilUsername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}