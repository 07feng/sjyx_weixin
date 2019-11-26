package com.sunnet.framework.util;

import java.util.Random;

public class RandomStringGenerator
{
  private static final String base = "abcdefghijklmnopqrstuvwxyz0123456789";
  
  public static String getRandomStringByLength(int length)
  {
    StringBuffer sb = new StringBuffer();
    Random random = new Random();
    for (int i = 0; i < length; i++)
    {
      int number = random.nextInt("abcdefghijklmnopqrstuvwxyz0123456789".length());
      sb.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(number));
    }
    return sb.toString().toUpperCase();
  }
}
