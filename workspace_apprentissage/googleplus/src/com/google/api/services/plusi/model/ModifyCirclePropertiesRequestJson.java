package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ModifyCirclePropertiesRequestJson extends EsJson<ModifyCirclePropertiesRequest>
{
  static final ModifyCirclePropertiesRequestJson INSTANCE = new ModifyCirclePropertiesRequestJson();

  private ModifyCirclePropertiesRequestJson()
  {
    super(ModifyCirclePropertiesRequest.class, new Object[] { "circleId", ApiaryFieldsJson.class, "commonFields", "description", "enableTracing", "justFollowing", "name", "photoId", "visible" });
  }

  public static ModifyCirclePropertiesRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ModifyCirclePropertiesRequestJson
 * JD-Core Version:    0.6.2
 */