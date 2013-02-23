package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.a;

public class au extends ImageView
  implements GooglePlayServicesClient.ConnectionCallbacks, PlusClient.a
{
  private static final String TAG = au.class.getSimpleName();
  private int bE;
  private boolean bF;
  private boolean bG;
  private Bitmap bH;
  private PlusClient bI;
  private Uri mUri;

  public au(Context paramContext)
  {
    super(paramContext);
  }

  public au(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public au(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void C()
  {
    int i;
    if ((this.mUri != null) && ("android.resource".equals(this.mUri.getScheme())))
    {
      i = 1;
      if (this.bG)
        break label37;
    }
    label37: label52: 
    do
      while (true)
      {
        return;
        i = 0;
        break;
        if (this.mUri != null)
          break label52;
        setImageBitmap(null);
      }
    while ((i == 0) && ((this.bI == null) || (!this.bI.isConnected())));
    if (i != 0)
      setImageURI(this.mUri);
    while (true)
    {
      this.bG = false;
      break;
      this.bI.a(this, this.mUri, this.bE);
    }
  }

  public final void a(Uri paramUri, int paramInt)
  {
    boolean bool;
    if (this.mUri == null)
      if (paramUri == null)
      {
        bool = true;
        int i = this.bE;
        int j = 0;
        if (i == paramInt)
          j = 1;
        if ((!bool) || (j == 0))
          break label58;
      }
    while (true)
    {
      return;
      bool = false;
      break;
      bool = this.mUri.equals(paramUri);
      break;
      label58: this.mUri = paramUri;
      this.bE = paramInt;
      this.bG = true;
      C();
    }
  }

  public final void a(ConnectionResult paramConnectionResult, ParcelFileDescriptor paramParcelFileDescriptor)
  {
    if (!paramConnectionResult.isSuccess());
    while (true)
    {
      return;
      this.bG = false;
      if (paramParcelFileDescriptor != null)
        new a(this.bE).execute(new ParcelFileDescriptor[] { paramParcelFileDescriptor });
    }
  }

  public final void a(PlusClient paramPlusClient)
  {
    if (paramPlusClient != this.bI)
    {
      if ((this.bI != null) && (this.bI.isConnectionCallbacksRegistered(this)))
        this.bI.unregisterConnectionCallbacks(this);
      this.bI = paramPlusClient;
      this.bI.registerConnectionCallbacks(this);
    }
  }

  public final void b(Uri paramUri)
  {
    a(paramUri, 0);
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.bF = true;
    if ((this.bI != null) && (!this.bI.isConnectionCallbacksRegistered(this)))
      this.bI.registerConnectionCallbacks(this);
    if (this.bH != null)
      setImageBitmap(this.bH);
  }

  public final void onConnected()
  {
    C();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.bF = false;
    if ((this.bI != null) && (this.bI.isConnectionCallbacksRegistered(this)))
      this.bI.unregisterConnectionCallbacks(this);
  }

  public final void onDisconnected()
  {
  }

  private final class a extends AsyncTask<ParcelFileDescriptor, Void, Bitmap>
  {
    private final int bE;

    a(int arg2)
    {
      int i;
      this.bE = i;
    }

    // ERROR //
    private Bitmap a(ParcelFileDescriptor[] paramArrayOfParcelFileDescriptor)
    {
      // Byte code:
      //   0: aload_1
      //   1: iconst_0
      //   2: aaload
      //   3: astore_2
      //   4: aload_2
      //   5: invokevirtual 28	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
      //   8: invokestatic 34	android/graphics/BitmapFactory:decodeFileDescriptor	(Ljava/io/FileDescriptor;)Landroid/graphics/Bitmap;
      //   11: astore 6
      //   13: aload_0
      //   14: getfield 18	com/google/android/gms/internal/au$a:bE	I
      //   17: ifle +41 -> 58
      //   20: aload 6
      //   22: aload_0
      //   23: getfield 18	com/google/android/gms/internal/au$a:bE	I
      //   26: invokestatic 40	com/google/android/gms/internal/au:b	(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
      //   29: astore 9
      //   31: aload 9
      //   33: astore 6
      //   35: aload_2
      //   36: invokevirtual 43	android/os/ParcelFileDescriptor:close	()V
      //   39: aload 6
      //   41: areturn
      //   42: astore 10
      //   44: invokestatic 47	com/google/android/gms/internal/au:D	()Ljava/lang/String;
      //   47: ldc 49
      //   49: aload 10
      //   51: invokestatic 55	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   54: pop
      //   55: goto -16 -> 39
      //   58: aload_2
      //   59: invokevirtual 43	android/os/ParcelFileDescriptor:close	()V
      //   62: goto -23 -> 39
      //   65: astore 7
      //   67: invokestatic 47	com/google/android/gms/internal/au:D	()Ljava/lang/String;
      //   70: ldc 49
      //   72: aload 7
      //   74: invokestatic 55	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   77: pop
      //   78: goto -39 -> 39
      //   81: astore_3
      //   82: aload_2
      //   83: invokevirtual 43	android/os/ParcelFileDescriptor:close	()V
      //   86: aload_3
      //   87: athrow
      //   88: astore 4
      //   90: invokestatic 47	com/google/android/gms/internal/au:D	()Ljava/lang/String;
      //   93: ldc 49
      //   95: aload 4
      //   97: invokestatic 55	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   100: pop
      //   101: goto -15 -> 86
      //
      // Exception table:
      //   from	to	target	type
      //   35	39	42	java/io/IOException
      //   58	62	65	java/io/IOException
      //   4	31	81	finally
      //   82	86	88	java/io/IOException
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.au
 * JD-Core Version:    0.6.2
 */