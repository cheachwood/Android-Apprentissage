package com.google.android.apps.plus.views;

import android.text.style.URLSpan;

public abstract interface ItemClickListener extends ClickableStaticLayout.SpanClickListener, ClickableUserImage.UserImageClickListener
{
  public abstract void onSpanClick(URLSpan paramURLSpan);

  public abstract void onUserImageClick(String paramString1, String paramString2);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ItemClickListener
 * JD-Core Version:    0.6.2
 */