package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BlockUserRequestJson extends EsJson<BlockUserRequest>
{
  static final BlockUserRequestJson INSTANCE = new BlockUserRequestJson();

  private BlockUserRequestJson()
  {
    super(BlockUserRequest.class, new Object[] { DataAbuseReportJson.class, "abuseReport", ApiaryFieldsJson.class, "commonFields", "enableTracing", "ignore", DataMembersToBlockJson.class, "membersToBlock" });
  }

  public static BlockUserRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BlockUserRequestJson
 * JD-Core Version:    0.6.2
 */