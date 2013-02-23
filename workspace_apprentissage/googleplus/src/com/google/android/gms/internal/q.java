package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class q
{
  private static Context C;
  private static am D;

  public static am a(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    bj.c(paramContext);
    if (c() != null);
    for (int i = 1; i == 0; i = 0)
    {
      int j = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
      if (j == 0)
        break;
      throw new GooglePlayServicesNotAvailableException(j);
    }
    if (D == null)
    {
      Class localClass = c();
      if (localClass != null)
      {
        Log.i(q.class.getSimpleName(), "Making Creator statically");
        D = (am)a(localClass);
        b(paramContext);
      }
    }
    if (D != null);
    for (am localam = D; ; localam = D)
    {
      return localam;
      D = am.a.n((IBinder)a(getRemoteContext(paramContext).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
      b(paramContext);
    }
  }

  private static <T> T a(Class<?> paramClass)
  {
    try
    {
      Object localObject = paramClass.newInstance();
      return localObject;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new IllegalStateException("Unable to instantiate the dynamic class " + paramClass.getName());
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    throw new IllegalStateException("Unable to call the default constructor of " + paramClass.getName());
  }

  private static <T> T a(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      Object localObject = a(((ClassLoader)bj.c(paramClassLoader)).loadClass(paramString));
      return localObject;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    throw new IllegalStateException("Unable to find dynamic class " + paramString);
  }

  private static void b(Context paramContext)
  {
    try
    {
      D.b(ak.b(getRemoteContext(paramContext).getResources()));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  private static Class<?> c()
  {
    try
    {
      Class localClass2 = Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
      localClass1 = localClass2;
      return localClass1;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      while (true)
        Class localClass1 = null;
    }
  }

  private static Context getRemoteContext(Context paramContext)
  {
    if (C == null)
      if (c() == null)
        break label20;
    label20: for (C = paramContext; ; C = GooglePlayServicesUtil.getRemoteContext(paramContext))
      return C;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.q
 * JD-Core Version:    0.6.2
 */