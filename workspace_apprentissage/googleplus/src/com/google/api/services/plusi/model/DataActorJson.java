package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataActorJson extends EsJson<DataActor>
{
  static final DataActorJson INSTANCE = new DataActorJson();

  private DataActorJson()
  {
    super(DataActor.class, new Object[] { "gender", "name", "obfuscatedGaiaId", "photoUrl", "profileType", "profileUrl" });
  }

  public static DataActorJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataActorJson
 * JD-Core Version:    0.6.2
 */