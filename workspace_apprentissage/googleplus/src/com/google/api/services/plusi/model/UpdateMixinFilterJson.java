package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateMixinFilterJson extends EsJson<UpdateMixinFilter>
{
  static final UpdateMixinFilterJson INSTANCE = new UpdateMixinFilterJson();

  private UpdateMixinFilterJson()
  {
    super(UpdateMixinFilter.class, new Object[] { "mixinType" });
  }

  public static UpdateMixinFilterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateMixinFilterJson
 * JD-Core Version:    0.6.2
 */