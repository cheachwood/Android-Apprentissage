package com.android.volley;

public final class DefaultRetryPolicy
  implements RetryPolicy
{
  private final float mBackoffMultiplier = 1.0F;
  private int mCurrentRetryCount;
  private int mCurrentTimeoutMs = 2500;
  private final int mMaxNumRetries = 1;

  public DefaultRetryPolicy()
  {
    this(2500, 1, 1.0F);
  }

  private DefaultRetryPolicy(int paramInt1, int paramInt2, float paramFloat)
  {
  }

  public final int getCurrentRetryCount()
  {
    return this.mCurrentRetryCount;
  }

  public final int getCurrentTimeout()
  {
    return this.mCurrentTimeoutMs;
  }

  public final void retry(VolleyError paramVolleyError)
    throws VolleyError
  {
    this.mCurrentRetryCount = (1 + this.mCurrentRetryCount);
    this.mCurrentTimeoutMs = ((int)(this.mCurrentTimeoutMs + this.mCurrentTimeoutMs * this.mBackoffMultiplier));
    if (this.mCurrentRetryCount <= this.mMaxNumRetries);
    for (int i = 1; i == 0; i = 0)
      throw paramVolleyError;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.DefaultRetryPolicy
 * JD-Core Version:    0.6.2
 */