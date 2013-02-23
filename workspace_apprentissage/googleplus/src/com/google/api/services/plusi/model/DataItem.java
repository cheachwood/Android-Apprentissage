package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataItem extends GenericJson
{
  public DataActor actor;
  public String actorOid;
  public String id;
  public Boolean isRead;
  public String notificationType;
  public List<DataKvPair> opaqueClientFields;
  public Double timestamp;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataItem
 * JD-Core Version:    0.6.2
 */