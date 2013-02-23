package com.google.android.apps.plus.views;

public abstract interface EventActionListener
{
  public abstract void onAddPhotosClicked();

  public abstract void onAvatarClicked(String paramString);

  public abstract void onExpansionToggled(boolean paramBoolean);

  public abstract void onHangoutClicked();

  public abstract void onInstantShareToggle(boolean paramBoolean);

  public abstract void onInviteMoreClicked();

  public abstract void onLinkClicked(String paramString);

  public abstract void onLocationClicked();

  public abstract void onPhotoClicked(String paramString1, String paramString2, String paramString3);

  public abstract void onPhotoUpdateNeeded(String paramString1, String paramString2, String paramString3);

  public abstract void onRsvpChanged(String paramString);

  public abstract void onUpdateCardClicked(EventUpdate paramEventUpdate);

  public abstract void onViewAllInviteesClicked();
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventActionListener
 * JD-Core Version:    0.6.2
 */