package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.LinkedList;

final class k
  implements aq<T>
{
  k(d paramd)
  {
  }

  public final void b(T paramT)
  {
    d.a(this.b, paramT);
    Iterator localIterator = d.a(this.b).iterator();
    while (localIterator.hasNext())
    {
      d.a locala = (d.a)localIterator.next();
      d.b(this.b);
      locala.a$6728a24f();
    }
    d.a(this.b).clear();
    d.a(this.b, null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.k
 * JD-Core Version:    0.6.2
 */