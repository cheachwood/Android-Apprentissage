package com.google.android.apps.plus.fragments;

import com.google.api.services.plusi.model.SquareResult;
import java.util.ArrayList;
import java.util.List;

public final class SquareSearchLoaderResults
{
  private final String mNextToken;
  private final List<SquareResult> mResults;
  private final String mToken;

  public SquareSearchLoaderResults()
  {
    this.mToken = null;
    this.mNextToken = null;
    this.mResults = new ArrayList();
  }

  public SquareSearchLoaderResults(String paramString1, String paramString2, List<SquareResult> paramList)
  {
    this.mToken = paramString1;
    this.mNextToken = paramString2;
    if (paramList != null);
    while (true)
    {
      this.mResults = paramList;
      return;
      paramList = new ArrayList();
    }
  }

  public final String getNextToken()
  {
    return this.mNextToken;
  }

  public final List<SquareResult> getResults()
  {
    return this.mResults;
  }

  public final String getToken()
  {
    return this.mToken;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SquareSearchLoaderResults
 * JD-Core Version:    0.6.2
 */