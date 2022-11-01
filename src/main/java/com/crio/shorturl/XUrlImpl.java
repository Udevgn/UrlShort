package com.crio.shorturl;
import java.util.*;


public class XUrlImpl implements XUrl{

    Map<String,String> shortToLong;
    Map<String,String> longToShort;
    Map<String,Integer> hitCount;
    final int URL_LEN;
    final String SHORT_URL_BASE;

    public XUrlImpl(){
        URL_LEN = 9;
        SHORT_URL_BASE = "http://short.url/";
        shortToLong = new HashMap();
        longToShort = new HashMap();
        hitCount = new HashMap();
    }
    private String getFullShort(String str){
        return SHORT_URL_BASE+str ;
    }
    public String registerNewUrl(String longUrl){

        if(longToShort.containsKey(longUrl)){
                return longToShort.get(longUrl);
        }
        String shortUrl = generateKey(longUrl);
        shortToLong.put(shortUrl,longUrl);
        longToShort.put(longUrl,shortUrl);
        return shortUrl;
    }

    private String generateKey(String longUrl){
        char map[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
     
        StringBuffer shorturl = new StringBuffer();
     
        // Convert given integer id to a base 62 number
        Random random = new Random();
        int n = URL_LEN;
        while (n > 0)
        {
            // use above map to store actual character
            // in short url
            int idx = random.nextInt(map.length);
            shorturl.append(map[idx]);
            n--;
        }
     
        // Reverse shortURL to complete base conversion
        return getFullShort(shorturl.reverse().toString());
    }

    // If shortUrl is already present, return null
    // Else, register the specified shortUrl for the given longUrl
    // Note: You don't need to validate if longUrl is already present, 
    //       assume it is always new i.e. it hasn't been seen before 
    public String registerNewUrl(String longUrl, String shortUrl){
        
        if(shortToLong.containsKey(shortUrl)){
            return null;
        }
        shortToLong.put(shortUrl,longUrl);
        longToShort.put(longUrl,shortUrl);
        return shortUrl;
    }
  
    // If shortUrl doesn't have a corresponding longUrl, return null
    // Else, return the corresponding longUrl
    public String getUrl(String shortUrl){
        String longUrl =  shortToLong.get(shortUrl);
        hitCount.put(longUrl,hitCount.getOrDefault(longUrl,0)+1);
        return longUrl;
    }
  
    // Return the number of times the longUrl has been looked up using getUrl()
    public Integer getHitCount(String longUrl){
            return hitCount.getOrDefault(longUrl,0);
    }
  
    // Delete the mapping between this longUrl and its corresponding shortUrl
    // Do not zero the Hit Count for this longUrl
    public String delete(String longUrl){
        if(longToShort.containsKey(longUrl)==false)
            return null;
        String shortUrl = longToShort.get(longUrl);
        shortToLong.remove(shortUrl);
        longToShort.remove(longUrl);
        return shortUrl;
    }




}