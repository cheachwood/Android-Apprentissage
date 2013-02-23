package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BlockUserResponseJson extends EsJson<BlockUserResponse>
{
  static final BlockUserResponseJson INSTANCE = new BlockUserResponseJson();

  private BlockUserResponseJson()
  {
    super(BlockUserResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "versionInfo" });
  }

  public static BlockUserResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BlockUserResponseJson
 * JD-Core Version:    0.6.2
 */