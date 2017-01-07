import java.util.*;
import java.util.Map.Entry;



import java.io.*;

class article
{
    int id;
    float retid; //RLABEL FILE ID
    double mag1;
    double mag2;
    double mag3;
    int klass;
    String klasslabel;
    double bestsimwithpos;
    double bestsimwithneg;
    double bestf1score;
    double predScore;
   
    LinkedHashMap<Integer,Integer> freqv = new LinkedHashMap<Integer,Integer>();
    LinkedHashMap<Integer, Integer> bin = new LinkedHashMap<Integer,Integer>();
    LinkedHashMap<Integer, Double> tfidf = new LinkedHashMap<Integer,Double>();
}

class klass

{
    int id;
    String label;
    List<article> members = new ArrayList<article>();
    List<article> notmembers = new ArrayList<article>();
    double mMag;
    double nmMag;
    LinkedHashMap<Integer,Double> pcentroid = new LinkedHashMap<Integer,Double>();
    LinkedHashMap<Integer,Double> ncentroid = new LinkedHashMap<Integer,Double>();
}



class centroid
{   
    public static double computeMag(article a, LinkedHashMap<Integer,Integer> rep)
    {
        double ans = 0;
        Collection<Integer> x = rep.values();
        for (Integer i:x)
        {
            ans = ans+i*i;
        }
        return Math.sqrt(ans);
       
    }
   
   
    public static double computeMa(article a, LinkedHashMap<Integer,Double> rep)
    {
        double ans = 0;
        Collection<Double> x = rep.values();
        for (Double i:x)
        {
            ans = ans+i*i;
        }
        return Math.sqrt(ans);
       
    }
   
   
    public static double computeM(klass c, LinkedHashMap<Integer,Double> rep)
    {
        double ans = 0;
        Collection<Double> x = rep.values();
        for (double d:x)
        {
            ans = ans+ d*d;
        }
        return Math.sqrt(ans);
    }

   
    public static double cosine_sim(article a, LinkedHashMap<Integer,Integer>rep, klass b, LinkedHashMap<Integer,Double> repc,double magart, double magclass)
    {
        double ans = 0;
        Iterator<Entry<Integer, Integer>> ite = rep.entrySet().iterator();
         
         while (ite.hasNext())
        {
             Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
             int key = e.getKey();
             int val = e.getValue();
             
                 if(repc.containsKey(key))
                 {
                     double val2 = repc.get(key);
                     ans = ans+ (val*val2);
                 }
                 
             
             
        }
         
        ans = ans/(magart * magclass);
       
        if (ans > 1)
        {
            ans = 1.0;
        }
        return ans;
    }
   
    public static double cosine_simt(article a, LinkedHashMap<Integer,Double>rep, klass b, LinkedHashMap<Integer,Double> repc,double magart, double magclass)
    {
        double ans = 0;
        Iterator<Entry<Integer, Double>> ite = rep.entrySet().iterator();
         
         while (ite.hasNext())
        {
             Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
             int key = e.getKey();
             double val = e.getValue();
             
                 if(repc.containsKey(key))
                 {
                     double val2 = repc.get(key);
                     ans = ans+ (val*val2);
                 }
                 
             
             
        }
         
        ans = ans/(magart * magclass);
       
        if (ans > 1)
        {
            ans = 1.0;
        }
        return ans;
    }
   
   
public static double log2(double n)
{
        return (Math.log(n) / Math.log(2));
}
   


public static LinkedHashMap<Integer, Double> tf_centroid(klass c,List<article> memb)
{
    LinkedHashMap<Integer, Double> temp = new LinkedHashMap<Integer,Double>();
   
    for (article a: memb)
    {
         Iterator<Entry<Integer, Integer>> ite = a.freqv.entrySet().iterator();
         
         while (ite.hasNext())
        {
             Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
             int key = e.getKey();
             double val = e.getValue();
             
                 if(temp.containsKey(key))
                 {
                     double curr = temp.get(key);
                     val = val + curr;
                     temp.put(key,val);
                 }
                 
                 else
                 {
                    temp.put(key,val);
                 }
                 
             
             
        }
    }

     Iterator<Entry<Integer, Double>> ite = temp.entrySet().iterator();
     int size = memb.size();
     while (ite.hasNext())
    {
         Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
         int key = e.getKey();
         double val = e.getValue();
         val = val/size;
         temp.put(key,val);
   
    }
     
     return temp;
}


public static LinkedHashMap<Integer, Double> bin_centroid(klass c,List<article> memb)
{
    LinkedHashMap<Integer, Double> temp = new LinkedHashMap<Integer,Double>();
   
    for (article a: memb)
    {
         Iterator<Entry<Integer, Integer>> ite = a.bin.entrySet().iterator();
         
         while (ite.hasNext())
        {
             Entry<Integer, Integer> e =  (Entry<Integer, Integer>)ite.next();
             int key = e.getKey();
             double val = e.getValue();
             
                 if(temp.containsKey(key))
                 {
                     double curr = temp.get(key);
                     val = val + curr;
                     temp.put(key,val);
                 }
                 
                 else
                 {
                    temp.put(key,val);
                 }
                 
             
             
        }
    }

     Iterator<Entry<Integer, Double>> ite = temp.entrySet().iterator();
     int size = memb.size();
     while (ite.hasNext())
    {
         Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
         int key = e.getKey();
         double val = e.getValue();
         val = val/size;
         temp.put(key,val);
   
    }
     
     return temp;
}


public static LinkedHashMap<Integer, Double> tfidf_centroid(klass c,List<article> memb)
{
    LinkedHashMap<Integer, Double> temp = new LinkedHashMap<Integer,Double>();
   
    for (article a: memb)
    {
         Iterator<Entry<Integer, Double>> ite = a.tfidf.entrySet().iterator();
         
         while (ite.hasNext())
        {
             Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
             int key = e.getKey();
             double val = e.getValue();
             
                 if(temp.containsKey(key))
                 {
                     double curr = temp.get(key);
                     val = val + curr;
                     temp.put(key,val);
                 }
                 
                 else
                 {
                    temp.put(key,val);
                 }
                 
             
             
        }
    }

     Iterator<Entry<Integer, Double>> ite = temp.entrySet().iterator();
     int size = memb.size();
     while (ite.hasNext())
    {
         Entry<Integer, Double> e =  (Entry<Integer, Double>)ite.next();
         int key = e.getKey();
         double val = e.getValue();
         val = val/size;
         temp.put(key,val);
   
    }
     
     return temp;
}





   
    public static void main(String args[])
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
       
        LinkedHashMap<Integer,Integer> found_articles = new LinkedHashMap<Integer,Integer>();
        List<article> article_list = new ArrayList<article>();
        List <klass> class_list = new ArrayList<klass>();
        LinkedHashMap<Integer,Integer> features = new LinkedHashMap<Integer,Integer>();
        LinkedHashMap<Integer,Float> retmap = new LinkedHashMap<Integer,Float>();
        LinkedHashMap<Integer, String> knames = new LinkedHashMap<Integer,String>();
        LinkedHashMap<Integer, String> featurenames = new LinkedHashMap<Integer,String>();
       
        //ARTICLES WITH THEIR TERM FREQUENCY REPRESENTATION
       
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
       
       
       
       
       
       
       
       
       
       
    // THE BINARY REPRESENTATION
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
   
   
   
   
   
   
   
   
    //THE TFDIF REPRESENTATION
   
   
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
       
       
   
   
    //TRAIN OR TEST HASH MAP

    LinkedHashMap<Integer, Integer> trainortest = new LinkedHashMap<Integer, Integer>();
   
    try (BufferedReader br = new BufferedReader(new FileReader(trainfile))) {
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
   
   
   //LABEL READING
    
    try (BufferedReader br = new BufferedReader(new FileReader(inputfile))) {
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
        a.mag1 = computeMag(a,a.freqv);
        a.mag2 = computeMag(a, a.bin);
        a.mag3 = computeMa(a,a.tfidf);
       
         
    }
   
   
    
    
   
   
   
   
//SPLIT INTO TRAIN SET AND TEST SET   
   
List<article>  trainSet = new ArrayList<article>();
List<article> testSet = new ArrayList<article>();

for (article a: article_list)
{
    if (trainortest.get(a.id) == 0)
    {
        testSet.add(a);
    }
    else
    {
        trainSet.add(a);
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
    
System.out.println("I have to train with "+trainSet.size()+" articles");
System.out.println("I have to test with "+testSet.size()+" articles");


//TRAINING PHASE
    
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
    
     for (klass k: class_list)
     {
         System.out.println("For training, there are "+k.members.size()+" instances of class "+k.label+" and "+k.notmembers.size()+" other instances");
     }
    
//COMPUTING OF THE POSITIVE AND THE NEGATIVE CENTROIDS
    
     for (klass k: class_list)
     {
        
    
     if (featurerepresentation.equals("tf"))
             {
                 k.pcentroid = tf_centroid(k, k.members);
                 k.ncentroid = tf_centroid(k, k.notmembers);
                 k.mMag = computeM(k, k.pcentroid);
                 k.nmMag = computeM(k,k.ncentroid);
             }
    
     else if(featurerepresentation.equals("binary"))
     {
         k.pcentroid = bin_centroid(k,k.members);
         k.ncentroid = bin_centroid(k,k.notmembers);
         k.mMag = computeM(k, k.pcentroid);
        k.nmMag = computeM(k,k.ncentroid);
     }
    
     else if(featurerepresentation.equals("tfidf"))
     {
         k.pcentroid = tfidf_centroid(k,k.members);
         k.ncentroid = tfidf_centroid(k,k.notmembers);
         k.mMag = computeM(k, k.pcentroid);
        k.nmMag = computeM(k,k.ncentroid);

    
     }
    
    
    
    
     }
    
    
     System.out.println("Training over!" );
    
     //System.out.println(knames.toString());
    
     //TESTING PHASE
    
     for(article a: testSet)
     {
         double max = 0;
         if (featurerepresentation.equals("tf"))
         {   
             for (int i = 0; i<class_list.size();i++)
             {    klass currClass = class_list.get(i);
                 double simwithpos = cosine_sim(a, a.freqv, currClass, currClass.pcentroid, a.mag1,currClass.mMag);
                
                 double simwithneg = cosine_sim(a, a.freqv, currClass, currClass.ncentroid, a.mag1, currClass.nmMag);
                
                 double diff = simwithpos - simwithneg;
                
                 if (diff > max)
                 {    max = diff;
                     a.klass = currClass.id;
                     a.bestsimwithpos = simwithpos;
                     a.bestsimwithneg = simwithneg;
                     a.klasslabel = currClass.label;
                 }
             }
             a.bestf1score = max;
                
         }
    
        
         else if (featurerepresentation.equals("binary"))
         {   
             for (int i = 0; i<class_list.size();i++)
             {    klass currClass = class_list.get(i);
                 double simwithpos = cosine_sim(a, a.bin, currClass, currClass.pcentroid, a.mag2,currClass.mMag);
                 double simwithneg = cosine_sim(a, a.bin, currClass, currClass.ncentroid, a.mag2, currClass.nmMag);
                 double diff = simwithpos - simwithneg;
                
                
                 if (diff > max)
                 {    max = diff;
                     a.klass = currClass.id;
                     a.bestsimwithpos = simwithpos;
                     a.bestsimwithneg = simwithneg;
                     a.klasslabel = currClass.label;
                 }
             }
            
             a.bestf1score = max;
                
         }
        
         else if (featurerepresentation.equals("tfidf"))
         {    for (int i = 0; i<class_list.size();i++)
             {    klass currClass = class_list.get(i);
                 double simwithpos = cosine_simt(a, a.tfidf, currClass, currClass.pcentroid, a.mag3,currClass.mMag);
                 double simwithneg = cosine_simt(a, a.tfidf, currClass, currClass.ncentroid, a.mag3, currClass.nmMag);
                 double diff = simwithpos - simwithneg;
                
                 if (diff > max)
                 {    max = diff;
                     a.klass = currClass.id;
                     a.bestsimwithpos = simwithpos;
                     a.bestsimwithneg = simwithneg;
                     a.klasslabel = currClass.label;
                 }
             }
             a.bestf1score = max;
                
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

System.out.println(correct+" have been classified correctly out of "+testSet.size()+" articles");

try( PrintWriter out = new PrintWriter("/home/manix025/Desktop/PCentroids")){
    for (klass k: class_list)
    {
    out.print(k.id+" "+k.label+" "+k.pcentroid.toString());
    out.print("\n");
    }
} catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}

try( PrintWriter out = new PrintWriter("/home/manix025/Desktop/NCentroids")){
    for (klass k: class_list)
    {
    out.print(k.id+" "+k.label+" "+k.ncentroid.toString());
    out.print("\n");
    }
} catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}

try( PrintWriter out = new PrintWriter(outputfile)){
    for (article a: testSet)
    {
    out.print(a.id+" "+a.klasslabel+" "+a.bestsimwithpos+" "+a.bestsimwithneg+" "+a.bestf1score);
    out.print("\n");
    }
} catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}




final long endTime = System.currentTimeMillis();


for (klass k: class_list)
{  
	double max = 0;
	
	for (article a: testSet)
	{
		
		if (featurerepresentation.equals("tf"))
		{
		a.predScore = cosine_sim(a,a.freqv, k, k.pcentroid, a.mag1,k.mMag)-cosine_sim(a,a.freqv,k,k.ncentroid,a.mag1,k.nmMag);
		}
		else if (featurerepresentation.equals("binary"))
		{
		a.predScore = cosine_sim(a,a.bin, k, k.pcentroid, a.mag2,k.mMag)-cosine_sim(a,a.bin,k,k.ncentroid,a.mag2,k.nmMag);
		}
		
		else if(featurerepresentation.equals("tfidf"))
		{a.predScore = cosine_simt(a,a.tfidf, k, k.pcentroid, a.mag3,k.mMag)-cosine_simt(a,a.tfidf,k,k.ncentroid,a.mag3,k.nmMag);
			
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
				if(a2.predScore < a.predScore)
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

System.out.print("Congrats Miner, you have completed your execution in "+(endTime - starttime)/1000+" seconds");
    
    }
}
