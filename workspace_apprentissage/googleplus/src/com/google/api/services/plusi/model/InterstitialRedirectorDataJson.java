package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InterstitialRedirectorDataJson extends EsJson<InterstitialRedirectorData>
{
  static final InterstitialRedirectorDataJson INSTANCE = new InterstitialRedirectorDataJson();

  private InterstitialRedirectorDataJson()
  {
    super(InterstitialRedirectorData.class, new Object[] { "birthdayCompleted", "decision", "hasProfilePhoto", "hasSeenRecently", "subscribeCompleted" });
  }

  public static InterstitialRedirectorDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InterstitialRedirectorDataJson
 * JD-Core Version:    0.6.2
 */