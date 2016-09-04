package com.crhto.loginBackendless.Helpers;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import com.crhto.loginBackendless.R;

/**
 * Provides static methods for different value validators.
 * Shows Toasts with warnings if validation fails.
 */
public class ValidatorHelper
{

  /**
   * Validates email: checks whether it is not empty and whether it matches Patterns.EMAIL_ADDRESS regex.
   * Shows Toast with a warning if validation fails.
   *
   * @param currentContext context, in which validation occurs
   * @param email          email to be validated
   * @return true if email is valid, false if validation failed
   */
  public static boolean isEmailValid( Context currentContext, CharSequence email )
  {
    if( email.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_email_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    if( !Patterns.EMAIL_ADDRESS.matcher( email ).matches() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_email_invalid ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }

  /**
   * Validates password: checks whether it is not empty.
   * Shows Toast with a warning if validation fails.
   *
   * @param currentContext context, in which validation occurs
   * @param password       password to be validated
   * @return true if password is valid, false if validation failed
   */
  public static boolean isPasswordValid( Context currentContext, CharSequence password )
  {
    if( password.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_password_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }
}
