package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCoalescedItemJson extends EsJson<DataCoalescedItem>
{
  static final DataCoalescedItemJson INSTANCE = new DataCoalescedItemJson();

  private DataCoalescedItemJson()
  {
    super(DataCoalescedItem.class, new Object[] { A2aDataJson.class, "a2aData", DataActionJson.class, "action", "category", "coalescingCode", "debug", "defaultUrl", EntityEntityDataJson.class, "entityData", "entityReference", "entityReferenceType", "filterType", "hashCode", "id", "involvedActorOid", "isEntityDeleted", "isMuted", "isRead", DataKvPairJson.class, "opaqueClientFields", "pushEnabled", "timestamp" });
  }

  public static DataCoalescedItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCoalescedItemJson
 * JD-Core Version:    0.6.2
 */