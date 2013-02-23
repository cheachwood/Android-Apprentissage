package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GetActivityRequest extends GenericJson
{
  public String activityId;
  public Long activityMaxResharers;
  public ApiaryFields commonFields;
  public GetActivitiesCommonParams commonParams;
  public String deprecatedRenderContextLocation;
  public ClientEmbedOptions embedOptions;
  public Boolean enableTracing;
  public Boolean fetchReadState;
  public String ownerId;
  public RenderContext renderContext;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetActivityRequest
 * JD-Core Version:    0.6.2
 */