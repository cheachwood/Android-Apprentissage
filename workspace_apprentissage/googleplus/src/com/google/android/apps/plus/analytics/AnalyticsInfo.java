package com.google.android.apps.plus.analytics;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AnalyticsInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private final HashMap<String, String> mCustomValues;
  private final OzViews mEndView;
  private final long mStartTime;
  private final OzViews mStartView;

  AnalyticsInfo()
  {
    this(null, null, System.currentTimeMillis(), Collections.emptyMap());
  }

  public AnalyticsInfo(OzViews paramOzViews)
  {
    this.mStartView = paramOzViews;
    this.mEndView = null;
    this.mStartTime = System.currentTimeMillis();
    this.mCustomValues = new HashMap();
  }

  AnalyticsInfo(OzViews paramOzViews1, OzViews paramOzViews2, long paramLong)
  {
    this(paramOzViews1, paramOzViews2, paramLong, Collections.emptyMap());
  }

  public AnalyticsInfo(OzViews paramOzViews1, OzViews paramOzViews2, long paramLong, Map<String, String> paramMap)
  {
    this.mStartView = paramOzViews1;
    this.mEndView = paramOzViews2;
    this.mStartTime = paramLong;
    this.mCustomValues = new HashMap(paramMap);
  }

  public final OzViews getEndView()
  {
    return this.mEndView;
  }

  public final long getStartTimeMsec()
  {
    return this.mStartTime;
  }

  public final OzViews getStartView()
  {
    return this.mStartView;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.analytics.AnalyticsInfo
 * JD-Core Version:    0.6.2
 */