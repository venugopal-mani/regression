import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;



class article


{
    int id;
    float retid;
    int klass;
    String klasslabel;
    LinkedHashMap<Integer,Integer> freqv = new LinkedHashMap<Integer,Integer>();
    LinkedHashMap<Integer, Integer> bin = new LinkedHashMap<Integer,Integer>();
    LinkedHashMap<Integer, Double> tfidf = new LinkedHashMap<Integer,Double>();
    double predscore;
   int ysubi;
}


class klass
{
    int id;
    String label;
    List<article> members = new ArrayList<article>();
    List<article> notmembers = new ArrayList<article>();
    List<Integer> impfeatures = new ArrayList<Integer>();
    LinkedHashMap<Integer, Double> weights = new LinkedHashMap<Integer, Double>();
   
    
}





public class regression {
    static LinkedHashMap<Integer, Double> jumboMap = new LinkedHashMap<Integer,Double>();
   
    public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> unsortMap)
    {

        List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Integer, Double>>()
        {
            public int compare(Entry<Integer, Double> o1,
                    Entry<Integer, Double> o2)
            {
               
                    return o2.getValue().compareTo(o1.getValue());

                
            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
        for (Entry<Integer, Double> entry : list)
        {
        	int key = entry.getKey();
        	double val = entry.getValue();
            sortedMap.put(key, val);
        }

        return sortedMap;
    }
   
   
    
    public static double log2(double n)
    {
            return (Math.log(n) / Math.log(2));
    }
   
    
    public static double computeMag(LinkedHashMap<Integer, Double> m)
    {double ans = 0;
    	Iterator<Entry<Integer, Double>> ite = m.entrySet().iterator();
        while (ite.hasNext())
       {
     
        	Entry<Integer,Double> e =  (Entry<Integer, Double>)ite.next();
        	double val= e.getValue();
        	ans = ans + val*val;
       }
        return ans;
    }
    
    public static double computePredScore(article a, LinkedHashMap<Integer, Integer> rep, klass k)
    {
    	double predscore = 0;
    	
    	Iterator<Entry<Integer, Integer>> it = rep.entrySet().iterator();
        while (it.hasNext())
        {  
     	   Entry<Integer, Integer> e =  (Entry<Integer, Integer>)it.next();
     	   int dim = e.getKey();
     	   int val = e.getValue();
     	   
     	   if (k.weights.containsKey(dim))
     	   {
     		   predscore = predscore + val*k.weights.get(dim);
     	   }
        }
        
    	return predscore;
    }
  
    
    
    
    public static double computePredScoreT(article a, LinkedHashMap<Integer, Double> rep, klass k)
    {
    	double predscore = 0;
    	
    	Iterator<Entry<Integer, Double>> it = rep.entrySet().iterator();
        while (it.hasNext())
        {  
     	   Entry<Integer, Double> e =  (Entry<Integer, Double>)it.next();
     	   int dim = e.getKey();
     	   double val = e.getValue();
     	   
     	   if (k.weights.containsKey(dim))
     	   {
     		   predscore = predscore + val*k.weights.get(dim);
     	   }
        }
        
    	return predscore;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static double computeError(List<article>train, LinkedHashMap<Integer,Double> w, double lambda)
    
    {
    	double errorterm = 0.0;
    	double errorterm1 = 0.0;
    	double errorterm2 = lambda * computeMag(w);
    	LinkedHashMap<Integer,Double> weightedfeatures = new LinkedHashMap<Integer,Double>();
    	
    	for (article a: train)
    	{double netweightval = 0;
    		for (Map.Entry<Integer,Integer> entry : a.freqv.entrySet()) {
    		    int key = entry.getKey();
    		    
    		    double value = entry.getValue();
    		   
    		    if (w.containsKey(key))
    		    {
    		    	double val2 = w.get(key);
    		    	netweightval = netweightval + (value*val2);
    		    }
    		    // ...
    		}
    		weightedfeatures.put(a.id, netweightval-a.ysubi);
    	}
    
    	errorterm1 = computeMag(weightedfeatures);
    	errorterm = errorterm1 - errorterm2;
    	System.out.println("The error is: "+errorterm);
    	return errorterm;
        
    }
       
    
 public static double computeErrorB(List<article>train, LinkedHashMap<Integer,Double> w,  double lambda)
    
    {
    	double errorterm = 0.0;
    	double errorterm1 = 0.0;
    	double errorterm2 = lambda * computeMag(w);
    	LinkedHashMap<Integer,Double> weightedfeatures = new LinkedHashMap<Integer,Double>();
    	
    	for (article a: train)
    	{double netweightval = 0;
    		for (Map.Entry<Integer,Integer> entry : a.bin.entrySet()) {
    		    int key = entry.getKey();
    		    
    		    double value = entry.getValue();
    		   
    		    if (w.containsKey(key))
    		    {
    		    	double val2 = w.get(key);
    		    	netweightval = netweightval + (value*val2);
    		    }
    		    // ...
    		}
    		weightedfeatures.put(a.id, netweightval-a.ysubi);
    	}
    
    	errorterm1 = computeMag(weightedfeatures);
    	errorterm = errorterm1 - errorterm2;
    	System.out.println("The error is: "+errorterm);
    	return errorterm;
        
    }
    
 public static double computeErrorT(List<article>train, LinkedHashMap<Integer,Double> w,  double lambda)
    
    {
    	double errorterm = 0.0;
    	double errorterm1 = 0.0;
    	double errorterm2 = lambda * computeMag(w);
    	LinkedHashMap<Integer,Double> weightedfeatures = new LinkedHashMap<Integer,Double>();
    	
    	for (article a: train)
    	{double netweightval = 0;
    		for (Map.Entry<Integer,Double> entry : a.tfidf.entrySet()) {
    		    int key = entry.getKey();
    		    
    		    double value = entry.getValue();
    		   
    		    if (w.containsKey(key))
    		    {
    		    	double val2 = w.get(key);
    		    	netweightval = netweightval + (value*val2);
    		    }
    		    // ...
    		}
    		weightedfeatures.put(a.id, netweightval-a.ysubi);
    	}
    
    	errorterm1 = computeMag(weightedfeatures);
    	errorterm = errorterm1 - errorterm2;
    	System.out.println("The error is: "+errorterm);
    	return errorterm;
        
    }
   
    public static LinkedHashMap<Integer,Double> computeWeights(klass k, List<article> train, String featurerepresenation, double lambda)
    {
        LinkedHashMap<Integer,Double> w = new LinkedHashMap<Integer, Double>();
       
        //INITIALISE WEIGHTS

        if (featurerepresenation.equals("tf"))
        {
        for (article a: k.members)
        {	
            Iterator<Entry<Integer, Integer>> ite = a.freqv.entrySet().iterator();
              while (ite.hasNext())
             {
                  Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
                  int dimension = e.getKey();
                  if (w.containsKey(dimension))
                  {
                      int donothing = 0;
                  }
                  else
                  {
                      w.put(dimension, 0.0);
                  }
                  
             }     
        }
       
        System.out.println("Class "+k.id+" has "+w.size()+" dimensions");
      
        //VALUE OF ASUB I
        
      
        for (article a: train)
        {
        	if (a.klass == k.id)
        	{
        		a.ysubi = 1;
        	
        	}
        	else
        	{
        		a.ysubi = 0;
        	}
        }

        
        
     
      double error = 0;
      
      
      for(int iy = 0; iy < 3; iy++)
      {	  
    	  Iterator<Entry<Integer, Double>> ite = w.entrySet().iterator();
        while (ite.hasNext())
        {   

              Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
              
             int dimension = e.getKey();
             Set<Integer>currFeature = new LinkedHashSet<Integer>(dimension);
             Set<Integer> otherfeatures = new LinkedHashSet<Integer>(w.keySet());
            otherfeatures.removeAll(currFeature);
           
              double term1 = 0;
              
              for(article x: k.members)
              {
            	  if (x.freqv.containsKey(dimension))
            	  {
                  term1 = term1 + x.freqv.get(dimension);
            	  }
            	 
              }

              
              
         double term2 = 0;
       
        
         for (article x: train)
         {	
        	 double subterm1 = 0;
        	 if (x.freqv.containsKey(dimension))
        	 {
             subterm1 = x.freqv.get(dimension);
        	 }
        	 
        	 double subterm2 = 0;
             
        	 if (subterm1 !=0)
        	 {  subterm2= 0;
                 for (Integer i: otherfeatures)
                 {if (x.freqv.containsKey(i))
                 {
                     subterm2 = subterm2+ x.freqv.get(i)*w.get(i);
                 }  
                 }
        	 }
            
            term2 = term2 + subterm1*subterm2;
            
         }

         //System.out.println("Done computing second term ");
              
              double term3 = jumboMap.get(dimension);
        
        
         double newweight = (term1 - term2)/(term3 + lambda);
        
         if (newweight > 0 )
         {
         w.put(dimension, newweight);}
         
         else
         { w.put(dimension, 0.0);
     
         }
         //System.out.println("Done computing third term ");
      //System.out.println("Dimension "+count+" done");
          
        }
        double newerror = computeError(train,w,lambda);
        
       
        System.out.println("Outer iteration: "+iy+" "+"Error: "+newerror);
    } 
      
        }
        
       
        
        
        
        
        
        
        else if(featurerepresenation.equals("tfidf"))
        {
        
            for (article a: k.members)
            {	
                Iterator<Entry<Integer, Double>> ite = a.tfidf.entrySet().iterator();
                  while (ite.hasNext())
                 {
                      Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
                      int dimension = e.getKey();
                      if (w.containsKey(dimension))
                      {
                          int donothing = 0;
                      }
                      else
                      {
                          w.put(dimension, 0.0);
                      }
                      
                 }     
            }
           
            System.out.println("Class "+k.id+" has "+w.size()+" dimensions");
          
            
         

          for(int iy = 0; iy<3; iy++)
          {	  
        	  Iterator<Entry<Integer, Double>> ite = w.entrySet().iterator();
            while (ite.hasNext())
            {   
        
                  Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
                  
                 int dimension = e.getKey();
                 Set<Integer>currFeature = new LinkedHashSet<Integer>(dimension);
                 Set<Integer> otherfeatures = new LinkedHashSet<Integer>(w.keySet());
                otherfeatures.removeAll(currFeature);
               
                  double term1 = 0;
                  
                  for(article x: k.members)
                  {
                	  if (x.tfidf.containsKey(dimension))
                	  {
                      term1 = term1 + x.tfidf.get(dimension);
                	  }
                	 
                  }

                  
                  
             double term2 = 0;
           
            
             for (article x: train)
             {	
            	 double subterm1 = 0;
            	 if (x.tfidf.containsKey(dimension))
            	 {
                 subterm1 = x.tfidf.get(dimension);
            	 }
            	 
            	 double subterm2 = 0;
                 
            	 if (subterm1 !=0)
            	 {  subterm2= 0;
                     for (Integer i: otherfeatures)
                     {if (x.tfidf.containsKey(i))
                     {
                         subterm2 = subterm2+ x.tfidf.get(i)*w.get(i);
                     }  
                     }
            	 }
                
                term2 = term2 + subterm1*subterm2;
                
             }

             //System.out.println("Done computing second term ");
                  
                  double term3 = jumboMap.get(dimension);
            
            
             double newweight = (term1 - term2)/(term3 + lambda);
            
             if (newweight > 0 )
             {
             w.put(dimension, newweight);}
             
             else
             { w.put(dimension, 0.0);
         
             }
             //System.out.println("Done computing third term ");
          //System.out.println("Dimension "+count+" done");
              
            }
            double newerror = computeErrorT(train,w,lambda);
            System.out.println("Outer iteration: "+iy+" "+"Error: "+newerror);
            
           
            
        } 
        } 
        
        
        else if(featurerepresenation.equals("binary"))
        {
        
            for (article a: k.members)
            {	
                Iterator<Entry<Integer, Integer>> ite = a.bin.entrySet().iterator();
                  while (ite.hasNext())
                 {
                      Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
                      int dimension = e.getKey();
                      if (w.containsKey(dimension))
                      {
                          int donothing = 0;
                      }
                      else
                      {
                          w.put(dimension, 0.0);
                      }
                      
                 }     
            }
           
            System.out.println("Class "+k.id+" has "+w.size()+" dimensions");
          
            
            
         
        

          for(int iy = 0; iy<3; iy++)
          {	  
        	  Iterator<Entry<Integer, Double>> ite = w.entrySet().iterator();
            while (ite.hasNext())
            {   
        
                  Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
                  
                 int dimension = e.getKey();
                 Set<Integer>currFeature = new LinkedHashSet<Integer>(dimension);
                 Set<Integer> otherfeatures = new LinkedHashSet<Integer>(w.keySet());
                otherfeatures.removeAll(currFeature);
               
                  double term1 = 0;
                  
                  for(article x: k.members)
                  {
                	  if (x.bin.containsKey(dimension))
                	  {
                      term1 = term1 + x.bin.get(dimension);
                	  }
                	 
                  }

                  
                  
             double term2 = 0;
           
            
             for (article x: train)
             {	
            	 double subterm1 = 0;
            	 if (x.bin.containsKey(dimension))
            	 {
                 subterm1 = x.bin.get(dimension);
            	 }
            	 
            	 double subterm2 = 0;
                 
            	 if (subterm1 !=0)
            	 {  subterm2= 0;
                     for (Integer i: otherfeatures)
                     {if (x.bin.containsKey(i))
                     {
                         subterm2 = subterm2+ x.bin.get(i)*w.get(i);
                     }  
                     }
            	 }
                
                term2 = term2 + subterm1*subterm2;
                
             }

             //System.out.println("Done computing second term ");
                  
                  double term3 = jumboMap.get(dimension);
            
            
             double newweight = (term1 - term2)/(term3 + lambda);
            
             if (newweight > 0 )
             {
             w.put(dimension, newweight);}
             
             else
             { w.put(dimension, 0.0);
         
             }
             //System.out.println("Done computing third term ");
          //System.out.println("Dimension "+count+" done");
              
            }
            double newerror = computeErrorB(train,w,lambda);
            
          System.out.println("The error is: "+newerror);
        } 
          
        
        
    } 
        return w;
    }
    
    

    public static LinkedHashMap<Integer, Double> findFeatureDist(List<article> art, String s)
    {    LinkedHashMap<Integer, Double> answer = new LinkedHashMap<Integer, Double>();
        if (s.equals("tf"))
        {

            for (article x: art)
            {
                Iterator<Entry<Integer, Integer>> ite = x.freqv.entrySet().iterator();
               
                 while (ite.hasNext())
                    {
                         Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
                         int key = e.getKey();
                         double val = e.getValue() * e.getValue();
                         
                         if (answer.containsKey(key)){
                             double val2 = answer.get(key);
                             val2 = val2 + val;
                             answer.put(key, val2);
                         }
                         else
                         {
                             answer.put(key, val);
                         }
                    }
           
            }
           
        }
       
        else if (s.equals("binary"))
        {

            for (article x: art)
            {
                Iterator<Entry<Integer, Integer>> ite = x.bin.entrySet().iterator();
               
                 while (ite.hasNext())
                    {
                         Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
                         int key = e.getKey();
                         double val = e.getValue() * e.getValue();
                         
                         if (answer.containsKey(key)){
                             double val2 = answer.get(key);
                             val2 = val2 + val;
                             answer.put(key, val2);
                         }
                         else
                         {
                             answer.put(key, val);
                         }
                    }
           
            }
           
        }
       
        else if (s.equals("tfidf"))
        {

            for (article x: art)
            {
                Iterator<Entry<Integer, Double>> ite = x.tfidf.entrySet().iterator();
               
                 while (ite.hasNext())
                    {
                         Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
                         int key = e.getKey();
                         double val = e.getValue() * e.getValue();
                         
                         if (answer.containsKey(key)){
                             double val2 = answer.get(key);
                             val2 = val2 + val;
                             answer.put(key, val2);
                         }
                         else
                         {
                             answer.put(key, val);
                         }
                    }
           
            }
           
        }
       
        return answer;
       
       
    }
   
   
    public static void main(String[] args)
   
    {
        final long starttime = System.currentTimeMillis();
       
        String inputfile = args[0];
        String rlabelfile = args[1];
        String trainfile = args[2];
        String testfile = args[3];
        String classfile = args[4];
        String featuresfile = args[5];
        String featurerepresentation = args[6];
        String outputfile = args[7];
        String ridgetrainfile = args[8];
        String ridgeValfile = args[9];
        
        LinkedHashMap<Integer,Integer> found_articles = new LinkedHashMap<Integer,Integer>();
        List<article> article_list = new ArrayList<article>();
        List <klass> class_list = new ArrayList<klass>();
       
        LinkedHashMap<Integer,Integer> features = new LinkedHashMap<Integer,Integer>();
        LinkedHashMap<Integer,Float> retmap = new LinkedHashMap<Integer,Float>();
        LinkedHashMap<Integer, String> knames = new LinkedHashMap<Integer,String>();
        LinkedHashMap<Integer, String> featurenames = new LinkedHashMap<Integer,String>();
       

        
        try (BufferedReader br = new BufferedReader(new FileReader(inputfile))) {
            String line;
            String[]a = new String[3];
           
            while ((line = br.readLine()) != null  ) {
                a = line.split(" ");
                int id = Integer.parseInt(a[0]);
                int dim = Integer.parseInt(a[1]);
                int freq = Integer.parseInt(a[2]);
               
                if (found_articles.containsKey(id))
                {
                    for(article x: article_list)
                    {
                        if (x.id == id)
                        {
                            x.freqv.put(dim, freq);
                        }
                    }
                }
               
                else
                {
                    found_articles.put(id, 1);
                    article temp = new article();
                    temp.id = id;
                    temp.freqv.put(dim, freq);
                    article_list.add(temp);
                   
                }
           
            }
        }
       
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        LinkedHashMap<Integer,Integer> global_feature_map = new LinkedHashMap<Integer,Integer>();
       
       
        for (article x: article_list)
        {
            Iterator<Entry<Integer, Integer>> ite = x.freqv.entrySet().iterator();
           
             while (ite.hasNext())
                {
                     Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
                     int key = e.getKey();
                     x.bin.put(key,1);
                     
                     if (global_feature_map.containsKey(key)){
                         int value = global_feature_map.get(key);
                         value++;
                         global_feature_map.put(key, value);
                     }
                     else
                     {
                         global_feature_map.put(key, 1);
                     }
                }
       
        }
       
        System.out.println(global_feature_map.size());
       
       
        for (article x: article_list)
        {
            Iterator<Entry<Integer, Integer>> ite = x.freqv.entrySet().iterator();
           
             while (ite.hasNext())
                {
                     Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
                     int key = e.getKey();
                     double val = e.getValue();
                     double te = (log2(article_list.size()) - log2(global_feature_map.get(key)));
                     val = val*te;
                     x.tfidf.put(key,val);
                }
       
        }
        
        
        //CLABEL FILE

        try (BufferedReader br = new BufferedReader(new FileReader(featuresfile))) {
            String line;
            int id = 0;
           
            while ((line = br.readLine()) != null  ) {
            		id++;
            		featurenames.put(id, line);
            }
        }
       
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  

        
   //TRAIN OR TEST HASH MAP

        LinkedHashMap<Integer, Integer> trainortest = new LinkedHashMap<Integer, Integer>();
       
        try (BufferedReader br = new BufferedReader(new FileReader(ridgetrainfile))) {
            String line;
           
            while ((line = br.readLine()) != null  ) {
               
                int id = Integer.parseInt(line);
                trainortest.put(id, 1);
            }
        }
       
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           
        try (BufferedReader br = new BufferedReader(new FileReader(testfile))) {
            String line;
           
            while ((line = br.readLine()) != null  ) {
               
                int id = Integer.parseInt(line);
                trainortest.put(id, 0);
            }
        }
       
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       
        try (BufferedReader br = new BufferedReader(new FileReader(ridgeValfile))) {
            String line;
           
            while ((line = br.readLine()) != null  ) {
               
                int id = Integer.parseInt(line);
                trainortest.put(id, 2);
            }
        }
       
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
       
       
        //LABEL READING
       
    LinkedHashMap<String,Integer> klasslabels = new LinkedHashMap<String, Integer>();
    int x =0;
       
        try (BufferedReader br = new BufferedReader(new FileReader(rlabelfile))) {
            String line;
            String[]a = new String[3];
           
            while ((line = br.readLine()) != null  ) {
               
                a = line.split(" ");
                int id = Integer.parseInt(a[0]);
                String kl = a[1];
               
                if (klasslabels.containsKey(kl))
                {
                    int donothing = 0;
                   
                }
               
                else
                {
                    klasslabels.put(kl, x);
                    x++;
                }
               
                float ret = Float.parseFloat(a[2]);
                knames.put(id, kl);

                retmap.put(id, ret);
               
            }
        }
       
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
       
       
        //CLASS READING
       
        try (BufferedReader br = new BufferedReader(new FileReader(classfile))) {
            String line;
            String[]a = new String[2];
           
            while ((line = br.readLine()) != null  ) {
               
                a = line.split(" ");
                int id = Integer.parseInt(a[0]);
                String kl = a[1];
               
                int kl2 = klasslabels.get(kl);
               
                features.put(id,kl2);
               
               

               
               
            }
        }
       
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
       

        for (article a: article_list)
        {
            a.klass = features.get(a.id);
            a.klasslabel = knames.get(a.id);
        }
       
        System.out.println(trainortest.size());
   
       
        List<article>  trainSet = new ArrayList<article>();
        List<article> testSet = new ArrayList<article>();
        List<article> validSet = new ArrayList<article>();
        for (article a: article_list)
        {
            if(trainortest.containsKey(a.id))
            {
           
                if (trainortest.get(a.id) == 0)
            {
                testSet.add(a);
            }
            else if(trainortest.get(a.id) == 1)
            {
                trainSet.add(a);
            }
                
            else
            {
            	validSet.add(a);
            }
           
            }
        }   


        // CLASS INITIALIZATION
             for (int i = 0; i< klasslabels.size();i++)
             {
                 klass temp = new klass();
                 temp.id = i;
                 Iterator<Entry<String, Integer>> ite = klasslabels.entrySet().iterator();
                  while (ite.hasNext())
                 {
                      Entry<String, Integer> e =  (Entry<String, Integer>)ite.next();
                      String st = e.getKey();
                      int id = e.getValue();
                      
                      if (temp.id == id)
                      {
                          temp.label = st;
                      }
                 }
                
                 class_list.add(temp);
             }
            
            for (article a: trainSet)
             {
                 for(int i = 0; i<class_list.size();i++)
                 {    int index = i;
                     if (a.klass == index)
                     {
                         class_list.get(index).members.add(a);
                         
                     }
                    
                     else
                     {
                         class_list.get(index).notmembers.add(a);
                     }
                    
                 }
                
             }
         
            
jumboMap = findFeatureDist(trainSet,featurerepresentation); //GLOBAL FREQ MAP FROM TRAINING SET(For sigma a ik term in denominator)
System.out.println("The jumbo map has "+jumboMap.size()+" features");
       
                        
        System.out.println("I have to train with "+trainSet.size()+" articles");
        System.out.println("I have to test with "+testSet.size()+" articles");
        System.out.println("I have to validate with "+validSet.size()+" articles");
       

        double[] lambda = {0.001, 0.01, 1.0, 10 };
       
        for(klass k: class_list)
        {
        	k.weights = computeWeights(k, trainSet, featurerepresentation, 1.0);
        	
        }
        
       
       
        
        	
        
        	
        	
        	
        	 
for (article a: testSet)
        {double max = 0;
        	for (klass k: class_list)
        	{
        		a.predscore = computePredScore(a, a.freqv, k);
        		if(a.predscore > max)
        		{
        			max = a.predscore;
        			a.klass = k.id;
        			a.klasslabel = k.label;
        			
        			
        		}
        	}
    
        }
        
        
        
        
        System.out.println("Done Testing!" );
       
   int correct = 0;

   for (article a: testSet)
   {    //System.out.println(a.id+" "+a.klasslabel);
       String ans = a.klasslabel;
       if(ans.equals(knames.get(a.id)))
       {
           correct++;
       }
   }
   

   
   System.out.println(class_list.get(0).weights);
  
   System.out.println(correct+" have been classified correctly out of "+testSet.size()+" articles");

   try( PrintWriter out = new PrintWriter(outputfile)){
	    for (article a: testSet)
	    {
	    out.print(a.id+" "+a.retid+" "+a.klasslabel);
	    out.print("\n");
	    }
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
   
   
   for (klass k: class_list)
   {  
   	double max = 0;
   	
   	for (article a: testSet)
   	{
   		
   		if (featurerepresentation.equals("tf"))
   		{
   		a.predscore = computePredScore(a, a.freqv, k);
   		}
   		else if (featurerepresentation.equals("binary"))
   		{
   			a.predscore = computePredScore(a, a.bin, k);
   		   	
   		}
   		
   		else if(featurerepresentation.equals("tfidf"))
   		{	a.predscore = computePredScoreT(a, a.tfidf, k);
   	   		
   		}
   		
   	}
   	
   	for (article a: testSet)
   	{	
   		
   		
   		double f1score;
   		double tp = 1, tn = 0, fp = 0, fn = 0;
   		double prec,recall ;
   		
   		for(article a2: testSet)
   		{
   	
   			if (a.id != a2.id)
   			{
   				if(a2.predscore < a.predscore)
   				{
   						if(a2.klass != k.id)
   						{
   							tn++;
   						}
   						else
   						{
   							fn++;
   						}
   				}
   				else
   				{
   					if(a2.klass == k.id)
   					{
   						tp++;
   					}
   					else
   					{
   						fp++;
   					}
   				}
   		}
   		
   		}
   	
   		
   			
   		prec = tp/(tp+fp);
   		
   		recall= tp/(tp+fn);
   		
   		
   		if(prec == 0 && recall == 0)
   		{
   			f1score = 0;
   		}
   		
   	else{
   		f1score = (2*prec*recall)/(prec+recall);
   		
   		}
   		
   	if (f1score > max)
   	{
   		max = f1score;
   	}
   	
   	
   	
   }
   	
   	
   	System.out.println("The maximum F1 score for class "+k.label+" is "+max);
   	
   	
   }

	   try( PrintWriter out = new PrintWriter("/home/manix025/Desktop/feat"))
	   {
		   
		    for (klass k: class_list)
		    	
		    {LinkedHashMap<Integer, Double> curr = k.weights;
		    sortByValue(curr);
		    	Iterator<Entry<Integer, Double>> ite = curr.entrySet().iterator();
	        while (ite.hasNext())
	        {
	      
	         	Entry<Integer,Double> e =  (Entry<Integer, Double>)ite.next();
	         	int dim = e.getKey();
	         	double val = e.getValue();
	         	if(val > 0.0)
	         {
		    out.print(k.id+" "+k.label+" "+dim+" "+val);
		    out.print("\n");}
		    }
		    }
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	   
	 
	  
	
  
   
        System.out.println("Done!");
        
    }

}

