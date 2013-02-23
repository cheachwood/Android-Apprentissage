package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class ClientOzEvent extends GenericJson
{
  public ClientActionData actionData;
  public Long clientTimeMsec;
  public ClientOutputData endViewData;
  public OzEvent ozEvent;
  public ClientOutputData startViewData;
  public ClientViewerInfo viewerInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientOzEvent
 * JD-Core Version:    0.6.2
 */