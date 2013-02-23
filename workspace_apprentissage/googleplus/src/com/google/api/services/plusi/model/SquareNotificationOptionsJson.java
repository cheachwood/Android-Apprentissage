package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareNotificationOptionsJson extends EsJson<SquareNotificationOptions>
{
  static final SquareNotificationOptionsJson INSTANCE = new SquareNotificationOptionsJson();

  private SquareNotificationOptionsJson()
  {
    super(SquareNotificationOptions.class, new Object[] { "autoSubscribeOnJoin", "disableSubscription" });
  }

  public static SquareNotificationOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareNotificationOptionsJson
 * JD-Core Version:    0.6.2
 */