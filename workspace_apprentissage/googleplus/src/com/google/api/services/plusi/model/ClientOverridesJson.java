package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientOverridesJson extends EsJson<ClientOverrides>
{
  static final ClientOverridesJson INSTANCE = new ClientOverridesJson();

  private ClientOverridesJson()
  {
    super(ClientOverrides.class, new Object[] { CompositeStoryRequestProtoJson.class, "compositeStory" });
  }

  public static ClientOverridesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientOverridesJson
 * JD-Core Version:    0.6.2
 */