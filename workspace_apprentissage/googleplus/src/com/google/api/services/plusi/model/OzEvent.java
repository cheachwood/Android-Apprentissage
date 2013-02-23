package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class OzEvent extends GenericJson
{
  public ActionTarget actionTarget;
  public Boolean background;
  public Boolean badTiming;
  public OutputData endViewData;
  public FavaDiagnostics favaDiagnostics;
  public InputData inputData;
  public InterstitialRedirectorData interstitialRedirectorData;
  public Boolean isNativePlatformEvent;
  public Integer overallUserSegment;
  public OutputData startViewData;
  public Long timestampUsecDelta;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OzEvent
 * JD-Core Version:    0.6.2
 */