package com.google.android.gms.internal;

import android.os.IBinder;
import java.lang.reflect.Field;

public final class ak<T> extends f.a
{
  private final T bc;

  private ak(T paramT)
  {
    this.bc = paramT;
  }

  public static <T> T a(f paramf)
  {
    Object localObject2;
    if ((paramf instanceof ak))
      localObject2 = ((ak)paramf).bc;
    while (true)
    {
      return localObject2;
      IBinder localIBinder = paramf.asBinder();
      Field[] arrayOfField = localIBinder.getClass().getDeclaredFields();
      if (arrayOfField.length != 1)
        break label122;
      Field localField = arrayOfField[0];
      if (!localField.isAccessible())
      {
        localField.setAccessible(true);
        try
        {
          Object localObject1 = localField.get(localIBinder);
          localObject2 = localObject1;
        }
        catch (NullPointerException localNullPointerException)
        {
          throw new IllegalArgumentException("Binder object is null.", localNullPointerException);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw new IllegalArgumentException("remoteBinder is the wrong class.", localIllegalArgumentException);
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          throw new IllegalArgumentException("Could not access the field in remoteBinder.", localIllegalAccessException);
        }
      }
    }
    throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
    label122: throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
  }

  public static <T> f b(T paramT)
  {
    return new ak(paramT);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ak
 * JD-Core Version:    0.6.2
 */