package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class FavaDiagnostics extends GenericJson
{
  public Integer actionNumber;
  public FavaDiagnosticsNamespacedType actionType;
  public FavaDiagnosticsNamespacedType endView;
  public List<String> errorCode;
  public List<Integer> experimentIds;
  public Boolean isCacheHit;
  public Integer jsLoadTimeMs;
  public String jsVersion;
  public Long mainPageId;
  public FavaDiagnosticsMemoryStats memoryStats;
  public Integer numLogicalRequests;
  public Integer numRequests;
  public List<Long> requestId;
  public List<FavaDiagnosticsRequestStat> requestStats;
  public Boolean requiredJsLoad;
  public Integer screenHeight;
  public Integer screenWidth;
  public FavaDiagnosticsNamespacedType startView;
  public String status;
  public List<Integer> timeMs;
  public Long timeUsecDelta;
  public Integer totalTimeMs;
  public String tracers;
  public Integer viewportHeight;
  public Integer viewportWidth;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FavaDiagnostics
 * JD-Core Version:    0.6.2
 */