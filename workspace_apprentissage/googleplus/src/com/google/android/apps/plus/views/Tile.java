package com.google.android.apps.plus.views;

import android.os.Bundle;

public abstract interface Tile
{
  public abstract void onCreate(Bundle paramBundle);

  public abstract void onPause();

  public abstract void onResume();

  public abstract void onSaveInstanceState(Bundle paramBundle);

  public abstract void onStart();

  public abstract void onStop();

  public abstract void onTilePause();

  public abstract void onTileResume();

  public abstract void onTileStart();

  public abstract void onTileStop();

  public abstract void setVisibility(int paramInt);

  public static abstract interface ParticipantPresenceListener
  {
    public abstract void onParticipantPresenceChanged();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.Tile
 * JD-Core Version:    0.6.2
 */