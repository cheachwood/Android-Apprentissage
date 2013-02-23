package com.google.protobuf;

public final class Internal
{
  public static boolean isValidUtf8(ByteString paramByteString)
  {
    int i = paramByteString.size();
    int j = 0;
    while (true)
    {
      int k;
      int m;
      boolean bool;
      label56: label58: int n;
      label126: int i1;
      if (j < i)
      {
        k = j + 1;
        m = 0xFF & paramByteString.byteAt(j);
        if (m >= 128)
        {
          bool = false;
          if (m >= 194)
          {
            bool = false;
            if (m <= 244)
              break label58;
          }
          label188: 
          do
          {
            do
            {
              int i2;
              do
              {
                do
                {
                  while (true)
                  {
                    return bool;
                    bool = false;
                    if (k < i)
                    {
                      j = k + 1;
                      n = 0xFF & paramByteString.byteAt(k);
                      if ((n < 128) || (n > 191))
                      {
                        bool = false;
                      }
                      else
                      {
                        if (m <= 223)
                          break;
                        if (j < i)
                          break label126;
                        bool = false;
                      }
                    }
                  }
                  i1 = j + 1;
                  i2 = 0xFF & paramByteString.byteAt(j);
                  bool = false;
                }
                while (i2 < 128);
                bool = false;
              }
              while (i2 > 191);
              if (m > 239)
                break label212;
              if (m != 224)
                break label188;
              bool = false;
            }
            while (n < 160);
            if (m != 237)
              break label206;
            bool = false;
          }
          while (n > 159);
        }
      }
      else
      {
        while (true)
        {
          label206: j = i1;
          break;
          label212: bool = false;
          if (i1 >= i)
            break label56;
          int i3 = i1 + 1;
          int i4 = 0xFF & paramByteString.byteAt(i1);
          if ((i4 < 128) || (i4 > 191))
          {
            bool = false;
            break label56;
          }
          if (((m == 240) && (n < 144)) || ((m == 244) && (n > 143)))
          {
            bool = false;
            break label56;
            bool = true;
            break label56;
          }
          i1 = i3;
        }
      }
      j = k;
    }
  }

  public static abstract interface EnumLite
  {
    public abstract int getNumber();
  }

  public static abstract interface EnumLiteMap<T extends Internal.EnumLite>
  {
    public abstract T findValueByNumber(int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.Internal
 * JD-Core Version:    0.6.2
 */