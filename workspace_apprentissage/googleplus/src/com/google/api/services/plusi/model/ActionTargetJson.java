package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ActionTargetJson extends EsJson<ActionTarget>
{
  static final ActionTargetJson INSTANCE = new ActionTargetJson();

  private ActionTargetJson()
  {
    super(ActionTarget.class, arrayOfObject);
  }

  public static ActionTargetJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActionTargetJson
 * JD-Core Version:    0.6.2
 */