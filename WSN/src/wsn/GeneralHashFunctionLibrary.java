/*
  ********************************************************************
  *                                                                  *
  *                  General Hash Functions Library                  *
  * Author: Arash Partow - 2002                                      *
  * URL: http://www.partow.net                                       *
  *                                                                  *
  * Copyright Notice:                                                *
  * Free use of this library is permitted under the guidelines and   *
  * in accordance with the most current version of the Common Public *
  * License.                                                         *
  * http://www.opensource.org/licenses/cpl.php                       *
  *                                                                  *
  ********************************************************************
*/ 
package wsn;
class GeneralHashFunctionLibrary
{


   public long RSHash(String str)
   {

      int b = 378551;
      int a = 63689;
      long hash = 0;

      for(int i = 0; i < str.length(); i++)
      {
         hash = hash*a+str.charAt(i);
         a = a*b;
      }

      return (hash & 0x7FFFFFFF);

   }
   /* End Of RS Hash Function */


   public long JSHash(String str)
   {

      long hash = 1315423911;

      for(int i = 0; i < str.length(); i++)
      {
         hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
      }

      return (hash & 0x7FFFFFFF);

   }
   /* End Of JS Hash Function */


   public long PJWHash(String str)
   {

      long BitsInUnignedInt = (long)(4 * 8);
      long ThreeQuarters    = (long)((BitsInUnignedInt  * 3) / 4);
      long OneEighth        = (long)(BitsInUnignedInt / 8);
      long HighBits         = (long)(0xFFFFFFFF) << (BitsInUnignedInt - OneEighth);
      long hash             = 0;
      long test             = 0;

      for(int i = 0; i < str.length(); i++)
      {

         hash = (hash << OneEighth) + str.charAt(i);

         if((test = hash & HighBits)  != 0)
         {
   
            hash = (( hash ^ (test >> ThreeQuarters)) & (~HighBits));

         }

      }

      return (hash & 0x7FFFFFFF);

   }
   /* End Of  P. J. Weinberger Hash Function */


   public long ELFHash(String str)
   {

      long hash = 0;
      long x    = 0;

      for(int i = 0; i < str.length(); i++)
      {
        
         hash = (hash << 4) + str.charAt(i);
         
         if((x = hash & 0xF0000000L) != 0)
         {
                
            hash ^= (x >> 24);
            hash &= ~x;
            
         }
         
      }

      return (hash & 0x7FFFFFFF);

   }
   /* End Of ELF Hash Function */


   public long BKDRHash(String str)
   {

      long seed = 131; // 31 131 1313 13131 131313 etc..
      long hash = 0;

      for(int i = 0; i < str.length(); i++)
      {

         hash = (hash*seed)+str.charAt(i);

      }

      return (hash & 0x7FFFFFFF);

   }
   /* End Of BKDR Hash Function */


   public long SDBMHash(String str)
   {

      int hash = 0;

      for(int i = 0; i < str.length(); i++)
      {

         hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;

      }

      return (hash & 0x7FFFFFFF);

   }
   /* End Of SDBM Hash Function */


   public long DJBHash(String str)
   {

      int hash = 5381;

      for(int i = 0; i < str.length(); i++)
      {

         hash = ((hash << 5) + hash) + str.charAt(i);

      }

      return (hash & 0x7FFFFFFF);


   }
   /* End Of DJB Hash Function */


   public long APHash(String str)
   {

      int hash = 0;

      for(int i = 0; i < str.length(); i++)
      {

         if ((i & 1) == 0)
         {
            hash ^=((hash << 7)^str.charAt(i)^(hash >> 3));
         }
         else
         {
            hash ^= (~((hash << 11)^str.charAt(i)^(hash >> 5)));
         }

      }

      return (hash & 0x7FFFFFFF);


   }
   /* End Of AP Hash Function */


}
