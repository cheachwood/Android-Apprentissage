package com.google.android.apps.plus.views;

import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.DbPlusOneData;
import java.util.List;

public abstract interface OneUpListener extends ClickableStaticLayout.SpanClickListener, ClickableUserImage.UserImageClickListener
{
  public abstract void onLocationClick$75c560e7(DbLocation paramDbLocation);

  public abstract void onPlaceClick(String paramString);

  public abstract void onPlusOne(String paramString, DbPlusOneData paramDbPlusOneData);

  public abstract void onSkyjamBuyClick(String paramString);

  public abstract void onSkyjamListenClick(String paramString);

  public abstract void onSourceAppContentClick(String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4);

  public abstract void onSquareClick(String paramString1, String paramString2);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.OneUpListener
 * JD-Core Version:    0.6.2
 */