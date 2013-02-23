package com.google.android.gms.internal;

import android.os.Bundle;

public final class av
{
  private final Bundle bM;

  public av(Bundle paramBundle)
  {
    this.bM = paramBundle;
  }

  public final String F()
  {
    return this.bM.getString("profile_image_url");
  }

  public final String getDisplayName()
  {
    return this.bM.getString("display_name");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.av
 * JD-Core Version:    0.6.2
 */