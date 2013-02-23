package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GetActivitiesRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public String continuesToken;
  public ClientEmbedOptions embedOptions;
  public Boolean enableTracing;
  public Boolean isUserInitiated;
  public String ownerId;
  public String perspectiveId;
  public Boolean skipPopularMixin;
  public StreamParams streamParams;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetActivitiesRequest
 * JD-Core Version:    0.6.2
 */