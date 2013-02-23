package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class GetMobileExperimentsResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public List<GetMobileExperimentsResponseExperiment> experiment;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileExperimentsResponse
 * JD-Core Version:    0.6.2
 */