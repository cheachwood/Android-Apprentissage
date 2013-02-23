package com.google.android.gms.internal;

import android.os.Bundle;

public final class bk
{
  Bundle bM;

  public bk(Bundle paramBundle)
  {
    this.bM = paramBundle;
  }

  public final boolean ah()
  {
    return this.bM.getBoolean("has_plus_one", false);
  }

  public final String am()
  {
    return this.bM.getString("confirmation_message");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bk
 * JD-Core Version:    0.6.2
 */