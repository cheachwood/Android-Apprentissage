package com.google.android.gms.plus;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.gms.internal.e;

public final class PlusSignInButton extends ImageView
{
  static final Uri cn = e.a("client_sign_in");
  static final Uri co = e.a("client_sign_in_w");
  private int cp = 0;

  public PlusSignInButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public PlusSignInButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 16842866);
    setPadding(0, 0, 0, 0);
    ColorDrawable localColorDrawable = new ColorDrawable();
    localColorDrawable.setAlpha(0);
    setBackgroundDrawable(localColorDrawable);
    L();
  }

  private void L()
  {
    switch (this.cp)
    {
    default:
      throw new IllegalStateException();
    case 0:
    case 1:
    }
    for (Uri localUri = cn; ; localUri = co)
    {
      setImageURI(localUri);
      return;
    }
  }

  protected final void drawableStateChanged()
  {
    super.drawableStateChanged();
    if (isPressed());
    for (int i = e.i; ; i = 0)
    {
      setColorFilter(i);
      return;
    }
  }

  public final void setSize(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < 2));
    for (int i = 1; i == 0; i = 0)
      throw new IllegalStateException(String.valueOf("Invalid button type."));
    this.cp = paramInt;
    L();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.plus.PlusSignInButton
 * JD-Core Version:    0.6.2
 */