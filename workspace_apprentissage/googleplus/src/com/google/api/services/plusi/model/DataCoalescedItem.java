package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataCoalescedItem extends GenericJson
{
  public List<A2aData> a2aData;
  public List<DataAction> action;
  public String category;
  public String coalescingCode;
  public String debug;
  public String defaultUrl;
  public EntityEntityData entityData;
  public String entityReference;
  public String entityReferenceType;
  public String filterType;
  public Integer hashCode;
  public String id;
  public List<String> involvedActorOid;
  public Boolean isEntityDeleted;
  public Boolean isMuted;
  public Boolean isRead;
  public List<DataKvPair> opaqueClientFields;
  public Boolean pushEnabled;
  public Double timestamp;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCoalescedItem
 * JD-Core Version:    0.6.2
 */