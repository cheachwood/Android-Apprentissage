package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.PlusClient.b;
import com.google.android.gms.plus.PlusOneButton.OnPlusOneClickListener;

public class ad extends LinearLayout
  implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
  private static final int cy = Color.parseColor("#666666");
  protected int cA = 0;
  protected final LinearLayout cB;
  protected final FrameLayout cC;
  protected final CompoundButton cD;
  private final ProgressBar cE;
  protected final n cF;
  private final au[] cG = new au[4];
  protected int cH = 1;
  private int cI = 2;
  private int cJ = 3;
  private Uri[] cK;
  private String[] cL;
  private String[] cM;
  protected bk cO;
  protected final Resources cP;
  protected final LayoutInflater cQ;
  private a cR = new a();
  protected boolean cz;

  public ad(Context paramContext)
  {
    this(paramContext, null);
  }

  public ad(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    bj.a(paramContext, "Context must not be null.");
    String str1;
    int j;
    label100: String str2;
    int k;
    label134: Point localPoint;
    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext) != 0)
    {
      this.cP = null;
      this.cQ = null;
      str1 = be.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "size", paramContext, paramAttributeSet, true, false, "PlusOneButton");
      if (!"SMALL".equalsIgnoreCase(str1))
        break label267;
      j = 0;
      this.cJ = j;
      str2 = be.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "annotation", paramContext, paramAttributeSet, true, false, "PlusOneButton");
      if (!"INLINE".equalsIgnoreCase(str2))
        break label313;
      k = 2;
      this.cI = k;
      localPoint = new Point();
      a(localPoint);
      if (!isInEditMode())
        break label343;
      TextView localTextView = new TextView(paramContext);
      localTextView.setGravity(17);
      localTextView.setText("[ +1 ]");
      addView(localTextView, new LinearLayout.LayoutParams(localPoint.x, localPoint.y));
      this.cF = null;
      this.cE = null;
      this.cD = null;
      this.cC = null;
      this.cB = null;
    }
    while (true)
    {
      return;
      Context localContext = j$6263c3eb();
      this.cP = localContext.getResources();
      this.cQ = ((LayoutInflater)localContext.getSystemService("layout_inflater"));
      break;
      label267: if ("MEDIUM".equalsIgnoreCase(str1))
      {
        j = 1;
        break label100;
      }
      if ("TALL".equalsIgnoreCase(str1))
      {
        j = 2;
        break label100;
      }
      "STANDARD".equalsIgnoreCase(str1);
      j = 3;
      break label100;
      label313: if ("NONE".equalsIgnoreCase(str2))
      {
        k = 0;
        break label134;
      }
      "BUBBLE".equalsIgnoreCase(str2);
      k = 1;
      break label134;
      label343: setFocusable(true);
      this.cB = new LinearLayout(paramContext);
      this.cB.setGravity(17);
      this.cB.setOrientation(0);
      addView(this.cB);
      this.cD = new c(paramContext);
      this.cD.setBackgroundDrawable(null);
      n localn = new n(paramContext);
      localn.setFocusable(false);
      localn.setGravity(17);
      localn.setSingleLine();
      localn.setTextSize(0, TypedValue.applyDimension(2, b(this.cJ, this.cI), paramContext.getResources().getDisplayMetrics()));
      localn.setTextColor(cy);
      localn.setVisibility(0);
      this.cF = localn;
      FrameLayout localFrameLayout = new FrameLayout(paramContext);
      localFrameLayout.setFocusable(false);
      this.cC = localFrameLayout;
      this.cC.addView(this.cD, new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
      b(localPoint);
      ProgressBar localProgressBar = new ProgressBar(paramContext, null, 16843400);
      localProgressBar.setFocusable(false);
      localProgressBar.setIndeterminate(true);
      this.cE = localProgressBar;
      this.cE.setVisibility(4);
      this.cC.addView(this.cE, new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
      int m = this.cG.length;
      while (i < m)
      {
        au[] arrayOfau = this.cG;
        au localau = new au(getContext());
        localau.setVisibility(8);
        arrayOfau[i] = localau;
        i++;
      }
      S();
    }
  }

  private void N()
  {
    int i = 1;
    int j = (int)TypedValue.applyDimension(i, 5.0F, getContext().getResources().getDisplayMetrics());
    int k = (int)TypedValue.applyDimension(i, 1.0F, getContext().getResources().getDisplayMetrics());
    int m = this.cG.length;
    int n = 0;
    if (n < m)
    {
      LinearLayout.LayoutParams localLayoutParams;
      if (this.cG[n].getVisibility() == 0)
      {
        localLayoutParams = new LinearLayout.LayoutParams(this.cG[n].getLayoutParams());
        if (i == 0)
          break label120;
        localLayoutParams.setMargins(j, 0, k, 0);
        i = 0;
      }
      while (true)
      {
        this.cG[n].setLayoutParams(localLayoutParams);
        n++;
        break;
        label120: localLayoutParams.setMargins(k, 0, k, 0);
      }
    }
  }

  private void U()
  {
    CompoundButton localCompoundButton = this.cD;
    Drawable localDrawable;
    if (this.cP == null)
    {
      localDrawable = null;
      localCompoundButton.setButtonDrawable(localDrawable);
      switch (this.cH)
      {
      default:
        this.cD.setEnabled(false);
        this.cD.setChecked(false);
      case 0:
      case 1:
      case 2:
      }
    }
    while (true)
    {
      return;
      Resources localResources1 = this.cP;
      Resources localResources2 = this.cP;
      String str;
      switch (this.cJ)
      {
      default:
        str = "ic_plusone_standard";
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        localDrawable = localResources1.getDrawable(localResources2.getIdentifier(str, "drawable", "com.google.android.gms"));
        break;
        str = "ic_plusone_small";
        continue;
        str = "ic_plusone_medium";
        continue;
        str = "ic_plusone_tall";
      }
      this.cD.setEnabled(true);
      this.cD.setChecked(true);
      continue;
      this.cD.setEnabled(true);
      this.cD.setChecked(false);
      continue;
      this.cD.setEnabled(false);
      this.cD.setChecked(true);
    }
  }

  private void V()
  {
    switch (this.cI)
    {
    default:
      this.cF.a(null);
      this.cF.setVisibility(8);
    case 2:
    case 1:
    }
    while (true)
    {
      return;
      this.cF.a(this.cL);
      this.cF.setVisibility(0);
      continue;
      this.cF.a(this.cM);
      this.cF.setVisibility(0);
    }
  }

  private void W()
  {
    int i = 0;
    if ((this.cK != null) && (this.cI == 2))
    {
      Point localPoint = new Point();
      a(localPoint);
      localPoint.x = localPoint.y;
      int k = this.cG.length;
      int m = this.cK.length;
      int n = 0;
      if (n < k)
      {
        Uri localUri;
        if (n < m)
        {
          localUri = this.cK[n];
          label78: if (localUri != null)
            break label107;
          this.cG[n].setVisibility(8);
        }
        while (true)
        {
          n++;
          break;
          localUri = null;
          break label78;
          label107: this.cG[n].setLayoutParams(new LinearLayout.LayoutParams(localPoint.x, localPoint.y));
          this.cG[n].a(localUri, localPoint.y);
          this.cG[n].setVisibility(0);
        }
      }
    }
    else
    {
      int j = this.cG.length;
      while (i < j)
      {
        this.cG[i].setVisibility(8);
        i++;
      }
    }
    N();
  }

  private void a(int paramInt1, int paramInt2)
  {
    this.cH = paramInt2;
    this.cJ = paramInt1;
    O();
  }

  private void a(Point paramPoint)
  {
    int i = 24;
    int j = 20;
    switch (this.cJ)
    {
    default:
      int k = i;
      i = 38;
      j = k;
    case 1:
    case 0:
    case 2:
    }
    while (true)
    {
      DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
      float f1 = TypedValue.applyDimension(1, i, localDisplayMetrics);
      float f2 = TypedValue.applyDimension(1, j, localDisplayMetrics);
      paramPoint.x = ((int)(0.5D + f1));
      paramPoint.y = ((int)(0.5D + f2));
      return;
      i = 32;
      continue;
      j = 14;
      continue;
      i = 50;
    }
  }

  private static int b(int paramInt1, int paramInt2)
  {
    int i = 13;
    switch (paramInt1)
    {
    case 1:
    default:
    case 2:
    case 0:
    }
    while (true)
    {
      return i;
      if (paramInt2 != 2)
      {
        i = 15;
        continue;
        i = 11;
      }
    }
  }

  private void b(Point paramPoint)
  {
    paramPoint.y = ((int)(paramPoint.y - TypedValue.applyDimension(1, 6.0F, getResources().getDisplayMetrics())));
    paramPoint.x = paramPoint.y;
  }

  private Context j$6263c3eb()
  {
    try
    {
      Context localContext2 = getContext().createPackageContext("com.google.android.gms", 4);
      localContext1 = localContext2;
      return localContext1;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        if (Log.isLoggable("PlusOneButton", 5))
          Log.w("PlusOneButton", "Google Play services is not installed");
        Context localContext1 = null;
      }
    }
  }

  protected final void C()
  {
    if (this.cO == null);
    while (true)
    {
      return;
      this.cL = this.cO.bM.getStringArray("inline_annotations");
      V();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = this.cO.bM.getString("bubble_text");
      this.cM = arrayOfString;
      V();
      Parcelable[] arrayOfParcelable = this.cO.bM.getParcelableArray("profile_photo_uris");
      Uri[] arrayOfUri;
      if (arrayOfParcelable == null)
        arrayOfUri = null;
      while (true)
      {
        this.cK = arrayOfUri;
        W();
        if (!this.cO.ah())
          break label123;
        T();
        break;
        arrayOfUri = new Uri[arrayOfParcelable.length];
        System.arraycopy(arrayOfParcelable, 0, arrayOfUri, 0, arrayOfParcelable.length);
      }
      label123: S();
    }
  }

  protected final void O()
  {
    int i = 0;
    if (isInEditMode())
      return;
    this.cB.removeAllViews();
    Point localPoint = new Point();
    a(localPoint);
    this.cD.setLayoutParams(new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
    b(localPoint);
    this.cE.setLayoutParams(new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
    String str;
    label133: label143: LinearLayout.LayoutParams localLayoutParams;
    label193: int j;
    label204: int k;
    label222: int m;
    int n;
    if (this.cI == 1)
    {
      n localn3 = this.cF;
      switch (this.cJ)
      {
      default:
        str = "global_count_bubble_standard";
        localn3.a(e.a(str));
        W();
        n localn1 = this.cF;
        switch (this.cI)
        {
        default:
          localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
          if (this.cJ == 2)
          {
            j = 1;
            localLayoutParams.bottomMargin = j;
            if (this.cJ != 2)
              break label479;
            k = 0;
            localLayoutParams.leftMargin = k;
            localn1.setLayoutParams(localLayoutParams);
            float f = TypedValue.applyDimension(2, b(this.cJ, this.cI), getContext().getResources().getDisplayMetrics());
            this.cF.setTextSize(0, f);
            n localn2 = this.cF;
            m = (int)TypedValue.applyDimension(1, 3.0F, getContext().getResources().getDisplayMetrics());
            n = (int)TypedValue.applyDimension(1, 5.0F, getContext().getResources().getDisplayMetrics());
            if (this.cI != 2)
              break label485;
            label327: if ((this.cJ != 2) || (this.cI != 1))
              break label491;
            label343: localn2.setPadding(n, 0, 0, m);
            if ((this.cJ != 2) || (this.cI != 1))
              break label497;
            this.cB.setOrientation(1);
            this.cB.addView(this.cF);
            this.cB.addView(this.cC);
          }
          break;
        case 2:
        case 1:
        }
        break;
      case 1:
      case 0:
      case 2:
      }
    }
    while (true)
    {
      requestLayout();
      break;
      str = "global_count_bubble_medium";
      break label133;
      str = "global_count_bubble_small";
      break label133;
      str = "global_count_bubble_tall";
      break label133;
      this.cF.a(null);
      break label143;
      localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
      break label193;
      localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      break label193;
      j = 0;
      break label204;
      label479: k = 1;
      break label222;
      label485: n = 0;
      break label327;
      label491: m = 0;
      break label343;
      label497: this.cB.setOrientation(0);
      this.cB.addView(this.cC);
      int i1 = this.cG.length;
      while (i < i1)
      {
        this.cB.addView(this.cG[i]);
        i++;
      }
      this.cB.addView(this.cF);
    }
  }

  public final void Q()
  {
    setType(2);
    this.cE.setVisibility(0);
    U();
  }

  public void R()
  {
    setType(3);
    this.cE.setVisibility(4);
    U();
  }

  protected void S()
  {
    setType(1);
    this.cE.setVisibility(4);
    U();
  }

  protected void T()
  {
    setType(0);
    this.cE.setVisibility(4);
    U();
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
  }

  public void onConnected()
  {
  }

  public void onConnectionFailed$5d4cef71()
  {
    R();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
  }

  public void onDisconnected()
  {
  }

  public boolean performClick()
  {
    return this.cD.performClick();
  }

  public void setAnnotation(int paramInt)
  {
    bj.a(Integer.valueOf(paramInt), "Annotation must not be null.");
    this.cI = paramInt;
    V();
    O();
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.cD.setOnClickListener(paramOnClickListener);
    this.cF.setOnClickListener(paramOnClickListener);
  }

  public void setOnPlusOneClickListener(PlusOneButton.OnPlusOneClickListener paramOnPlusOneClickListener)
  {
    setOnClickListener(new b(paramOnPlusOneClickListener));
  }

  public void setSize(int paramInt)
  {
    a(paramInt, this.cH);
  }

  public void setType(int paramInt)
  {
    a(this.cJ, paramInt);
  }

  protected class a
    implements PlusClient.b
  {
    protected a()
    {
    }

    public void a(ConnectionResult paramConnectionResult, bk parambk)
    {
      if (ad.this.cz)
      {
        ad.this.cz = false;
        ad.this.cD.refreshDrawableState();
      }
      if ((paramConnectionResult.isSuccess()) && (parambk != null))
      {
        ad.this.cO = parambk;
        ad.this.C();
        ad.this.O();
      }
      while (true)
      {
        return;
        ad.this.R();
      }
    }
  }

  private final class b
    implements View.OnClickListener, PlusOneButton.OnPlusOneClickListener
  {
    private final PlusOneButton.OnPlusOneClickListener cX;

    public b(PlusOneButton.OnPlusOneClickListener arg2)
    {
      Object localObject;
      this.cX = localObject;
    }

    public final void onClick(View paramView)
    {
      Intent localIntent;
      if ((paramView == ad.this.cD) || (paramView == ad.this.cF))
      {
        if (ad.this.cO != null)
          break label52;
        localIntent = null;
        if (this.cX == null)
          break label74;
        this.cX.onPlusOneClick(localIntent);
      }
      while (true)
      {
        return;
        label52: localIntent = (Intent)ad.this.cO.bM.getParcelable("intent");
        break;
        label74: onPlusOneClick(localIntent);
      }
    }

    public final void onPlusOneClick(Intent paramIntent)
    {
      Context localContext = ad.this.getContext();
      if (((localContext instanceof Activity)) && (paramIntent != null))
      {
        Activity localActivity = (Activity)localContext;
        localActivity.startActivityForResult(paramIntent, 0);
      }
    }
  }

  private class c extends CompoundButton
  {
    public c(Context arg2)
    {
      super();
    }

    public void toggle()
    {
      if (ad.this.cz)
        super.toggle();
      while (true)
      {
        return;
        ad.this.cz = true;
        ad.this.Q();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ad
 * JD-Core Version:    0.6.2
 */